package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Equipment;

public class EquipmentResponseDTO {
    private int id;
    private String name;
    private String description;
    private String type;
    private double grade;
    private int quantity;
    private CompanyResponseDTO company;

    public EquipmentResponseDTO(){
    }

    public EquipmentResponseDTO(Equipment equipment){
        this.id = equipment.getId();
        this.name = equipment.getName();
        this.description = equipment.getDescription();
        this.type = equipment.getType();
        this.grade = equipment.getGrade();
        this.quantity = equipment.getQuantity();
        this.company = new CompanyResponseDTO(equipment.getCompany());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CompanyResponseDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyResponseDTO company) {
        this.company = company;
    }
}
