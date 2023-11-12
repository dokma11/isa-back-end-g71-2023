package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="company_administrator")
public class CompanyAdministrator extends User{
    
    public CompanyAdministrator() {
    }
}
