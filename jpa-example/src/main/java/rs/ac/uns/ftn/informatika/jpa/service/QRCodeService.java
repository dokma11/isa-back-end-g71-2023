package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.QRCode;
import rs.ac.uns.ftn.informatika.jpa.repository.QRCodeRepository;

import java.util.List;

@Service
public class QRCodeService {
    @Autowired
    private QRCodeRepository qrCodeRepository;

    public List<QRCode> getAllQRCodeByUser(Integer userId) {
        return qrCodeRepository.findByUserId(userId);
    }
    public List<QRCode> getQRCodeByUserAndStatus(Integer userId, Appointment.AppointmentStatus status) {
        return qrCodeRepository.findQRCodeByUserAndAppointmentStatus(userId, status);
    }
}
