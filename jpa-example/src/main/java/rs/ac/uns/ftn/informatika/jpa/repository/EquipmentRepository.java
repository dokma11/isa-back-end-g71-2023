package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    @Query("select e from Equipment e join fetch e.appointments a where e.id =?1")
    public Equipment findOneWithAppointments(Integer equipmentId);

}
