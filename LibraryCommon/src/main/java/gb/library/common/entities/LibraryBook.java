package gb.library.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "library_books")
@NoArgsConstructor
@Getter
@Setter
public class LibraryBook extends IdBasedEntity{
    @ManyToOne
    @JoinColumn(name = "world_book_id", nullable = false)
    private WorldBook worldBook;
    @Column(name = "publisher", length = 40, nullable = false)
    private String publisher;
    //Международный стандартный книжный номер (англ. International Standard Book Number, сокращённо — англ. ISBN)
    @Column(name = "isbn", length = 13, nullable = false)
    private String isbn;
    @Column(name = "inventory_number", length = 20, nullable = false, unique = true)
    private String inventoryNumber;
    @Column
    private boolean available;


    @ManyToOne
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage placedAt;

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
