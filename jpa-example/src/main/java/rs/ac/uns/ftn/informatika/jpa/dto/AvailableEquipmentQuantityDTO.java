package rs.ac.uns.ftn.informatika.jpa.dto;

public class AvailableEquipmentQuantityDTO {
    private Integer equipmentId;
    private int availableQuantity;

    public AvailableEquipmentQuantityDTO(Integer equipmentId, int availableQuantity) {
        this.equipmentId = equipmentId;
        this.availableQuantity = availableQuantity;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

}
