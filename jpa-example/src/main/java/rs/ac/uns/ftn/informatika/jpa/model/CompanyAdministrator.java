package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="company_administrator")
public class CompanyAdministrator extends User{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "companyAdministrator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AdministratorComplaint> administratorComplaints;

    @OneToMany(mappedBy = "administrator", fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

    @Column(name="verified", nullable = false)
    private boolean verified;

    public CompanyAdministrator() {

    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<AdministratorComplaint> getAdministratorComplaints() {
        return administratorComplaints;
    }

    public void setAdministratorComplaints(List<AdministratorComplaint> administratorComplaints) {
        this.administratorComplaints = administratorComplaints;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

}
