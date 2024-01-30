package rs.ac.uns.ftn.informatika.jpa.service;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.jpa.dto.EquipmentQuantityDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;
import rs.ac.uns.ftn.informatika.jpa.model.EquipmentQuantity;
import rs.ac.uns.ftn.informatika.jpa.repository.EquipmentQuantityRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.EquipmentRepository;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(readOnly = false)
public class EquipmentQuantityService {

    private final Logger LOG = LoggerFactory.getLogger(RegisteredUserService.class);
    @Autowired
    private EquipmentQuantityRepository equipmentQuantityRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Cacheable("equipmentQuantity")
    public EquipmentQuantity findOne(Integer id) {
        return equipmentQuantityRepository.findById(id).orElseGet(null);
    }

    @RateLimiter(name = "standard", fallbackMethod = "standardFallback")
    public List<EquipmentQuantity> findAll() {
        LOG.info("Uspjesan poziv za rate limiter.");
        return equipmentQuantityRepository.findAll();
    }

    // Metoda koja ce se pozvati u slucaju RequestNotPermitted exception-a
    public List<EquipmentQuantity> standardFallback(RequestNotPermitted rnp) {
        LOG.warn("Prevazidjen broj poziva u ogranicenom vremenskom intervalu");
        // Samo prosledjujemo izuzetak -> global exception handler koji bi ga obradio :)
        throw rnp;
    }
    public Page<EquipmentQuantity> findAll(Pageable page) {
        return equipmentQuantityRepository.findAll(page);
    }

    @Transactional(readOnly = false)
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

    @CacheEvict(cacheNames = {"equipmentQuantity"}, allEntries = true)
    public void removeFromCache() {
        LOG.info("equipmentQuantity removed from cache!");
    }
}
