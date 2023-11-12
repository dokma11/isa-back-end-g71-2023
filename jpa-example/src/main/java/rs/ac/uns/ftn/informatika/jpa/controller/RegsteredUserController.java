package rs.ac.uns.ftn.informatika.jpa.controller;


import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.RegisterUserCreateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.RegisteredUserResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.RegisteredUser;
import rs.ac.uns.ftn.informatika.jpa.service.RegisteredUserService;

import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/registeredUsers")
public class RegsteredUserController {

    @Autowired
    RegisteredUserService registeredUserService;

    @GetMapping
    public ResponseEntity<List<RegisteredUserResponseDTO>> GetAll()
    {
        List<RegisteredUser> registeredUsers = registeredUserService.getAll();
        List<RegisteredUserResponseDTO> dtos = new ArrayList<>();
        //DTO MAPPING
        for (RegisteredUser u: registeredUsers) {
            RegisteredUserResponseDTO dto = new RegisteredUserResponseDTO(u);
            dtos.add(dto);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<RegisteredUserResponseDTO> create(@RequestBody RegisterUserCreateDTO user){
        try{
            RegisteredUser newUser = new RegisteredUser();

            //DTO MAPPING
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setState(user.getState());
            newUser.setCity(user.getCity());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setTelephoneNumber(user.getTelephoneNumber());
            newUser.setProfession(user.getProfession());
            newUser.setCompanyInformation(user.getCompanyInformation());


            //saving new user
            RegisteredUser savedUser = registeredUserService.create(newUser);

            //DTO mapping
            RegisteredUserResponseDTO response = new RegisteredUserResponseDTO(savedUser);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        RegisteredUser user = registeredUserService.findOne(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            registeredUserService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
