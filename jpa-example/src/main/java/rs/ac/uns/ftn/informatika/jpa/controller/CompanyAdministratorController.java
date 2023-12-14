package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.CompanyAdministratorCreateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CompanyAdministratorResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CompanyAdministratorUpdateDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Company;
import rs.ac.uns.ftn.informatika.jpa.model.CompanyAdministrator;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyAdministratorService;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/companyAdministrator")
public class CompanyAdministratorController {
    @Autowired
    private CompanyAdministratorService companyAdministratorService;

    @Autowired
    private CompanyService companyService;

    @GetMapping
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
    public ResponseEntity<CompanyAdministratorResponseDTO> getCompanyAdministrator(@PathVariable Integer id) {

        CompanyAdministrator companyAdministrator = companyAdministratorService.findOne(id);

        // company must exist
        if (companyAdministrator == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyAdministratorResponseDTO(companyAdministrator), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CompanyAdministratorResponseDTO> saveCompanyAdministrator(@RequestBody CompanyAdministratorCreateDTO companyAdministratorDTO) {

        Company company = companyService.findOneWithAdministrators(companyAdministratorDTO.getCompany().getId());

        CompanyAdministrator companyAdministrator = new CompanyAdministrator();
        companyAdministrator.setName(companyAdministratorDTO.getName());
        companyAdministrator.setSurname(companyAdministratorDTO.getSurname());
        companyAdministrator.setCity(companyAdministratorDTO.getCity());
        companyAdministrator.setCompany(company);
        company.addAdministrator(companyAdministrator);
        companyAdministrator.setCompanyInformation(companyAdministratorDTO.getCompanyInformation());
        companyAdministrator.setEmail(companyAdministratorDTO.getEmail());
        companyAdministrator.setPassword(companyAdministratorDTO.getPassword());
        companyAdministrator.setProfession(companyAdministratorDTO.getProfession());
        companyAdministrator.setRole(companyAdministratorDTO.getRole());
        companyAdministrator.setState(companyAdministratorDTO.getState());
        companyAdministrator.setTelephoneNumber(companyAdministratorDTO.getTelephoneNumber());

        companyAdministrator = companyAdministratorService.save(companyAdministrator);
        return new ResponseEntity<>(new CompanyAdministratorResponseDTO(companyAdministrator), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
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
        companyAdministrator.setEmail(companyAdministratorDTO.getEmail());
        companyAdministrator.setPassword(companyAdministratorDTO.getPassword());
        companyAdministrator.setProfession(companyAdministratorDTO.getProfession());
        companyAdministrator.setRole(companyAdministratorDTO.getRole());
        companyAdministrator.setState(companyAdministratorDTO.getState());
        companyAdministrator.setTelephoneNumber(companyAdministratorDTO.getTelephoneNumber());

        companyAdministrator = companyAdministratorService.save(companyAdministrator);
        return new ResponseEntity<>(new CompanyAdministratorResponseDTO(companyAdministrator), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
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
    public ResponseEntity<CompanyAdministratorResponseDTO> getByEmail(@PathVariable String email) {

        CompanyAdministrator companyAdministrator = companyAdministratorService.findByEmail(email);

        // company must exist
        if (companyAdministrator == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyAdministratorResponseDTO(companyAdministrator), HttpStatus.OK);
    }
}
