package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.AuthenticationTokensDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CredentialsDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.RegisteredUserResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.RegisteredUser;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.service.AuthorisationService;
import rs.ac.uns.ftn.informatika.jpa.service.CustomUserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/auth")
public class AuthorisationController {
    @Autowired
    private AuthorisationService authorisationService;


    @PostMapping(consumes = "application/json", path = "/login")
    public ResponseEntity<AuthenticationTokensDTO> Login(@RequestBody CredentialsDTO credentials)
    {
        String token = authorisationService.login(credentials);
        if(token.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        AuthenticationTokensDTO tokensDTO = new AuthenticationTokensDTO();
        tokensDTO.setAccessToken(token);
        return new ResponseEntity<>(tokensDTO, HttpStatus.OK);

    }
}
