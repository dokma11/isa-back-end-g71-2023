package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.CompanyAdministrator;

public interface CompanyAdministratorRepository extends JpaRepository<CompanyAdministrator, Integer> {

    @Query("select a from CompanyAdministrator a where a.username =?1")
    public CompanyAdministrator findByUsername(String username);

}
