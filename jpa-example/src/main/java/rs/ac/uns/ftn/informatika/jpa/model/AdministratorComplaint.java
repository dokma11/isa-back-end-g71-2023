package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;

@Entity
public class AdministratorComplaint {

    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    private Integer id;

    @Column(name = "text", unique = false, nullable = true)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comapny_administator_id")
    private CompanyAdministrator companyAdministrator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registered_user_id")
    private RegisteredUser registeredUser;

    @OneToOne(mappedBy = "administratorComplaint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_complaint_Response_id")
    AdministratorComplaintResponse administratorComplaintResponse;

    public AdministratorComplaint(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CompanyAdministrator getCompanyAdministrator() {
        return companyAdministrator;
    }

    public void setCompanyAdministrator(CompanyAdministrator companyAdministrator) {
        this.companyAdministrator = companyAdministrator;
    }

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public AdministratorComplaintResponse getAdministratorComplaintResponse() {
        return administratorComplaintResponse;
    }

    public void setAdministratorComplaintResponse(AdministratorComplaintResponse administratorComplaintResponse) {
        this.administratorComplaintResponse = administratorComplaintResponse;
    }
}
