package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.RegisteredUser;
import rs.ac.uns.ftn.informatika.jpa.repository.RegisteredUserRepository;

import java.util.List;

@Service
public class RegisteredUserService {

    @Autowired
    RegisteredUserRepository registeredUserRepository;

    @Autowired
    private MailService mailService;
    public Page<RegisteredUser> findAll(Pageable pageable) {return registeredUserRepository.findAll(pageable);}
    public RegisteredUser create(RegisteredUser user)  { return registeredUserRepository.save(user); }
    public void remove(int id) { registeredUserRepository.deleteById(id);}

    public List<RegisteredUser> getAll() {return registeredUserRepository.findAll();}
    public RegisteredUser findOne(Integer id){
        return registeredUserRepository.findById(id).orElse(null);
    }

    public RegisteredUser register(RegisteredUser user) throws MailException{
        RegisteredUser newUser = create(user);
        try{
            mailService.sendRegistrationNotification(newUser);
        }catch(InterruptedException e){
            System.out.println("Interrupted exception!");
        }
        return newUser;
    }
}
