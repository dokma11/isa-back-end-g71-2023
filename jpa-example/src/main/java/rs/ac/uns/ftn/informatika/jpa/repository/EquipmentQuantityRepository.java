package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.EquipmentQuantity;

public interface EquipmentQuantityRepository  extends JpaRepository<EquipmentQuantity, Integer> {

}
