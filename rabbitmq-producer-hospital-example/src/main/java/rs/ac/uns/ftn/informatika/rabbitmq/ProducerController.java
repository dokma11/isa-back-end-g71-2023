package rs.ac.uns.ftn.informatika.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "api")
public class ProducerController {
	
	@Autowired
	private Producer producer;

	private String message1 = "1|Ninina kompanija|4, Mihala Babinke, Novi Sad, Srbija|Dom zdravlja Novi Sad|77, Bulevar cara Lazara, Novi Sad, Srbija|Igla za vadjenje krvi|15|2024-02-02|NEW";
	private String message2 = "2|Ninina kompanija|4, Mihala Babinke, Novi Sad, Srbija|Klinicki centar Novi Sad|101, Bulevar oslobodjenja, Novi Sad, Srbija|Stalak za infuziju|5|2024-01-29|NEW";
	private	String message3 = "3|Ninina kompanija|4, Mihala Babinke, Novi Sad, Srbija|Klinicki centar Novi Sad|10, Futoski put, Novi Sad, Srbija|Krevet za pacijenta|10|2024-02-05|NEW";

	private String queue = "spring-boot-hospital2";
	private String exchange = "myexchangehospital";

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
		producer.sendToExchange(exchange, queue, message1);
		log.info("Producer > " + message1);

		producer.sendToExchange(exchange, queue, message2);
		log.info("Producer > " + message2);

		producer.sendToExchange(exchange, queue, message3);
		log.info("Producer > " + message3);
	}

	@PostConstruct
	public void executeOnApplicationStart() {
		sendAutomaticMessage();
	}

	@RabbitListener(queues = "control-queue-hospital")
	public void handleControlMessage(String message) {
		log.info("Producer> " + message);

		if(message.contains("Zavrsena")){
			LocalDate currentDate = LocalDate.now();
			LocalDate newDate = currentDate.plusMonths(1);

			if(message1.contains(LocalDate.now().toString())){
				message1 = "1|Ninina kompanija|4, Mihala Babinke, Novi Sad, Srbija|Dom zdravlja Novi Sad|77, Bulevar cara Lazara, Novi Sad, Srbija|Igla za vadjenje krvi|15|" + newDate + "|NEW";
				producer.sendToExchange(exchange, queue, message1);
			}
			else if(message2.contains(LocalDate.now().toString())){
				message2 = "2|Ninina kompanija|4, Mihala Babinke, Novi Sad, Srbija|Klinicki centar Novi Sad|101, Bulevar oslobodjenja, Novi Sad, Srbija|Stalak za infuziju|5|" + newDate + "|NEW";
				producer.sendToExchange(exchange, queue, message2);
			}
			else if(message2.contains(LocalDate.now().toString())){
				message3 = "3|Ninina kompanija|4, Mihala Babinke, Novi Sad, Srbija|Klinicki centar Novi Sad|10, Futoski put, Novi Sad, Srbija|Krevet za pacijenta|10|" + newDate + "|NEW";
				producer.sendToExchange(exchange, queue, message3);
			}
		}
	}
}
