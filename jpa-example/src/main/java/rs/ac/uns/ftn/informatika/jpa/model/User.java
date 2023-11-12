package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Inheritance;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy =TABLE_PER_CLASS)
public class User {
    public enum UserRole {REGISTERED_USER, COMPANY_ADMINISTRATOR, SYSTEM_ADMINISTRATOR}
    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    private Integer id;

    @Column(name="name", unique=false, nullable=false)
    private String name;

    @Column(name="surname", unique=false, nullable=false)
    private String surname;

    @Email
    @Pattern(regexp = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9]"
            + "(?:[A-Za-z0-9-]*[A-Za-z0-9])?",
            message = "{invalid.email}")
    @Column(name="email", unique=true, nullable=false)
    private String email;

    @Column(name = "password",  unique = false, nullable = false)
    private String password;

    @Column(name = "company_information",  unique = false, nullable = false)
    private String CompanyInformation;

    @Column(name="telephone_number", unique=false, nullable=false)
    private String telephoneNumber;

    @Column(name="city", unique=false, nullable=false)
    private String city;

    @Column(name="state", unique=false, nullable=false)
    private String state;

    @Column(name="role", unique=false, nullable=false)
    private UserRole role;

    @Column(name="profession", unique=false, nullable=false)
    private String profession;

    public User() {
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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
}
