package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;

@Entity
@Table(name="company_administrator")
public class CompanyAdministrator extends User{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    public CompanyAdministrator() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
