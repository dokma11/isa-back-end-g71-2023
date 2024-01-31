package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;
import rs.ac.uns.ftn.informatika.jpa.model.HospitalContract;

import java.util.List;

public interface HospitalContractRepository extends JpaRepository<HospitalContract, Integer> {

    List<HospitalContract> findAllByCompanyName(String companyName);

}
