package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.EquipmentQuantityDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;
import rs.ac.uns.ftn.informatika.jpa.model.EquipmentQuantity;
import rs.ac.uns.ftn.informatika.jpa.repository.EquipmentQuantityRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.EquipmentRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class EquipmentQuantityService {

    @Autowired
    private EquipmentQuantityRepository equipmentQuantityRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    public EquipmentQuantity findOne(Integer id) {
        return equipmentQuantityRepository.findById(id).orElseGet(null);
    }

    public List<EquipmentQuantity> findAll() {
        return equipmentQuantityRepository.findAll();
    }

    public Page<EquipmentQuantity> findAll(Pageable page) {
        return equipmentQuantityRepository.findAll(page);
    }

    public EquipmentQuantity save(EquipmentQuantity equipment) {

            //najdemo eq
            Equipment eqForUpdate = equipmentRepository.findById(equipment.getEquipmentId()).orElseGet(null);
            if(eqForUpdate == null || eqForUpdate.getAvailableQuantity() < equipment.getQuantity()){
                return null;
            }
            //umanjivanje
            eqForUpdate.setAvailableQuantity(eqForUpdate.getAvailableQuantity() - equipment.getQuantity());
            //cuvanje
            equipmentRepository.save(eqForUpdate);

        return equipmentQuantityRepository.save(equipment);
    }

    public void remove(Integer id) {
        equipmentQuantityRepository.deleteById(id);
    }

    public List<EquipmentQuantity> getIfRemovable(Integer id) {

        List<EquipmentQuantity> ret = new ArrayList<>();
        List<EquipmentQuantity> equipmentQuantities = equipmentQuantityRepository.findAll();

        for (EquipmentQuantity e : equipmentQuantities) {
            if(e.getEquipmentId().equals(id)){
                if(e.getAppointment().getStatus().equals(Appointment.AppointmentStatus.ON_HOLD) ||
                   e.getAppointment().getStatus().equals(Appointment.AppointmentStatus.IN_PROGRESS)){
                    ret.add(e);
                }
            }
        }

        return ret;
    }

    public Integer getQuantityByEquipmentId(Integer id){
        Integer counter = 0;
        for (EquipmentQuantity e : equipmentQuantityRepository.findAll()) {
            if(e.getEquipmentId().equals(id)){
                if(e.getAppointment().getStatus().equals(Appointment.AppointmentStatus.ON_HOLD) ||
                        e.getAppointment().getStatus().equals(Appointment.AppointmentStatus.IN_PROGRESS)){
                    counter++;
                }
            }
        }

        return counter;
    }
}
