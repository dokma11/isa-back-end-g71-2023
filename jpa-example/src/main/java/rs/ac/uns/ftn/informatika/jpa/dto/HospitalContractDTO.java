package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.HospitalContract;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

public class HospitalContractDTO {


    private Integer id;

    private String companyName;


    private String companyAddress;


    private String hospitalName;


    private String hospitalAddress;


    private String equipmentName;


    private Integer equipmentQuantity;


    private LocalDate deliveryDate;

    private HospitalContract.HospitalContractStatus status;



    public HospitalContractDTO(HospitalContract hospitalContract) {
        this.id = hospitalContract.getId();
        this.companyName = hospitalContract.getCompanyName();
        this.companyAddress = hospitalContract.getCompanyAddress();
        this.hospitalName = hospitalContract.getHospitalName();
        this.hospitalAddress = hospitalContract.getHospitalAddress();
        this.equipmentName = hospitalContract.getEquipmentName();
        this.equipmentQuantity = hospitalContract.getEquipmentQuantity();
        this.deliveryDate = hospitalContract.getDeliveryDate();
        this.status = hospitalContract.getStatus();
    }

    public HospitalContract.HospitalContractStatus getStatus() {
        return status;
    }

    public void setStatus(HospitalContract.HospitalContractStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Integer getEquipmentQuantity() {
        return equipmentQuantity;
    }

    public void setEquipmentQuantity(Integer equipmentQuantity) {
        this.equipmentQuantity = equipmentQuantity;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "HospitalContract{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", hospitalAddress='" + hospitalAddress + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", equipmentQuantity=" + equipmentQuantity +
                ", deliveryDate=" + deliveryDate +
                ", status=" + status +
                '}';
    }
}
