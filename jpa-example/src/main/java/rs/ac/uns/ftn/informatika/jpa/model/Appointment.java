package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Appointment {

    public enum AppointmentStatus {ON_HOLD, IN_PROGRESS, DONE, CANCELED}
    public enum AppointmentType{PREDEFINED, EXCEPTIONAL}

    @Column(name="status", unique=false, nullable=false)
    private AppointmentStatus status;

    @Column(name="type", unique=false, nullable=false)
    private AppointmentType type;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "administrator_id")
    private CompanyAdministrator administrator;

    // Da li treba odvojiti mozda u dva (time i date)
    @Column(name = "pickupTime", nullable = false)
    private LocalDateTime pickupTime;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registered_user_id", nullable = true)
    private RegisteredUser user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "appointment_equipment", joinColumns = @JoinColumn(name = "appointment_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id"))
    private Set<Equipment> equipment = new HashSet<>();

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<EquipmentQuantity> equipmentQuantities = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    public Appointment() {
    }

    public Appointment(CompanyAdministrator administrator, LocalDateTime pickupTime, Integer duration, RegisteredUser user, Company company, AppointmentStatus status, AppointmentType type) {
        this.administrator = administrator;
        this.pickupTime = pickupTime;
        this.duration = duration;
        this.user = user;
        this.company = company;
        this.status = status;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CompanyAdministrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(CompanyAdministrator administrator) {
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

    public RegisteredUser getUser() {
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    public Set<EquipmentQuantity> getEquipmentQuantities() {
        return equipmentQuantities;
    }

    public void setEquipmentQuantities(Set<EquipmentQuantity> equipmentQuantities) {
        this.equipmentQuantities = equipmentQuantities;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

}
