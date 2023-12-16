package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.jpa.dto.EquipmentAndQuantityResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    @Query("select e from Equipment e join fetch e.appointments a where e.id =?1")
    public Equipment findOneWithAppointments(Integer equipmentId);

    public List<Equipment> findByCompany_Id(Integer companyId);

    @Query("SELECT NEW rs.ac.uns.ftn.informatika.jpa.dto.EquipmentAndQuantityResponseDTO(eq.equipmentId, e.name, e.description,e.type,eq.quantity) " +
            "FROM EquipmentQuantity eq " +
            "JOIN Equipment e ON eq.equipmentId = e.id " +
            "WHERE eq.appointment.id = :appointmentId")
    List<EquipmentAndQuantityResponseDTO> findEquipmentAndQuantityByAppointmentId(@Param("appointmentId") Integer appointmentId);

}
