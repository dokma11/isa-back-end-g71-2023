package rs.ac.uns.ftn.informatika.jpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.informatika.jpa.dto.*;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Company;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyService;

@RestController
@RequestMapping(value = "api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> getCompanies() {

        List<Company> companies = companyService.findAll();

        // convert companies to DTOs
        List<CompanyResponseDTO> companiesDTO = new ArrayList<>();
        for (Company c : companies) {
            companiesDTO.add(new CompanyResponseDTO(c));
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyResponseDTO> getCompany(@PathVariable Integer id) {

        Company company = companyService.findOne(id);

        // company must exist
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyResponseDTO(company), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CompanyResponseDTO> saveCompany(@RequestBody CompanyCreateDTO companyDTO) {

        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setLongitude(companyDTO.getLongitude());
        company.setLatitude(companyDTO.getLatitude());
        company.setDescription(companyDTO.getDescription());
        company.setAverageGrade(companyDTO.getAverageGrade());

        company = companyService.save(company);
        return new ResponseEntity<>(new CompanyResponseDTO(company), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<CompanyResponseDTO> updateCompany(@PathVariable Integer id, @RequestBody CompanyUpdateDTO companyDTO) {

        // a company must exist
        Company company = companyService.findOne(id);

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
        return new ResponseEntity<>(new CompanyResponseDTO(company), HttpStatus.OK);
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
    public ResponseEntity<List<CompanyResponseDTO>> getSearchedCompanies(@RequestParam String name,
                                                                         @RequestParam String city) {

        List<Company> searchedCompanies = companyService.search(name, city);

        // convert companies to DTOs
        List<CompanyResponseDTO> companiesDTO = new ArrayList<>();
        for (Company c : searchedCompanies) {
            companiesDTO.add(new CompanyResponseDTO(c));
        }

        return new ResponseEntity<>(companiesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{companyId}/equipment")
    public ResponseEntity<List<EquipmentResponseDTO>> getCompaniesEquipment(@PathVariable Integer companyId) {

        Company company = companyService.findOneWithEquipment(companyId);

        Set<Equipment> equipment = company.getEquipment();
        List<EquipmentResponseDTO> equipmentDTO = new ArrayList<>();

        for (Equipment e : equipment) {
            equipmentDTO.add(new EquipmentResponseDTO(e));
        }
        return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{companyId}/appointment")
    public ResponseEntity<List<AppointmentResponseDTO>> getCompaniesAppointments(@PathVariable Integer companyId) {

        Company company = companyService.findOneWithAppointments(companyId);

        Set<Appointment> appointments = company.getAppointments();
        List<AppointmentResponseDTO> appointmentDTO = new ArrayList<>();

        for (Appointment a : appointments) {
            appointmentDTO.add(new AppointmentResponseDTO(a));
        }
        return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);
    }
}
