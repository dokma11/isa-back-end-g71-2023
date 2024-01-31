package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.Equipment;
import rs.ac.uns.ftn.informatika.jpa.model.QRCode;

import java.util.List;

public interface QRCodeRepository  extends JpaRepository<QRCode, Integer> {

    List<QRCode> findByUserId(Integer userId);

    @Query("SELECT qr FROM QRCode qr " +
            "WHERE qr.user.id = :userId " +
            "AND qr.appointment.status = :status")
    List<QRCode> findQRCodeByUserAndAppointmentStatus(@Param("userId") Integer userId, @Param("status") Appointment.AppointmentStatus status);

}
