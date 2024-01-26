package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Company;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("select a from Appointment a join fetch a.equipment e where a.id =?1")
    public Appointment findOneWithEquipment(Integer appointmentId);

    @Query("select a from Appointment a join fetch a.equipmentQuantities e where a.id =?1")
    public Appointment findOneWithEquipmentQuantitites(Integer appointmentId);

    List<Appointment> findByCompany_IdAndTypeAndUserIsNull(Integer companyId, Appointment.AppointmentType type);

    @Query("SELECT DISTINCT a FROM Appointment a WHERE a.company.id = :companyId AND DATE(a.pickupTime) = DATE(:date) AND (a.administrator IS NOT NULL OR a.user IS NOT NULL)")
    List<Appointment> findBookedTimeSlotsForDay(@Param("companyId") Integer companyId, @Param("date") LocalDateTime date);

    @Query("SELECT DISTINCT a.administrator.id FROM Appointment a WHERE a.pickupTime = :pickupTime AND a.administrator.id IS NOT NULL AND a.company.id = :companyId")
    List<Integer> findAdminIdsForAppointmentsAtPickupTime(
            @Param("pickupTime") LocalDateTime pickupTime,
            @Param("companyId") Integer companyId);

    public List<Appointment> findAllByUser_Id(Integer userId);

    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND a.status IN (:statuses)")
    public List<Appointment> findUsersFutureAppointments(@Param("userId") Integer userId, @Param("statuses")List<Appointment.AppointmentStatus> statuses);

}
