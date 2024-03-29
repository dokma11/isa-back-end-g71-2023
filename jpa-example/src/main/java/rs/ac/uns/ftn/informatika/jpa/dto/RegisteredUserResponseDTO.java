package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.RegisteredUser;

public class RegisteredUserResponseDTO {

    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String telephoneNumber;
   private String city;
   private String state;
   private String profession;
   private String role;

   private int points;
   private RegisteredUser.User_Category category;

   private String companyInformation;

    public RegisteredUserResponseDTO() {
    }

    public RegisteredUserResponseDTO(RegisteredUser user){
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        email = user.getUsername();
        telephoneNumber = user.getTelephoneNumber();
        city = user.getCity();
        state = user.getState();
        profession = user.getProfession();
        role = user.getRole().getName();
        category = user.getCategory();
        points = user.getPoints();
        password = user.getPassword();
        companyInformation = user.getCompanyInformation();


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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public RegisteredUser.User_Category getCategory() {
        return category;
    }

    public void setCategory(RegisteredUser.User_Category category) {
        this.category = category;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyInformation() {
        return companyInformation;
    }

    public void setCompanyInformation(String companyInformation) {
        this.companyInformation = companyInformation;
    }
}
