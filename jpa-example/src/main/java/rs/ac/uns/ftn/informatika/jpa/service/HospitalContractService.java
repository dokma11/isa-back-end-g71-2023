package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;
import rs.ac.uns.ftn.informatika.jpa.model.HospitalContract;
import rs.ac.uns.ftn.informatika.jpa.repository.EquipmentRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.HospitalContractRepository;

import java.util.List;

@Service
public class HospitalContractService {

    @Autowired
    private HospitalContractRepository hospitalContractRepository;

    public HospitalContract findOne(Integer id) {
        return hospitalContractRepository.findById(id).orElseGet(null);
    }

    public List<HospitalContract> findAll() {
        return hospitalContractRepository.findAll();
    }

    public Page<HospitalContract> findAll(Pageable page) {
        return hospitalContractRepository.findAll(page);
    }

    public HospitalContract save(HospitalContract hospitalContract) {
        return hospitalContractRepository.save(hospitalContract);
    }

    public void remove(Integer id) {
        hospitalContractRepository.deleteById(id);
    }

    public List<HospitalContract> findAllByCompanyName (String name){ return  hospitalContractRepository.findAllByCompanyName(name);}

}
