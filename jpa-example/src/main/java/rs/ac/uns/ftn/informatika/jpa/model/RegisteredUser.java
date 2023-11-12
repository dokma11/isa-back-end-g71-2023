package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="registered_user")


public class RegisteredUser extends User{
    public enum User_Category {BRONSE, SILVER, GOLD}
    @Column(name = "points", unique = false, nullable = true)
    private int points;

    @Column(name = "category", unique = false, nullable = true)
    private User_Category category;

    public RegisteredUser() {
        // predefined values
        points = 0;
        category = User_Category.BRONSE;
        setRole(UserRole.REGISTERED_USER);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public User_Category getCategory() {
        return category;
    }

    public void setCategory(User_Category category) {
        this.category = category;
    }
}
