package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        CompanyAdministrator administrator = null;
        RegisteredUser user = null;

        if(appointmentDTO.getAdministratorId() != null){
            administrator = companyAdministratorService.findOne(appointmentDTO.getAdministratorId());
        }

        if(appointmentDTO.getUserId() != null){
            user = registeredUserService.findOne(appointmentDTO.getUserId());
        }

        Company company = companyService.findOne(appointmentDTO.getCompanyId());

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

    @GetMapping(value = "/predefined/{id}")
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentResponseDTO>> getCompanysPredefinedAppointments(@PathVariable Integer id) {

        List<Appointment> appointments = appointmentService.findAllPredefinedAppointmentsForCompany(id);

        // convert appointments to DTOs
        List<AppointmentResponseDTO> appointmentsDTO = new ArrayList<>();
        for (Appointment a : appointments) {
            appointmentsDTO.add(new AppointmentResponseDTO(a));
        }

        return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
    }

    @GetMapping("/freeTimeSlots")
    @PreAuthorize("hasRole('REGISTERED_USER')")
    public List<LocalDateTime> getFreeTimeSlots(
            @RequestParam Integer companyId,
            @RequestParam String date,  // Datum u formatu "2023-12-31T00:00"
            @RequestParam String startTime,  // Vreme u formatu "2023-12-31T08:00"
            @RequestParam String endTime  // Vreme u formatu "2023-12-31T16:00"
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime selectedDate = LocalDateTime.parse(date, formatter);;
        LocalDateTime parsedStartTime = LocalDateTime.parse(startTime,formatter);
        LocalDateTime parsedEndTime = LocalDateTime.parse(endTime,formatter);

        // Poziv servisa sa odgovarajućim parametrima
        return appointmentService.findFreeTimeSlots(companyId, selectedDate, parsedStartTime, parsedEndTime);
    }

    @GetMapping(value = "/admins")
    @PreAuthorize("hasAnyRole('REGISTERED_USER')")
    public ResponseEntity<List<Integer>> findAdminIdsForAppointmentsAtPickupTime(
            @RequestParam("pickupTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime pickupTime,
            @RequestParam("companyId") Integer companyId) {

        // Call the service method with both parameters
        List<Integer> adminIds = appointmentService.findAdminIdsForAppointmentsAtPickupTime(pickupTime, companyId);

        return new ResponseEntity<>(adminIds, HttpStatus.OK);
    }

    @PutMapping(path = "/schedule/{userId}/{appointmentId}")
    @PreAuthorize("hasAnyRole('REGISTERED_USER')")
    public ResponseEntity<AppointmentResponseDTO> schedulePredefinedAppointment(@PathVariable Integer userId, @PathVariable Integer appointmentId){
        Appointment appointment = appointmentService.schedulePredefinedAppointment(userId,appointmentId);
        if(appointment == null)
            return ResponseEntity.notFound().build();
        AppointmentResponseDTO dto = new AppointmentResponseDTO(appointment);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}")
    @PreAuthorize("hasAnyRole('REGISTERED_USER')")
    public ResponseEntity<List<AppointmentResponseDTO>> getUsersAppointments(@PathVariable Integer userId){
        List<AppointmentResponseDTO> dtos = new ArrayList<>();
        List<Appointment> appointments = appointmentService.getUsersAppointments(userId);
        for (Appointment a: appointments) {
            AppointmentResponseDTO dto = new AppointmentResponseDTO(a);
            dtos.add(dto);
        }

        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }


}
