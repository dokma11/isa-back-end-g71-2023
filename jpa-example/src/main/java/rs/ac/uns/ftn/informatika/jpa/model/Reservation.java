package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;

public class Reservation {
    public enum ReservationStatus { TAKEN, EXPIRED, CANCELLED, IN_PROCESS}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "equipment", nullable = false)
    //private Set<Equipment> equipments = new HashSet<Equipment>();

    @Column(name = "appointment", nullable = false)
    private Appointment appointment;

    @Column(name = "user", nullable = false)
    private RegisteredUser user;

    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    public Reservation(){
    }
}
