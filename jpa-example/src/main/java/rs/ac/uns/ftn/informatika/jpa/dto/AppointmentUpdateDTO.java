package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Appointment;

import java.time.LocalDateTime;

public class AppointmentUpdateDTO {

    private CompanyAdministratorResponseDTO administrator;
    private LocalDateTime pickupTime;
    private Integer duration;
    private RegisteredUserResponseDTO user;
    private CompanyResponseDTO company;
    private Appointment.AppointmentStatus status;
    private Appointment.AppointmentType type;

    public AppointmentUpdateDTO() {

    }

    public AppointmentUpdateDTO(Appointment appointment) {
        this.administrator = new CompanyAdministratorResponseDTO(appointment.getAdministrator());
        this.pickupTime = appointment.getPickupTime();
        this.duration = appointment.getDuration();
        this.user = new RegisteredUserResponseDTO(appointment.getUser());
        this.company = new CompanyResponseDTO(appointment.getCompany());
        this.status = appointment.getStatus();
        this.type = appointment.getType();
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
