package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.AuthenticationTokensDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CredentialsDTO;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.util.TokenUtils;

@Service
public class AuthorisationService {

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private BCryptPasswordEncoder encoder;
    public String login(CredentialsDTO credentials){
        try{
            UserDetails user = customUserService.loadUserByUsername(credentials.getUsername());
            User us = customUserService.getUserByUsername(credentials.username);
            if(user== null || !encoder.matches(credentials.getPassword(),user.getPassword() ))
                return "";
            // generisanje tokena i vracanje
            return tokenUtils.generateToken(credentials.username, us.getRole().getName(), us.getId().toString());
        }catch(Exception e){
            System.out.println("OVO NIJE REALNO VISE EVO.");
            return "";
        }


    }
}
