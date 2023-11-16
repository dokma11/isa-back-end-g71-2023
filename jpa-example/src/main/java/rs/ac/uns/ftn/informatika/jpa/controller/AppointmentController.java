package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.AppointmentDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Company;
import rs.ac.uns.ftn.informatika.jpa.service.AppointmentService;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyService;

import java.util.ArrayList;
import java.util.List;

public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CompanyService companyService;

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

        Company company = companyService.findOneWithAppointments(appointmentDTO.getCompany().getId());

        Appointment appointment = new Appointment();
        appointment.setAdminName(appointmentDTO.getAdminName());
        appointment.setAdminSurname(appointmentDTO.getAdminSurname());
        appointment.setPickupTime(appointmentDTO.getPickupTime());
        appointment.setDuration(appointmentDTO.getDuration());
        appointment.setCompany(company);
        company.addAppointment(appointment);

        appointment = appointmentService.save(appointment);
        return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Integer id, @RequestBody AppointmentDTO appointmentDTO) {

        // an appointment must exist
        Appointment appointment = appointmentService.findOne(appointmentDTO.getId());

        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        appointment.setAdminName(appointmentDTO.getAdminName());
        appointment.setAdminSurname(appointmentDTO.getAdminSurname());
        appointment.setPickupTime(appointmentDTO.getPickupTime());
        appointment.setDuration(appointmentDTO.getDuration());

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
