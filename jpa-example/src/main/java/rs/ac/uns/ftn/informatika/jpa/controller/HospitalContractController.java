package rs.ac.uns.ftn.informatika.jpa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.HospitalContractDTO;
import rs.ac.uns.ftn.informatika.jpa.model.CompanyAdministrator;
import rs.ac.uns.ftn.informatika.jpa.model.HospitalContract;
import rs.ac.uns.ftn.informatika.jpa.service.CompanyAdministratorService;
import rs.ac.uns.ftn.informatika.jpa.service.HospitalContractService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/contracts")
public class HospitalContractController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final Logger log = LoggerFactory.getLogger(VehicleLocationController.class);

    @Autowired
    private HospitalContractService hospitalContractService;

    @Autowired
    private CompanyAdministratorService companyAdministratorService;

    @GetMapping
    @PreAuthorize("hasAnyRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<List<HospitalContractDTO>> getContract() {

        List<HospitalContract> hospitalContracts = hospitalContractService.findAll();

        List<HospitalContractDTO> hospitalContractDTOS = new ArrayList<>();
        for (HospitalContract contract : hospitalContracts) {
            hospitalContractDTOS.add(new HospitalContractDTO(contract));
        }

        return new ResponseEntity<>(hospitalContractDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("hasRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<HospitalContractDTO> updateHospitalContract(@PathVariable Integer id, @RequestBody HospitalContractDTO hospitalContractDTO) {

        HospitalContract hospitalContract = hospitalContractService.findOne(id);

        if (hospitalContract == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        hospitalContract.setHospitalName(hospitalContractDTO.getHospitalName());
        hospitalContract.setHospitalAddress(hospitalContractDTO.getHospitalAddress());
        hospitalContract.setCompanyName(hospitalContractDTO.getCompanyName());
        hospitalContract.setCompanyAddress(hospitalContractDTO.getCompanyAddress());
        hospitalContract.setEquipmentName(hospitalContractDTO.getEquipmentName());
        hospitalContract.setEquipmentQuantity(hospitalContractDTO.getEquipmentQuantity());
        hospitalContract.setDeliveryDate(hospitalContractDTO.getDeliveryDate());
        hospitalContract.setStatus(hospitalContractDTO.getStatus());
        hospitalContract = hospitalContractService.save(hospitalContract);

        if(hospitalContract.getStatus() == HospitalContract.HospitalContractStatus.NEW){
            String hospitalSimulatorEditMessage = "Vas ugovor je izmenjen datuma: " + LocalDate.now() + " u: " + LocalTime.now() + "h";
            rabbitTemplate.convertAndSend("control-queue-hospital", hospitalSimulatorEditMessage);
        }
        else{
            String hospitalSimulatorCancelMessage = "Vasa porudzbina je otkazana datuma: " + LocalDate.now() + " u: " + LocalTime.now() + "h";
            rabbitTemplate.convertAndSend("control-queue-hospital", hospitalSimulatorCancelMessage);
        }

        return new ResponseEntity<>(new HospitalContractDTO(hospitalContract), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole( 'COMPANY_ADMINISTRATOR')")
    public ResponseEntity<List<HospitalContractDTO>> getCompanyContracts(@PathVariable Integer id) {
        CompanyAdministrator admin  = companyAdministratorService.findOne(id);
        List<HospitalContract> hospitalContracts = hospitalContractService.findAllByCompanyName(admin.getCompany().getName());

        List<HospitalContractDTO> hospitalContractDTOS = new ArrayList<>();
        for (HospitalContract contract : hospitalContracts) {
            hospitalContractDTOS.add(new HospitalContractDTO(contract));
        }

        return new ResponseEntity<>(hospitalContractDTOS, HttpStatus.OK);
    }

    @RabbitListener(queues = "spring-boot-hospital2")
    public void handler(String message) {
        log.info("Consumer> " + message);

        String[] parts = message.split("\\|");

        if (parts.length > 8) {
            HospitalContract hospitalContract = new HospitalContract(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], HospitalContract.HospitalContractStatus.valueOf(parts[8]));
            hospitalContractService.save(hospitalContract);
        } else {
            log.error("Invalid message format: " + message);
        }
    }

}
