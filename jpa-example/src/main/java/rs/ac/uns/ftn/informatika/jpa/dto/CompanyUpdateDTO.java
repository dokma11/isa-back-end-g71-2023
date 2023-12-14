package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Company;

public class CompanyUpdateDTO {
    private String name;
    private String address;
    private double longitude;
    private double latitude;
    private String description;
    private double averageGrade;

    public CompanyUpdateDTO(){
    }

    public CompanyUpdateDTO(Company company){
        this.name = company.getName();
        this.address = company.getAddress();
        this.longitude = company.getLongitude();
        this.latitude = company.getLatitude();
        this.description = company.getDescription();
        this.averageGrade = company.getAverageGrade();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

}
