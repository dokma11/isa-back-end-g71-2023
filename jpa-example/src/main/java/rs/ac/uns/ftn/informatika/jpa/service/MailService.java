package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import rs.ac.uns.ftn.informatika.jpa.model.RegisteredUser;
import rs.ac.uns.ftn.informatika.jpa.util.TokenUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Autowired
    private TokenUtils tokenUtils;

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




}
