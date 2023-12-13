package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="company_administrator")
public class CompanyAdministrator extends User{


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "companyAdministrator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AdministratorComplaint> administratorComplaints;

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
}
