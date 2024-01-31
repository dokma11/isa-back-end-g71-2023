package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;

@Entity
public class CompanyComplaint {

    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    private Integer id;

    @Column(name = "text", unique = false, nullable = true)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registered_user_id")
    private RegisteredUser registeredUser;

    @OneToOne(mappedBy = "companyComplaint", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_complaint_response_id")
    CompanyComplaintResponse companyComplaintResponse;

    public CompanyComplaint(){

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public CompanyComplaintResponse getCompanyComplaintResponse() {
        return companyComplaintResponse;
    }

    public void setCompanyComplaintResponse(CompanyComplaintResponse companyComplaintResponse) {
        this.companyComplaintResponse = companyComplaintResponse;
    }

}
