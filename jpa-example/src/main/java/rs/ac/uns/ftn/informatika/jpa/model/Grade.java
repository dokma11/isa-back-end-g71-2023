package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Grade {


    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registeredUser_id")
    private RegisteredUser registeredUser;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "reasonForGrade", nullable = false)
    private String reasonsForGrade;
    @Column(name = "personalReason", nullable = false)
    private String personalReason;
    @Column(name = "gradeValue", nullable = false)
    private int gradeValue;

    public Grade(String reasonsForGrade, String personalReason, int gradeValue) {
        this.reasonsForGrade = reasonsForGrade;
        this.personalReason = personalReason;
        this.gradeValue = gradeValue;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getReasonsForGrade() {
        return reasonsForGrade;
    }

    public void setReasonsForGrade(String reasonsForGrade) {
        this.reasonsForGrade = reasonsForGrade;
    }

    public String getPersonalReason() {
        return personalReason;
    }

    public void setPersonalReason(String personalReason) {
        this.personalReason = personalReason;
    }

    public int getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(int gradeValue) {
        this.gradeValue = gradeValue;
    }
}
