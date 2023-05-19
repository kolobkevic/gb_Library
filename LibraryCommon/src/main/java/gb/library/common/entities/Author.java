package gb.library.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@Getter
@Setter
public class Author extends IdBasedEntity{
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    /*
     * CONSTRUCTORS
     */

    /*
     * TRANSIENT FIELDS
     */
    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /*
     * OVERWRITE METHODS
     */

    /*
     * METHODS
     */
    public void setFullName(String fullName){
        setFirstName(fullName.split(" ")[0]);
        setLastName(fullName.split(" ")[1]);
    }
}
