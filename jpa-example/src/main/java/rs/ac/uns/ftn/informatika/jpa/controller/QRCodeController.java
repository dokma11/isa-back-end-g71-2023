package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.AppointmentResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.QRCodeDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Appointment;
import rs.ac.uns.ftn.informatika.jpa.model.QRCode;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyService;
import rs.ac.uns.ftn.informatika.jpa.service.QRCodeService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/qrCode")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping(path = "/users/{userId}/qrCodes")
    @PreAuthorize("hasAnyRole('REGISTERED_USER')")
    public ResponseEntity<List<QRCodeDTO>> getUsersQRCodes(@PathVariable Integer userId){
        List<QRCodeDTO> dtos = new ArrayList<>();
        List<QRCode> qrs = qrCodeService.getAllQRCodeByUser(userId);
        for (QRCode qr: qrs) {
            QRCodeDTO dto = new QRCodeDTO(qr);
            dtos.add(dto);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}/qrCodes/status/{status}")
    @PreAuthorize("hasAnyRole('REGISTERED_USER')")
    public ResponseEntity<List<QRCodeDTO>> getUsersQRCodesByStatus(
            @PathVariable Integer userId,
            @PathVariable String status
    ) {
        List<QRCodeDTO> dtos = new ArrayList<>();
        Appointment.AppointmentStatus appointmentStatus = Appointment.AppointmentStatus.valueOf(status); // Assuming status is an enum
        List<QRCode> qrs = qrCodeService.getQRCodeByUserAndStatus(userId, appointmentStatus);

        for (QRCode qr : qrs) {
            QRCodeDTO dto = new QRCodeDTO(qr);
            dtos.add(dto);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

}
