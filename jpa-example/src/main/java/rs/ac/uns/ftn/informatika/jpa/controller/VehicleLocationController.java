package rs.ac.uns.ftn.informatika.jpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.informatika.jpa.model.HospitalContract;
import rs.ac.uns.ftn.informatika.jpa.service.HospitalContractService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping(value = "api/vehicles")
public class VehicleLocationController {

    String returnMessage = "";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private HospitalContractService hospitalContractService;

    private static final Logger log = LoggerFactory.getLogger(VehicleLocationController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send/message")
    public Map<String, String> broadcastNotification(String message) {
        Map<String, String> messageConverted = parseMessage(message);

        if (messageConverted != null) {
            if (messageConverted.containsKey("toId") && messageConverted.get("toId") != null
                    && !messageConverted.get("toId").equals("")) {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageConverted.get("toId"),
                        messageConverted);
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageConverted.get("fromId"),
                        messageConverted);
            } else {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher", messageConverted);
            }
        }

        return messageConverted;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> parseMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> retVal;

        try {
            retVal = mapper.readValue(message, Map.class);
        } catch (IOException e) {
            retVal = null;
        }

        return retVal;
    }

    @RabbitListener(queues="spring-boot2")
    public void handler(String message){
        log.info("Consumer> " + message);
        returnMessage = message;
        String destination = "/socket-publisher";

        simpMessagingTemplate.convertAndSend(destination, message);
    }

    @GetMapping("/startSending")
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR', 'SYSTEM_ADMINISTRATOR')")
    public String startSendingMessages() {
        sendControlMessageToProducer("start-sending-3-seconds");
        return "Sending messages started!";
    }

    @GetMapping("/stopSending")
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR', 'SYSTEM_ADMINISTRATOR')")
    public String stopSendingMessages() {
        sendControlMessageToProducer("stop-sending");
        return "Sending messages stopped!";
    }

    private void sendControlMessageToProducer(String controlMessage) {
        rabbitTemplate.convertAndSend("control-queue", controlMessage);

        sendMessagesToHospital();
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void sendAutomaticMessage() {
        for (HospitalContract hospitalContract : hospitalContractService.findAll()) {
            if (hospitalContract != null && hospitalContract.getDeliveryDate() != null && hospitalContract.getStatus() == HospitalContract.HospitalContractStatus.NEW) {
                String deliveryDateString = hospitalContract.getDeliveryDate().toString();
                String currentDateString = LocalDate.now().toString();

                if (deliveryDateString.equals(currentDateString)) {
                    sendMessagesToHospital();
                }
            } else if (hospitalContract != null && hospitalContract.getDeliveryDate() != null && hospitalContract.getStatus() == HospitalContract.HospitalContractStatus.CANCELED) {
                LocalDate currentDate = LocalDate.now();
                LocalDate newDate = currentDate.plusMonths(1);

                hospitalContract.setDeliveryDate(newDate);
                hospitalContract.setStatus(HospitalContract.HospitalContractStatus.NEW);
                hospitalContractService.save(hospitalContract);

                String hospitalSimulatorEditMessage = "Izmenjen je ugovor datuma: " + LocalDate.now() + " u: " + LocalTime.now() + "h";
                rabbitTemplate.convertAndSend("control-queue-hospital", hospitalSimulatorEditMessage);
            }
        }
    }

    private void sendMessagesToHospital(){
        String hospitalSimulatorStartMessage = "Pocela je dostava opreme datuma: " + LocalDate.now() + " u: " + LocalTime.now() + "h";
        rabbitTemplate.convertAndSend("control-queue-hospital", hospitalSimulatorStartMessage);

        try {
            Thread.sleep(55_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        String hospitalSimulatorEndMessage = "Zavrsena je dostava opreme datuma: " + LocalDate.now() + " u: " + LocalTime.now() + "h";
        rabbitTemplate.convertAndSend("control-queue-hospital", hospitalSimulatorEndMessage);
    }
}
