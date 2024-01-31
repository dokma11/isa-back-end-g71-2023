package rs.ac.uns.ftn.informatika.jpa.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.repository.QRCodeRepository;
import rs.ac.uns.ftn.informatika.jpa.util.TokenUtils;
import rs.ac.uns.ftn.informatika.jpa.repository.EquipmentRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Set;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private QRCodeRepository qrCodeRepository;

    @Async
    public void sendRegistrationNotification(RegisteredUser user) throws MailException, InterruptedException {
        /*SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Registration");
        mail.setText("Hello " + user.getName() + ",\n\nthank you for using our site.\nYour registration was successfull! ");
        javaMailSender.send(mail);

        System.out.println("Email sent!");



        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);*/

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            // Use MimeMessageHelper to set up the MimeMessage
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(user.getUsername());
            mimeMessageHelper.setFrom(env.getProperty("spring.mail.username"));
            mimeMessageHelper.setSubject("Registration");

            // Use HTML for the email body and include a registration link
            // generating special token for confirming identity
            String token = tokenUtils.generateToken(user.getUsername(), user.getRole().getName(), user.getId().toString());
            String registrationLink = "http://localhost:8081/api/registeredUsers/confirmRegistration/"+token;
            String emailBody = "Hello " + user.getName() + ",<br/><br/>Thank you for using our site.<br/>Your registration was successful!<br/><br/>";
            emailBody += "To confirm your registration, please click the following link:<br/><a href='" + registrationLink + "'>" + registrationLink + "</a>";

            // Set the email body as HTML
            mimeMessageHelper.setText(emailBody, true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

            System.out.println("Email sent!");
        } catch (MessagingException e) {
            throw new MailSendException("Failed to send registration email", e);
        }
    }

    @Async
    public void sendAppointmentConfirmation(Appointment appointment) throws MailException, InterruptedException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            // Use MimeMessageHelper to set up the MimeMessage
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(appointment.getUser().getUsername());
            mimeMessageHelper.setFrom(env.getProperty("spring.mail.username"));
            mimeMessageHelper.setSubject("Appointment Confirmation");

            // Use HTML for the email body and include appointment details and QR code
            String appointmentDetails = "Appointment Details:\n";
            LocalDateTime pickupTime = appointment.getPickupTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = pickupTime.format(formatter);
            appointmentDetails += "Date and time: " + formattedDateTime + "\n";
            appointmentDetails += "Duration: " + appointment.getDuration() + "\n";
            appointmentDetails += "Company: " + appointment.getCompany().getName() + "\n";
            appointmentDetails += "Company administrator that will give you the equipment: " + appointment.getAdministrator().getName() + "\n";
            Set<EquipmentQuantity> equipmentQuantitySet = appointment.getEquipmentQuantities(); //mozda ovo nece raditi
            appointmentDetails += "Equipment: \n";
            for(EquipmentQuantity eq : equipmentQuantitySet){
                Equipment equipment = equipmentRepository.findById(eq.getEquipmentId()).orElseGet(null);;
                appointmentDetails += "Name: " + equipment.getName()+ "\n" + " Quantity: " + eq.getQuantity() + "\n\n";
            }


            // Generate QR code with appointment information
            String qrCodeContent = appointmentDetails;
            ByteArrayOutputStream qrCodeStream = generateQRCode(qrCodeContent);
            byte[] qrCodeBytes = qrCodeStream.toByteArray();

            saveQRCodeToDatabase(appointment.getUser(), appointment, qrCodeContent);


            //String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeBytes);
            mimeMessageHelper.addAttachment("qr-code.png", new ByteArrayResource(qrCodeBytes));

            // Embed the QR code image in the email body
            String emailBody = "Thank you for your reservation, all information about your appointment is in the QR code!\n";
            /*String emailBody = "Hello " + appointment.getUser().getName() + ",<br/><br/>";
            emailBody += "Thank you for making an appointment.<br/>";
            emailBody += appointmentDetails + "<br/><br/>";
            emailBody += "<img src='data:image/png;base64," + qrCodeBase64 + "' alt='QR Code'><br/><br/>";
            emailBody += "Please present this QR code during your appointment.<br/>";*/
            String cid = mimeMessageHelper.getMimeMessage().getSession().getProperties().getProperty("mail.smtp.connectiontimeout"); // Generate a unique CID
            emailBody += "<img src='cid:" + cid + "' alt='QR Code'><br/><br/>";

            // Set the email body as HTML
            mimeMessageHelper.setText(emailBody, true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

            System.out.println("Email sent!");
        } catch (MessagingException e) {
            throw new MailSendException("Failed to send appointment confirmation email", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    private ByteArrayOutputStream generateQRCode(String content) throws WriterException, IOException {
        int width = 300;
        int height = 300;
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);

        return out;
    }

    @Async
    public void sendAppointmentPickUpNotification(Appointment appointment) throws MailException, InterruptedException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(appointment.getUser().getUsername());
            mimeMessageHelper.setFrom(env.getProperty("spring.mail.username"));
            mimeMessageHelper.setSubject("Appointment Pick Up Confirmation");

            String emailBody = "Hello " + appointment.getUser().getName() + ",<br/><br/>Thank you for using our site.<br/>You successfully picked up your medical equipment!<br/><br/>";

            String appointmentDetails = "Appointment Details:<br/>";
            LocalDateTime pickupTime = appointment.getPickupTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = pickupTime.format(formatter);
            appointmentDetails += "Date and time: " + formattedDateTime + "<br/>";
            appointmentDetails += "Duration: " + appointment.getDuration() + " minutes<br/>";
            appointmentDetails += "Company name: " + appointment.getCompany().getName() + "<br/>";
            appointmentDetails += "Company administrator that will give you the equipment: " + appointment.getAdministrator().getName() + " " + appointment.getAdministrator().getSurname() + "<br/><br/>";
            Set<EquipmentQuantity> equipmentQuantitySet = appointment.getEquipmentQuantities();
            appointmentDetails += "Equipment: <br/>";
            for(EquipmentQuantity eq : equipmentQuantitySet){
                Equipment equipment = equipmentRepository.findById(eq.getEquipmentId()).orElseGet(null);;
                appointmentDetails += "Name: " + equipment.getName()+ "<br/>" + " Quantity: " + eq.getQuantity() + "<br/>";
            }

            emailBody += appointmentDetails;

            mimeMessageHelper.setText(emailBody, true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

            System.out.println("Email sent!");
        } catch (MessagingException e) {
            throw new MailSendException("Failed to send appointment pick up confirmation email", e);
        }
    }


    private void saveQRCodeToDatabase(RegisteredUser user,Appointment appointment,String content) {
        QRCode qrCode = new QRCode();
        qrCode.setAppointment(appointment);
        qrCode.setUser(user);
        qrCode.setContent(content);
        qrCode.setSentDate(LocalDateTime.now());
        qrCodeRepository.save(qrCode);
    }


}
