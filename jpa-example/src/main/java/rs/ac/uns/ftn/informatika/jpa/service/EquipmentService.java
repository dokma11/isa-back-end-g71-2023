package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.AvailableEquipmentQuantityDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;
import rs.ac.uns.ftn.informatika.jpa.repository.AppointmentRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.CompanyRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.EquipmentQuantityRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.EquipmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentQuantityRepository equipmentQuantityRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Equipment findOne(Integer id) {
        return equipmentRepository.findById(id).orElseGet(null);
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public Page<Equipment> findAll(Pageable page) {
        return equipmentRepository.findAll(page);
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public void remove(Integer id) {
        equipmentRepository.deleteById(id);
    }

    public List<AvailableEquipmentQuantityDTO> getAvailableQuantity(Integer companyId){
        //get all equipment from company
        List<Equipment> equipment = equipmentRepository.findByCompany_Id(companyId);
        List<AvailableEquipmentQuantityDTO> dtos = new ArrayList<>();
        for (Equipment eq: equipment) {
            // for each find the reserved amount
            Integer reservedQuantity = equipmentQuantityRepository.getSumOfQuantitiesByEquipmentId(eq.getId());
            if(reservedQuantity == null)
                reservedQuantity = 0;
            AvailableEquipmentQuantityDTO dto = new AvailableEquipmentQuantityDTO(eq.getId(), eq.getQuantity() - reservedQuantity);
            dtos.add(dto);
        }

        return dtos;
    }
}
