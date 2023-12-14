package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.AppointmentCreateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.AppointmentResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.AppointmentUpdateDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Company;
import rs.ac.uns.ftn.informatika.jpa.model.CompanyAdministrator;
import rs.ac.uns.ftn.informatika.jpa.model.RegisteredUser;
import rs.ac.uns.ftn.informatika.jpa.service.AppointmentService;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyAdministratorService;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyService;
import rs.ac.uns.ftn.informatika.jpa.service.RegisteredUserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CompanyAdministratorService companyAdministratorService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private RegisteredUserService registeredUserService;

    @GetMapping
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments() {

        List<Appointment> appointments = appointmentService.findAll();

        // convert appointments to DTOs
        List<AppointmentResponseDTO> appointmentsDTO = new ArrayList<>();
        for (Appointment a : appointments) {
            appointmentsDTO.add(new AppointmentResponseDTO(a));
        }

        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<AppointmentResponseDTO> getAppointment(@PathVariable Integer id) {

        Appointment appointment = appointmentService.findOne(id);

        // appointment must exist
        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AppointmentResponseDTO(appointment), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<AppointmentResponseDTO> saveAppointment(@RequestBody AppointmentCreateDTO appointmentDTO) {

        CompanyAdministrator administrator = new CompanyAdministrator();
        RegisteredUser user = new RegisteredUser();

        if(appointmentDTO.getAdministrator() != null){
            administrator = companyAdministratorService.findOne(appointmentDTO.getAdministrator().getId());
        }

        if(appointmentDTO.getUser() != null){
            user = registeredUserService.findOne(appointmentDTO.getUser().getId());
        }

        Company company = companyService.findOne(appointmentDTO.getCompany().getId());

        Appointment appointment = new Appointment();
        appointment.setAdministrator(administrator);
        appointment.setPickupTime(appointmentDTO.getPickupTime());
        appointment.setDuration(appointmentDTO.getDuration());
        appointment.setUser(user);
        appointment.setCompany(company);
        appointment.setStatus(appointmentDTO.getStatus());
        appointment.setType(appointmentDTO.getType());

        appointment = appointmentService.save(appointment);
        return new ResponseEntity<>(new AppointmentResponseDTO(appointment), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@PathVariable Integer id, @RequestBody AppointmentUpdateDTO appointmentDTO) {

        // an appointment must exist
        Appointment appointment = appointmentService.findOne(id);
        CompanyAdministrator administrator = companyAdministratorService.findOne(appointmentDTO.getAdministrator().getId());
        RegisteredUser user = registeredUserService.findOne(appointmentDTO.getUser().getId());
        Company company = companyService.findOne(appointmentDTO.getCompany().getId());

        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        appointment.setAdministrator(administrator);
        appointment.setPickupTime(appointmentDTO.getPickupTime());
        appointment.setDuration(appointmentDTO.getDuration());
        appointment.setUser(user);
        appointment.setCompany(company);
        appointment.setStatus(appointmentDTO.getStatus());
        appointment.setType(appointmentDTO.getType());

        appointment = appointmentService.save(appointment);
        return new ResponseEntity<>(new AppointmentResponseDTO(appointment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Integer id) {

        Appointment appointment = appointmentService.findOne(id);

        if (appointment != null) {
            appointmentService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
