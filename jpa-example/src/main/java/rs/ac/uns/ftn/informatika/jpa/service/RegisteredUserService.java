package rs.ac.uns.ftn.informatika.jpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.RegisteredUser;
import rs.ac.uns.ftn.informatika.jpa.repository.RegisteredUserRepository;
import rs.ac.uns.ftn.informatika.jpa.util.TokenUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RegisteredUserService {


    @Autowired
    RegisteredUserRepository registeredUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private MailService mailService;

    public Page<RegisteredUser> findAll(Pageable pageable) {return registeredUserRepository.findAll(pageable);}

    public RegisteredUser create(RegisteredUser user, boolean isPasswordReset)  {
        // hesiranje lozinke
        //provjer da li je doslo do promjene lozinke ili tek treba da upisemo u bazu


        if(isPasswordReset)
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return registeredUserRepository.save(user);
    }

    public void remove(int id) { registeredUserRepository.deleteById(id);}

    public List<RegisteredUser> getAll() {return registeredUserRepository.findAll();}


    public RegisteredUser findOne(Integer id){

        return registeredUserRepository.findById(id).orElse(null);
    }

    public RegisteredUser register(RegisteredUser user) throws MailException{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        RegisteredUser newUser = create(user, false); //ne resetujemo lozinku prvi put kada se regostrujemo
        try{
            mailService.sendRegistrationNotification(newUser);
        }catch(InterruptedException e){
            System.out.println("Interrupted exception!");
        }
        return newUser;
    }

    public boolean confirmRegistration(String token){
        String username = tokenUtils.getUsernameFromToken(token);
        //pronalazenje pomocu username
        RegisteredUser user = registeredUserRepository.findByUsername(username);

        if(user == null)
            return false;

        user.setEnabled(true);
        registeredUserRepository.save(user);

        return true;
    }


    public RegisteredUser updatePenaltyPoints(Integer userId, LocalDateTime appointmentTime){
        RegisteredUser user = findOne(userId);
        long hoursUntilAppointment = LocalDateTime.now().until(appointmentTime, ChronoUnit.HOURS);
        if(hoursUntilAppointment >= 24){
            //jedan penalty poen se dodjeljuje
            user.setPoints(user.getPoints() + 1);
        }else{
            //dodjeljuju se dva penalty poena
            user.setPoints(user.getPoints() + 2);
        }
        return create(user,false);
    }


    @Scheduled(cron = "0 0 0 1 * ?")
    public void updatePenaltyPoints(){

        List<RegisteredUser> registeredUsers = registeredUserRepository.findAll();
        for(RegisteredUser u : registeredUsers){
            u.setPoints(0);
            registeredUserRepository.save(u);
        }

    }

}
