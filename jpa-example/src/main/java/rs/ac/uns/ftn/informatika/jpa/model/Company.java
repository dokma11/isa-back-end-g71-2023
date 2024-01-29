package rs.ac.uns.ftn.informatika.jpa.model;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.persistence.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "averageGrade", nullable = true)
    private double averageGrade;

    @Column(name = "workinghoursstart", nullable = false)
    private LocalTime workingHoursStart;

    @Column(name = "workinghoursend", nullable = false)
    private LocalTime workingHoursEnd;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CompanyAdministrator> administrators = new HashSet<CompanyAdministrator>();

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<Appointment>();

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CompanyComplaint> complaints;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Grade> grades = new HashSet<Grade>();

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Equipment> equipment = new HashSet<>();

    public Company() {
    }

    public Company(String name, String address, double longitude, double latitude, String description, double averageGrade) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.averageGrade = averageGrade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Set<CompanyAdministrator> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(Set<CompanyAdministrator> administrators) {
        this.administrators = administrators;
    }

    public void addAdministrator(CompanyAdministrator administrator) {
        administrators.add(administrator);
        administrator.setCompany(this);
    }

    public void removeAdministrator(CompanyAdministrator administrator) {
        administrators.remove(administrator);
        administrator.setCompany(null);
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public void addAppointment(Appointment a) {
        appointments.add(a);
        a.setCompany(this);
    }

    public void removeAppointment(Appointment a) {
        appointments.remove(a);
        a.setCompany(null);
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }

    public LocalTime getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(LocalTime workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public LocalTime getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(LocalTime workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }

    public List<CompanyComplaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<CompanyComplaint> complaints) {
        this.complaints = complaints;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
    }

    public void addEquipment(Equipment e) {
        equipment.add(e);
        e.setCompany(this);
    }

    public void removeEquipment(Equipment e) {
        equipment.remove(e);
        e.setCompany(null);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
