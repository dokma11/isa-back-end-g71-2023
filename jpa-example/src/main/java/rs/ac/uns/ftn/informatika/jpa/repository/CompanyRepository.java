package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.jpa.model.Company;

import java.util.List;
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    List<Company> findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(String name, String address);

    @Query("select c from Company c join fetch c.administrators a where c.id =?1")
    public Company findOneWithAdministrators(Integer companyId);

    @Query("select c from Company c join fetch c.appointments a where c.id =?1")
    public Company findOneWithAppointments(Integer companyId);

    @Query("select distinct c from Company c " +
            "left join fetch c.administrators a " +
            "left join fetch c.appointments ap " +
            "where c.id = ?1")
    public Company findOneWithAdministratorsAndAppointments(Integer companyId);

    @Query("select c from Company c join fetch c.equipment e where c.id =?1")
    public Company findOneWithEquipment(Integer companyId);

    @Query("SELECT COUNT(DISTINCT a.id) FROM Company c JOIN c.administrators a WHERE c.id = :companyId")
    long countAdminsByCompanyId(@Param("companyId") Integer companyId);
}
