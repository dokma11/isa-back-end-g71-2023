package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="system_administrator")
public class SystemAdministrator extends User {

    @OneToMany(mappedBy = "systemAdministrator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AdministratorComplaintResponse> administratorComplaintResponseSet = new HashSet<AdministratorComplaintResponse>();

    @OneToMany(mappedBy = "systemAdministrator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CompanyComplaintResponse> companyComplaintResponses = new HashSet<CompanyComplaintResponse>();

    public SystemAdministrator(){

    }

    public Set<AdministratorComplaintResponse> getAdministratorComplaintResponseSet() {
        return administratorComplaintResponseSet;
    }

    public void setAdministratorComplaintResponseSet(Set<AdministratorComplaintResponse> administratorComplaintResponseSet) {
        this.administratorComplaintResponseSet = administratorComplaintResponseSet;
    }

    public Set<CompanyComplaintResponse> getCompanyComplaintResponses() {
        return companyComplaintResponses;
    }

    public void setCompanyComplaintResponses(Set<CompanyComplaintResponse> companyComplaintResponses) {
        this.companyComplaintResponses = companyComplaintResponses;
    }
}
