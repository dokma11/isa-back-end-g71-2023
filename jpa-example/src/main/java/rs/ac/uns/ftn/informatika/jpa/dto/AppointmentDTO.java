package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Appointment;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Integer id;
    private String adminName;
    private String adminSurname;
    private LocalDateTime pickupTime;
    private Integer duration;
    private CompanyDTO company;

    public AppointmentDTO() {
    }

    public AppointmentDTO(Appointment a) {
        this.adminName = a.getAdminName();
        this.adminSurname = a.getAdminSurname();
        this.pickupTime = a.getPickupTime();
        this.duration = a.getDuration();
        this.company = new CompanyDTO(a.getCompany());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminSurname() {
        return adminSurname;
    }

    public void setAdminSurname(String adminSurname) {
        this.adminSurname = adminSurname;
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

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }
}
