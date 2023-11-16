package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="company_administrator")
public class CompanyAdministrator extends User{

    @OneToMany(mappedBy = "companyAdministrator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AdministratorComplaint> administratorComplaints;
    public CompanyAdministrator() {
    }

    public List<AdministratorComplaint> getAdministratorComplaints() {
        return administratorComplaints;
    }

    public void setAdministratorComplaints(List<AdministratorComplaint> administratorComplaints) {
        this.administratorComplaints = administratorComplaints;
    }
}
