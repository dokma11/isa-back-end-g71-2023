package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.CompanyAdministratorCreateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CompanyAdministratorResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CompanyAdministratorUpdateDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Company;
import rs.ac.uns.ftn.informatika.jpa.model.CompanyAdministrator;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyAdministratorService;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyService;
import rs.ac.uns.ftn.informatika.jpa.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/companyAdministrator")
public class CompanyAdministratorController {

    @Autowired
    private CompanyAdministratorService companyAdministratorService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CompanyService companyService;

    @GetMapping
    @PreAuthorize("hasRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<List<CompanyAdministratorResponseDTO>> getCompanyAdministrators() {

        List<CompanyAdministrator> companyAdministrators = companyAdministratorService.findAll();

        // convert companies to DTOs
        List<CompanyAdministratorResponseDTO> companyAdministratorsDTO = new ArrayList<>();
        for (CompanyAdministrator c : companyAdministrators) {
            companyAdministratorsDTO.add(new CompanyAdministratorResponseDTO(c));
        }

        return new ResponseEntity<>(companyAdministratorsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<CompanyAdministratorResponseDTO> getCompanyAdministrator(@PathVariable Integer id) {

        CompanyAdministrator companyAdministrator = companyAdministratorService.findOne(id);

        // company must exist
        if (companyAdministrator == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyAdministratorResponseDTO(companyAdministrator), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<CompanyAdministratorResponseDTO> saveCompanyAdministrator(@RequestBody CompanyAdministratorCreateDTO companyAdministratorDTO) {

        Company company = companyService.findOneWithAdministrators(companyAdministratorDTO.getCompany().getId());

        CompanyAdministrator companyAdministrator = new CompanyAdministrator();
        companyAdministrator.setName(companyAdministratorDTO.getName());
        companyAdministrator.setSurname(companyAdministratorDTO.getSurname());
        companyAdministrator.setCity(companyAdministratorDTO.getCity());
        companyAdministrator.setCompany(company);
        company.addAdministrator(companyAdministrator);
        companyAdministrator.setCompanyInformation(companyAdministratorDTO.getCompanyInformation());
        companyAdministrator.setUsername(companyAdministratorDTO.getUsername());
        companyAdministrator.setPassword(companyAdministratorDTO.getPassword());
        companyAdministrator.setProfession(companyAdministratorDTO.getProfession());
        //POGLEDATI JOS JEDNOM
        companyAdministrator.setRole(roleService.findByName("ROLE_COMPANY_ADMINISTRATOR").get(0));
        companyAdministrator.setState(companyAdministratorDTO.getState());
        companyAdministrator.setTelephoneNumber(companyAdministratorDTO.getTelephoneNumber());
        companyAdministrator.setVerified(companyAdministratorDTO.isVerified());

        companyAdministrator = companyAdministratorService.save(companyAdministrator);
        return new ResponseEntity<>(new CompanyAdministratorResponseDTO(companyAdministrator), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole( 'COMPANY_ADMINISTRATOR', 'SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<CompanyAdministratorResponseDTO> updateCompanyAdministrator(@PathVariable Integer id, @RequestBody CompanyAdministratorUpdateDTO companyAdministratorDTO) {

        // a company admin must exist
        CompanyAdministrator companyAdministrator = companyAdministratorService.findOne(id);

        if (companyAdministrator == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        companyAdministrator.setName(companyAdministratorDTO.getName());
        companyAdministrator.setSurname(companyAdministratorDTO.getSurname());
        companyAdministrator.setCity(companyAdministratorDTO.getCity());
        companyAdministrator.setCompanyInformation(companyAdministratorDTO.getCompanyInformation());
        companyAdministrator.setUsername(companyAdministratorDTO.getUsername());

        if(!companyAdministrator.getPassword().equals(companyAdministratorDTO.getPassword())){
            companyAdministrator.setPassword(companyAdministratorDTO.getPassword());
            companyAdministratorService.SetPassword(companyAdministrator);
        }
        
        companyAdministrator.setProfession(companyAdministratorDTO.getProfession());
        companyAdministrator.setRole(roleService.findByName("ROLE_COMPANY_ADMINISTRATOR").get(0));
        companyAdministrator.setState(companyAdministratorDTO.getState());
        companyAdministrator.setTelephoneNumber(companyAdministratorDTO.getTelephoneNumber());
        companyAdministrator.setVerified(companyAdministratorDTO.isVerified());

        companyAdministrator = companyAdministratorService.save(companyAdministrator);
        return new ResponseEntity<>(new CompanyAdministratorResponseDTO(companyAdministrator), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<Void> deleteCompanyAdministrator(@PathVariable Integer id) {

        CompanyAdministrator companyAdministrator = companyAdministratorService.findOne(id);

        if (companyAdministrator != null) {
            companyAdministratorService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/email/{email}")
    @PreAuthorize("hasRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<CompanyAdministratorResponseDTO> getByEmail(@PathVariable String email) {

        CompanyAdministrator companyAdministrator = companyAdministratorService.findByEmail(email);

        // company must exist
        if (companyAdministrator == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyAdministratorResponseDTO(companyAdministrator), HttpStatus.OK);
    }
}
