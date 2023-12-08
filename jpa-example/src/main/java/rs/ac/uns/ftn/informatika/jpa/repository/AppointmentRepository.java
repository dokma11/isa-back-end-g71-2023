package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Company;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("select a from Appointment a join fetch a.equipment e where a.id =?1")
    public Appointment findOneWithEquipment(Integer appointmentId);

    @Query("select a from Appointment a join fetch a.equipmentQuantities e where a.id =?1")
    public Appointment findOneWithEquipmentQuantitites(Integer appointmentId);

}
