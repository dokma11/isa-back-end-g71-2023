package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;

public class Equipment {

    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    private Integer id;
    @Column(name="name", unique=false, nullable=false)
    private String name;
    @Column(name="description", unique=false, nullable=false)
    private String description;
    @Column(name = "type",  unique = false, nullable = false)
    private String type;

    @Column(name = "grade",  unique = false, nullable = false)
    private int grade;

    public Equipment() {
    }

    public Equipment(Integer id, String name, String description, String type, int grade) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.grade = grade;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
