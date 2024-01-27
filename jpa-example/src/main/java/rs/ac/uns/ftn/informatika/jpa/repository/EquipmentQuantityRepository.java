package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.EquipmentQuantity;

import java.util.List;

public interface EquipmentQuantityRepository  extends JpaRepository<EquipmentQuantity, Integer> {
    @Query("SELECT SUM(eq.quantity) FROM EquipmentQuantity eq " +
            "LEFT JOIN eq.appointment app " +
            "WHERE  eq.equipmentId = :equipmentId AND app.status IN (0, 1)")
    Integer getSumOfQuantitiesByEquipmentId(@Param("equipmentId") Integer equipmentId);

    List<EquipmentQuantity> findAllByAppointment_Id(Integer appointmentId);
}
