package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.AvailableEquipmentQuantityDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.EquipmentCreateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.EquipmentResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.EquipmentUpdateDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Company;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyService;
import rs.ac.uns.ftn.informatika.jpa.service.EquipmentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private CompanyService companyService;

    @GetMapping
    @PreAuthorize("hasAnyRole( 'COMPANY_ADMINISTRATOR', 'REGISTERED_USER','SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<List<EquipmentResponseDTO>> getEquipment() {

        List<Equipment> equipment = equipmentService.findAll();

        // convert companies to DTOs
        List<EquipmentResponseDTO> equipmentDTO = new ArrayList<>();
        for (Equipment e : equipment) {
            equipmentDTO.add(new EquipmentResponseDTO(e));
        }

        return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole( 'COMPANY_ADMINISTRATOR', 'REGISTERED_USER','SYSTEM_ADMINISTRATOR')")
    public ResponseEntity<EquipmentResponseDTO> getEquipment(@PathVariable Integer id) {

        Equipment equipment = equipmentService.findOne(id);

        // equipment must exist
        if (equipment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new EquipmentResponseDTO(equipment), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<EquipmentResponseDTO> saveEquipment(@RequestBody EquipmentCreateDTO equipmentDTO) {

        Company company = companyService.findOne(equipmentDTO.getCompany().getId());

        Equipment equipment= new Equipment();
        equipment.setName(equipmentDTO.getName());
        equipment.setDescription(equipmentDTO.getDescription());
        equipment.setType(equipmentDTO.getType());
        equipment.setGrade(equipmentDTO.getGrade());
        equipment.setQuantity(equipmentDTO.getQuantity());
        equipment.setCompany(company);
        company.addEquipment(equipment);

        equipment = equipmentService.save(equipment);
        return new ResponseEntity<>(new EquipmentResponseDTO(equipment), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("hasRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<EquipmentResponseDTO> updateEquipment(@PathVariable Integer id, @RequestBody EquipmentUpdateDTO equipmentDTO) {

        // an equipment must exist
        Equipment equipment = equipmentService.findOne(id);

        if (equipment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        equipment.setName(equipmentDTO.getName());
        equipment.setDescription(equipmentDTO.getDescription());
        equipment.setType(equipmentDTO.getType());
        equipment.setGrade(equipmentDTO.getGrade());
        equipment.setQuantity(equipmentDTO.getQuantity());

        equipment = equipmentService.save(equipment);
        return new ResponseEntity<>(new EquipmentResponseDTO(equipment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Integer id) {

        Equipment equipment = equipmentService.findOne(id);

        if (equipment != null) {
            equipmentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/available/{companyId}")
    @PreAuthorize("hasRole( 'REGISTERED_USER')")
    public ResponseEntity<List<AvailableEquipmentQuantityDTO>> getAvailableQuantity(@PathVariable int companyId){
        List<AvailableEquipmentQuantityDTO> result = equipmentService.getAvailableQuantity(companyId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
