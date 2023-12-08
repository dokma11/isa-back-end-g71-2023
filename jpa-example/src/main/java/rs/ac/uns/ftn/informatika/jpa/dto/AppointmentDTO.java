package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Company;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Integer id;
    private CompanyAdministratorDTO administrator;
    private LocalDateTime pickupTime;
    private Integer duration;
    private RegisteredUserResponseDTO user;
    private CompanyDTO company;
    private Appointment.AppointmentStatus status;
    private Appointment.AppointmentType type;


    public AppointmentDTO() {
    }

    public AppointmentDTO(Appointment a) {
        this.administrator = new CompanyAdministratorDTO(a.getAdministrator());
        this.pickupTime = a.getPickupTime();
        this.duration = a.getDuration();
        if(a.getUser() != null){
            this.user = new RegisteredUserResponseDTO(a.getUser());
        }
        else{
            this.user = null;
        }
        this.status = a.getStatus();
        this.type = a.getType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CompanyAdministratorDTO getAdministrator() {
        return administrator;
    }

    public void setAdministrator(CompanyAdministratorDTO administrator) {
        this.administrator = administrator;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public RegisteredUserResponseDTO getUser() {
        return user;
    }

    public void setUser(RegisteredUserResponseDTO user) {
        this.user = user;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public Appointment.AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(Appointment.AppointmentStatus status) {
        this.status = status;
    }

    public Appointment.AppointmentType getType() {
        return type;
    }

    public void setType(Appointment.AppointmentType type) {
        this.type = type;
    }
}
