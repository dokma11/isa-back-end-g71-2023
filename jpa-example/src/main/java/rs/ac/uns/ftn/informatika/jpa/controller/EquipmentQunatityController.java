package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.*;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.service.AppointmentService;
import rs.ac.uns.ftn.informatika.jpa.service.EquipmentQuantityService;
import rs.ac.uns.ftn.informatika.jpa.service.EquipmentService;
import rs.ac.uns.ftn.informatika.jpa.service.MailService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/equipmentQuantity")
public class EquipmentQunatityController {
    @Autowired
    private EquipmentQuantityService equipmentQuantityService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MailService mailService;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('REGISTERED_USER')")
    public ResponseEntity saveEquipmentQunatity(@RequestBody List<EquipmentQuantityDTO> equipmentQuantityDTOS) {
        try{

            for(EquipmentQuantityDTO eq : equipmentQuantityDTOS){
                EquipmentQuantity equipmentQuantity = new EquipmentQuantity();
                equipmentQuantity.setId(eq.getId());
                equipmentQuantity.setEquipmentId(eq.getEquipmentId());
                equipmentQuantity.setQuantity(eq.getQuantity());
                equipmentQuantity.setAppointment(appointmentService.findOne(eq.getAppointmentId()));
                equipmentQuantityService.save(equipmentQuantity);
            }
            Appointment appointment = appointmentService.findOne(equipmentQuantityDTOS.get(0).getAppointmentId());
            mailService.sendAppointmentConfirmation(appointment);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/equipment/{id}")
    @PreAuthorize("hasAnyRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<List<EquipmentQuantityDTO>> getById(@PathVariable Integer id) {

        List<EquipmentQuantity> equipment = equipmentQuantityService.findAll();

        List<EquipmentQuantityDTO> equipmentDTO = new ArrayList<>();
        for (EquipmentQuantity e : equipment) {
            if(e.getEquipmentId().equals(id)){
                equipmentDTO.add(new EquipmentQuantityDTO(e));
            }
        }

        return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/removable/{id}")
    @PreAuthorize("hasRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<List<EquipmentQuantityDTO>> getIfRemovable(@PathVariable Integer id) {

        List<EquipmentQuantity> equipment = equipmentQuantityService.getIfRemovable(id);

        List<EquipmentQuantityDTO> equipmentDTO = new ArrayList<>();
        for (EquipmentQuantity e : equipment) {
            equipmentDTO.add(new EquipmentQuantityDTO(e));
        }

        return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/quantity/{id}")
    @PreAuthorize("hasRole( 'COMPANY_ADMINISTRATOR')")
    public Integer getQuantityByEquipmentId(@PathVariable Integer id) {
        return equipmentQuantityService.getQuantityByEquipmentId(id);
    }

    @GetMapping(value ="/{id}")
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<EquipmentQuantityDTO> getOneById(@PathVariable Integer id)
    {
        EquipmentQuantity u = equipmentQuantityService.findOne(id);

        if(u == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EquipmentQuantityDTO dto = new EquipmentQuantityDTO(u);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/")
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<List<EquipmentQuantityDTO>> getAll(){
        List<EquipmentQuantityDTO> dtos = new ArrayList<>();
        for(EquipmentQuantity ew : equipmentQuantityService.findAll()){
            EquipmentQuantityDTO dto = new EquipmentQuantityDTO(ew);
            dtos.add(dto);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    @GetMapping(value = "/removeCache")
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<String> removeFromCache() {
        equipmentQuantityService.removeFromCache();
        return ResponseEntity.ok("equipmentQuantities successfully removed from cache!");
    }
}
