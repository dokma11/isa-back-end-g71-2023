package rs.ac.uns.ftn.informatika.jpa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import rs.ac.uns.ftn.informatika.jpa.model.HospitalContract;

@SpringBootApplication
public class HospitalContractController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(VehicleLocationController.class);


    @RabbitListener(queues = "spring-boot-hospital2")
    public void handler(String message) {
        log.info("Consumer> " + message);

        String[] parts = message.split("\\|");

        if (parts.length >= 7) {
            HospitalContract hospitalContract = new HospitalContract(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
            log.info(hospitalContract.toString());
        } else {
            log.error("Invalid message format: " + message);
        }
    }

}
