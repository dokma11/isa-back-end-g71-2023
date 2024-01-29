package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;

@Entity
public class AdministratorComplaintResponse {

    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "administrator_complaint_id")
    private AdministratorComplaint administratorComplaint;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "system_administrator_id")
    private SystemAdministrator systemAdministrator;

    @Column(name = "comment", nullable = false, unique = false)
    private String comment;

    public AdministratorComplaintResponse(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AdministratorComplaint getAdministratorComplaint() {
        return administratorComplaint;
    }

    public void setAdministratorComplaint(AdministratorComplaint administratorComplaint) {
        this.administratorComplaint = administratorComplaint;
    }

    public SystemAdministrator getSystemAdministrator() {
        return systemAdministrator;
    }

    public void setSystemAdministrator(SystemAdministrator systemAdministrator) {
        this.systemAdministrator = systemAdministrator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
