package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class HospitalContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "companyName", nullable = false)
    private String companyName;

    @Column(name = "companyAddress", nullable = false)
    private String companyAddress;

    @Column(name = "hospitalName", nullable = false)
    private String hospitalName;

    @Column(name = "hospitalAddress", nullable = false)
    private String hospitalAddress;

    @Column(name = "equipmentName", nullable = false)
    private String equipmentName;

    @Column(name = "equipmentQuantity", nullable = false)
    private Integer equipmentQuantity;

    @Column(name = "deliveryDate", nullable = false)
    private LocalDate deliveryDate;

    public enum HospitalContractStatus {NEW, CANCELED}

    @Column(name="status", nullable = false)
    private HospitalContract.HospitalContractStatus status;

    public HospitalContract() {
    }

    public HospitalContract(String id, String companyName, String companyAddress, String hospitalName, String hospitalAddress, String equipmentName, String equipmentQuantity, String deliveryDate, HospitalContract.HospitalContractStatus status) {
        this.id = Integer.parseInt(id);
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.equipmentName = equipmentName;
        this.equipmentQuantity = Integer.parseInt(equipmentQuantity);
        this.deliveryDate = LocalDate.parse(deliveryDate);
        this.status = status;
    }

    public HospitalContractStatus getStatus() {
        return status;
    }

    public void setStatus(HospitalContractStatus status) {
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
