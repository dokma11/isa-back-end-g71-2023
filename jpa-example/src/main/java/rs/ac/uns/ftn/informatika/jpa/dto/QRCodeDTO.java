package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.QRCode;

import java.time.LocalDateTime;

public class QRCodeDTO {

    private Integer id;
    private String content;
    private LocalDateTime sentDate;
    private RegisteredUserResponseDTO user; // Assuming the user ID is an integer
    private AppointmentResponseDTO appointment; // Assuming the appointment ID is an integer

    public QRCodeDTO(QRCode qr){
        this.id = qr.getId();
        this.content = qr.getContent();
        this.sentDate = qr.getSentDate();
        this.user = new RegisteredUserResponseDTO(qr.getUser());
        this.appointment = new AppointmentResponseDTO(qr.getAppointment());

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public RegisteredUserResponseDTO getUser() {
        return user;
    }

    public void setUser(RegisteredUserResponseDTO user) {
        this.user = user;
    }

    public AppointmentResponseDTO getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentResponseDTO appointment) {
        this.appointment = appointment;
    }
}
