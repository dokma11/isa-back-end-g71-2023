package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.informatika.jpa.dto.CompanyDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Company;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyService;

@RestController
@RequestMapping(value = "api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getCompanies() {

        List<Company> companies = companyService.findAll();

        // convert companies to DTOs
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company c : companies) {
            companiesDTO.add(new CompanyDTO(c));
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Integer id) {

        Company company = companyService.findOne(id);

        // company must exist
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CompanyDTO> saveCourse(@RequestBody CompanyDTO companyDTO) {

        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setLongitude(companyDTO.getLongitude());
        company.setLatitude(companyDTO.getLatitude());
        company.setDescription(companyDTO.getDescription());
        company.setAverageGrade(companyDTO.getAverageGrade());

        company = companyService.save(company);
        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody CompanyDTO companyDTO) {

        // a company must exist
        Company company = companyService.findOne(companyDTO.getId());

        if (company == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setLongitude(companyDTO.getLongitude());
        company.setLatitude(companyDTO.getLatitude());
        company.setDescription(companyDTO.getDescription());
        company.setAverageGrade(companyDTO.getAverageGrade());

        company = companyService.save(company);
        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Integer id) {

        Company company = companyService.findOne(id);

        if (company != null) {
            companyService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CompanyDTO>> getSearchedCompanies(@RequestParam String searchTerm,
                                                                 @RequestParam String searchType) {

        List<Company> searchedCompanies = companyService.search(searchTerm, searchType);

        // convert companies to DTOs
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (Company c : searchedCompanies) {
            companiesDTO.add(new CompanyDTO(c));
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }



}