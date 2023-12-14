package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.EquipmentQuantity;

public class EquipmentQuantityDTO {
    private Integer id;
    private AppointmentResponseDTO appointment;
    private Integer equipmentId;
    private Integer quantity;

    public EquipmentQuantityDTO(){

    }

    public EquipmentQuantityDTO(EquipmentQuantity eq){
        this.id = eq.getId();
        this.appointment = new AppointmentResponseDTO(eq.getAppointment());
        this.equipmentId = eq.getEquipmentId();
        this.quantity = eq.getQuantity();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppointmentResponseDTO getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentResponseDTO appointment) {
        this.appointment = appointment;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
