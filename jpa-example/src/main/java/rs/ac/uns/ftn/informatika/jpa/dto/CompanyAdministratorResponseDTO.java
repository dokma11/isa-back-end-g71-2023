package rs.ac.uns.ftn.informatika.jpa.dto;


import rs.ac.uns.ftn.informatika.jpa.model.CompanyAdministrator;

public class CompanyAdministratorResponseDTO {

    private Integer id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String CompanyInformation;
    private String telephoneNumber;
    private String city;
    private String state;
//    private User.UserRole role;
    private java.lang.String role;
    private String profession;
    private CompanyResponseDTO company;

    public CompanyAdministratorResponseDTO() {
    }

    public CompanyAdministratorResponseDTO(CompanyAdministrator administrator) {
        this.id = administrator.getId();
        this.name = administrator.getName();
        this.surname = administrator.getSurname();
        this.username = administrator.getUsername();
        this.password = administrator.getPassword();
        this.CompanyInformation = administrator.getCompanyInformation();
        this.telephoneNumber = administrator.getTelephoneNumber();
        this.city = administrator.getCity();
        this.state = administrator.getState();
        this.role = administrator.getRole().getName();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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
