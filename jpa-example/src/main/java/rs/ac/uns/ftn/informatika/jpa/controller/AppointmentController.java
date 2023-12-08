package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.AppointmentDTO;
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
    public ResponseEntity<List<AppointmentDTO>> getAppointments() {

        List<Appointment> appointments = appointmentService.findAll();

        // convert appointments to DTOs
        List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
        for (Appointment a : appointments) {
            appointmentsDTO.add(new AppointmentDTO(a));
        }

        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Integer id) {

        Appointment appointment = appointmentService.findOne(id);

        // appointment must exist
        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AppointmentDTO> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {

        CompanyAdministrator administrator = companyAdministratorService.findOne(appointmentDTO.getAdministrator().getId());
        RegisteredUser user = registeredUserService.findOne(appointmentDTO.getUser().getId());
        Company company = companyService.findOne(appointmentDTO.getCompany().getId());

        Appointment appointment = new Appointment();
        appointment.setAdministrator(administrator);
        appointment.setPickupTime(appointmentDTO.getPickupTime());
        appointment.setDuration(appointmentDTO.getDuration());
        appointment.setUser(user);
        appointment.setCompany(company);
        appointment.setStatus(appointmentDTO.getStatus());
        appointment.setType(appointment.getType());

        appointment = appointmentService.save(appointment);
        return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Integer id, @RequestBody AppointmentDTO appointmentDTO) {

        // an appointment must exist
        Appointment appointment = appointmentService.findOne(appointmentDTO.getId());
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
        return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
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
