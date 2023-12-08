package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Appointment {

    public enum AppointmentStatus {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "administrator_id", nullable = false)
    private CompanyAdministrator administrator;

    // Da li treba odvojiti mozda u dva (time i date)
    @Column(name = "pickupTime", nullable = false)
    private LocalDateTime pickupTime;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registered_user_id")
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

    public Appointment(CompanyAdministrator administrator, LocalDateTime pickupTime, Integer duration, RegisteredUser user, Company company) {
        this.administrator = administrator;
        this.pickupTime = pickupTime;
        this.duration = duration;
        this.user = user;
        this.company = company;
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
}
