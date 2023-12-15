package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.EquipmentQuantity;

public class EquipmentQuantityDTO {
    private Integer id;
    private Integer appointmentId;
    private Integer equipmentId;
    private Integer quantity;

    public EquipmentQuantityDTO(){

    }

    public EquipmentQuantityDTO(EquipmentQuantity eq){
        this.id = eq.getId();
        this.appointmentId = eq.getAppointment().getId();
        this.equipmentId = eq.getEquipmentId();
        this.quantity = eq.getQuantity();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
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
