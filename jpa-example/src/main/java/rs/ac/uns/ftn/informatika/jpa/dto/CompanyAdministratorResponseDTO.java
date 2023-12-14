package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.CompanyAdministrator;
import rs.ac.uns.ftn.informatika.jpa.model.User;

public class CompanyAdministratorResponseDTO {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String CompanyInformation;
    private String telephoneNumber;
    private String city;
    private String state;
    private User.UserRole role;
    private String profession;
    private CompanyResponseDTO company;

    public CompanyAdministratorResponseDTO() {
    }

    public CompanyAdministratorResponseDTO(CompanyAdministrator administrator) {
        this.id = administrator.getId();
        this.name = administrator.getName();
        this.surname = administrator.getSurname();
        this.email = administrator.getEmail();
        this.password = administrator.getPassword();
        this.CompanyInformation = administrator.getCompanyInformation();
        this.telephoneNumber = administrator.getTelephoneNumber();
        this.city = administrator.getCity();
        this.state = administrator.getState();
        this.role = administrator.getRole();
        this.profession = administrator.getProfession();
        this.company = new CompanyResponseDTO(administrator.getCompany());
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyInformation() {
        return CompanyInformation;
    }

    public void setCompanyInformation(String companyInformation) {
        CompanyInformation = companyInformation;
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

    public User.UserRole getRole() {
        return role;
    }

    public void setRole(User.UserRole role) {
        this.role = role;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public CompanyResponseDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyResponseDTO company) {
        this.company = company;
    }
}
