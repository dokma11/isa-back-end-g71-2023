package rs.ac.uns.ftn.informatika.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "api")
public class ProducerController {
	
	@Autowired
	private Producer producer;
	private int iterationNumber;
	private boolean shouldSend = false;
	private	ArrayList<Double> latitudes = new ArrayList<Double>();
	private ArrayList<Double> longitudes = new ArrayList<Double>();

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

	@Scheduled(fixedRate = 3000)
	public void sendAutomaticMessage() {
		if(shouldSend && iterationNumber < 19){
			String queue = "spring-boot2";
			String message = latitudes.get(iterationNumber) + "," + longitudes.get(iterationNumber);
			String exchange = "myexchange";

			producer.sendToExchange(exchange, queue, message);

			iterationNumber++;
		}
	}

	@RabbitListener(queues = "control-queue")
	public void handleControlMessage(String message) {
		if ("start-sending-3-seconds".equals(message)) {
			startSendingMessages();
		} else if ("stop-sending".equals(message)) {
			stopSendingMessages();
		}
	}

	private void startSendingMessages() {
		shouldSend = true;
		iterationNumber = 0;

		latitudes.add(45.270323);
		longitudes.add(19.807234);

		latitudes.add(45.270144);
		longitudes.add(19.807656);

		latitudes.add(45.270639);
		longitudes.add(19.808594);

		latitudes.add(45.269980);
		longitudes.add(19.809431);

		latitudes.add(45.268515);
		longitudes.add(19.811307);

		latitudes.add(45.266132);
		longitudes.add(19.814551);

		latitudes.add(45.264596);
		longitudes.add(19.816550);

		latitudes.add(45.263041);
		longitudes.add(19.818271);

		latitudes.add(45.261055);
		longitudes.add(19.820547);

		latitudes.add(45.259148);
		longitudes.add(19.822667);

		latitudes.add(45.255504);
		longitudes.add(19.824241);

		latitudes.add(45.253447);
		longitudes.add(19.824289);

		latitudes.add(45.251176);
		longitudes.add(19.824415);

		latitudes.add(45.247568);
		longitudes.add(19.825018);

		latitudes.add(45.245299);
		longitudes.add(19.825110);

		latitudes.add(45.241621);
		longitudes.add(19.825238);

		latitudes.add(45.240042);
		longitudes.add(19.826082);

		latitudes.add(45.240817);
		longitudes.add(19.829116);

		latitudes.add(45.241075);
		longitudes.add(19.830262);

		System.out.println("Sending messages started!");
	}

	private void stopSendingMessages() {
		shouldSend = false;
		System.out.println("Sending messages stopped!");
	}
}
