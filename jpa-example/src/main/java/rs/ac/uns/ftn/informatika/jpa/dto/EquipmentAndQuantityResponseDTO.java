package rs.ac.uns.ftn.informatika.jpa.dto;

public class EquipmentAndQuantityResponseDTO {
    private int id;
    private String name;
    private String description;
    private String type;
    private int quantity;

    public EquipmentAndQuantityResponseDTO(int id, String name, String description, String type, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
