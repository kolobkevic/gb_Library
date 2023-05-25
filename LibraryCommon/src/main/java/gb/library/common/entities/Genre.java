package gb.library.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "genres")
@NoArgsConstructor
@Getter
@Setter
public class Genre extends IdBasedEntity{
    @Column(name = "name", length = 40, nullable = false, unique = true)
    private String name;
    @Column(name = "description", length = 200)
    private String description;

    /*
     * CONSTRUCTORS
     */

    /*
     * TRANSIENT FIELDS
     */

    /*
     * OVERWRITE METHODS
     */

    /*
     * METHODS
     */
}
