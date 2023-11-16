package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.Company;

import java.util.List;
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    List<Company> findByNameContainingIgnoreCase(String name);

    List<Company> findByAddressContainingIgnoreCase(String address);

    @Query("select c from Company c join fetch c.administrators a where c.id =?1")
    public Company findOneWithAdministrators(Integer companyId);

    @Query("select c from Company c join fetch c.appointments a where c.id =?1")
    public Company findOneWithAppointments(Integer companyId);

    @Query("select distinct c from Company c " +
            "left join fetch c.administrators a " +
            "left join fetch c.appointments ap " +
            "where c.id = ?1")
    public Company findOneWithAdministratorsAndAppointments(Integer companyId);

}
