package rs.ac.uns.ftn.informatika.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "api")
public class ProducerController {
	
	@Autowired
	private Producer producer;

	private static final Logger log = LoggerFactory.getLogger(ProducerController.class);

	@PostMapping(value="/{queue}", consumes = "text/plain")
	public ResponseEntity<String> sendMessage(@PathVariable("queue") String queue, @RequestBody String message) {
		producer.sendTo(queue, message);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value="/{exchange}/{queue}", consumes = "text/plain")
	public ResponseEntity<String> sendMessageToExchange(@PathVariable("exchange") String exchange, @PathVariable("queue") String queue, @RequestBody String message) {
		producer.sendToExchange(exchange, queue, message);
		return ResponseEntity.ok().build();
	}

	@Scheduled(cron = "0 0 0 1 * ?")
	public void sendAutomaticMessage() {
		String queue = "spring-boot-hospital2";
		String message = "0|Ninina kompanija|4, Mihala Babinke, Novi Sad, Srbija|Dom zdravlja Novi Sad|77, Bulevar cara Lazara, Novi Sad, Srbija|Igla za vadjenje krvi|15|2023-02-02";
		String exchange = "myexchangehospital";

		producer.sendToExchange(exchange, queue, message);

		log.info("Producer > " + message);

		String message2 = "1|Ninina kompanija|4, Mihala Babinke, Novi Sad, Srbija|Klinicki centar Novi Sad|101, Bulevar oslobodjenja, Novi Sad, Srbija|Stalak za infuziju|5|2023-02-05";

		producer.sendToExchange(exchange, queue, message2);

		log.info("Producer > " + message);

		String message3 = "2|Ninina kompanija|4, Mihala Babinke, Novi Sad, Srbija|Klinicki centar Novi Sad|10, Futoski put, Novi Sad, Srbija|Krevet za pacijenta|10|2023-01-29";

		producer.sendToExchange(exchange, queue, message3);

		log.info("Producer > " + message);
	}

	@PostConstruct
	public void executeOnApplicationStart() {
		sendAutomaticMessage();
	}

	@RabbitListener(queues = "control-queue-hospital")
	public void handleControlMessage(String message) {
		log.info("Producer> " + message);
	}
}
