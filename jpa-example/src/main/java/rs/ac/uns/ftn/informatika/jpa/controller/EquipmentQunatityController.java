package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.AppointmentCreateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.AppointmentResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.EquipmentQuantityDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.RegisteredUserResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.service.AppointmentService;
import rs.ac.uns.ftn.informatika.jpa.service.EquipmentQuantityService;
import rs.ac.uns.ftn.informatika.jpa.service.EquipmentService;
import rs.ac.uns.ftn.informatika.jpa.service.MailService;

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

}
