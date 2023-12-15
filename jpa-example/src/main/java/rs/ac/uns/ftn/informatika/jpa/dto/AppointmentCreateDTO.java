package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Appointment;

import java.time.LocalDateTime;

public class AppointmentCreateDTO {
    private CompanyAdministratorResponseDTO administrator;
    private LocalDateTime pickupTime;
    private Integer duration;
    private RegisteredUserResponseDTO user;
    private Integer companyId;
    private Appointment.AppointmentStatus status;
    private Appointment.AppointmentType type;

    public AppointmentCreateDTO() {

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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
