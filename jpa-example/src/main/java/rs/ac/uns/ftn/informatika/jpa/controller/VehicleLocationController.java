package rs.ac.uns.ftn.informatika.jpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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
            retVal = mapper.readValue(message, Map.class); // parsiranje JSON stringa
        } catch (IOException e) {
            retVal = null;
        }

        return retVal;
    }

    @RabbitListener(queues="spring-boot2")
    public void handler(String message){
        log.info("Consumer> " + message);
        returnMessage = message;
        log.info("Return messsage is " + returnMessage);

        String destination = "/socket-publisher";
        //Map<String, String> messageConverted = parseMessage(message);

        simpMessagingTemplate.convertAndSend(destination, message);

        log.info("POSLAO JE JEBNO");
    }

    @GetMapping("/startSending")
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'COMPANY_ADMINISTRATOR', 'SYSTEM_ADMINISTRATOR')")
    public String startSendingMessages() {
        sendControlMessageToProducer("start-sending");
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

        String hospitalSimulatorStartMessage = "Pocelo je slanje datuma: " + LocalDate.now() + " u: " + LocalTime.now();
        rabbitTemplate.convertAndSend("control-queue-hospital", hospitalSimulatorStartMessage);

        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        String hospitalSimulatorEndMessage = "Zavrseno je slanje datuma: " + LocalDate.now() + " u: " + LocalTime.now();
        rabbitTemplate.convertAndSend("control-queue-hospital", hospitalSimulatorEndMessage);
    }

}
