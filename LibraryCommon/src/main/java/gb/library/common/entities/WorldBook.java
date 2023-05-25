package gb.library.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;


@Entity
@Table(name = "world_books")
@NoArgsConstructor
@Getter
@Setter
public class WorldBook extends IdBasedEntity{
    @Column(name = "title", length = 128, nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    @Column(name = "age_rating", length = 4, nullable = false)
    private AgeRating ageRating;
    @Column(name = "description", length = 250)
    private String description;

    /*
     * CONSTRUCTORS
     */

    /*
     * TRANSIENT FIELDS
     */
    @Transient
    public String getPublicTitle(){
        return "\"" + title + "\" - " + author.getFullName();
    }

    /*
     * OVERWRITE METHODS
     */

    /*
     * METHODS
     */
}
