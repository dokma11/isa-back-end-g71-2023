package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Appointment;

import java.time.LocalDateTime;

public class AppointmentResponseDTO {
    private Integer id;
    private CompanyAdministratorResponseDTO administrator;
    private LocalDateTime pickupTime;
    private Integer duration;
    private RegisteredUserResponseDTO user;
    private CompanyResponseDTO company;
    private Appointment.AppointmentStatus status;
    private Appointment.AppointmentType type;

    public AppointmentResponseDTO() {
    }

    public AppointmentResponseDTO(Appointment a) {
        this.id = a.getId();
        this.administrator = new CompanyAdministratorResponseDTO(a.getAdministrator());
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
        this.company = new CompanyResponseDTO(a.getCompany());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CompanyAdministratorResponseDTO getAdministrator() {
        return administrator;
    }

    public void setAdministrator(CompanyAdministratorResponseDTO administrator) {
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

    public CompanyResponseDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyResponseDTO company) {
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
