package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    private LocalDateTime sentDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registered_user_id", nullable = true)
    private RegisteredUser user;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    public QRCode(String content, LocalDateTime sentDate, RegisteredUser user, Appointment appointment) {
        this.content = content;
        this.sentDate = sentDate;
        this.user = user;
        this.appointment = appointment;
    }

    public QRCode(){

    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
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

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }
}
