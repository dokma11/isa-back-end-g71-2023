package rs.ac.uns.ftn.informatika.jpa.controller;


import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.RegisterUserCreateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.RegisteredUserResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.RegisteredUserUpdateDTO;
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
    public ResponseEntity<RegisteredUserResponseDTO> register(@RequestBody RegisterUserCreateDTO user){
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
            RegisteredUser savedUser = registeredUserService.register(newUser);

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

    @PutMapping(value ="/{id}",consumes = "application/json")
    public ResponseEntity<RegisteredUserResponseDTO> update(@PathVariable Integer id, @RequestBody RegisteredUserUpdateDTO user){
        try{
                RegisteredUser userForUpdate = registeredUserService.findOne(id);
                if(userForUpdate == null){
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                userForUpdate.setName(user.getName());
                userForUpdate.setSurname(user.getSurname());
                userForUpdate.setTelephoneNumber(user.getTelephoneNumber());
                userForUpdate.setCity(user.getCity());
                userForUpdate.setState(user.getState());
                userForUpdate.setProfession(user.getProfession());
                userForUpdate.setPassword(user.getPassword());
                userForUpdate.setCompanyInformation(user.getCompanyInformation());

                userForUpdate = registeredUserService.create(userForUpdate);
                return new ResponseEntity<>(new RegisteredUserResponseDTO(userForUpdate),HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping(value ="/{id}")
    public ResponseEntity<RegisteredUserResponseDTO> getOneUser(@PathVariable Integer id)
    {
        RegisteredUser u = registeredUserService.findOne(id);

        if(u == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RegisteredUserResponseDTO user = new RegisteredUserResponseDTO(u);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
