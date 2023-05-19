package gb.library.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "reserved_books")
@NoArgsConstructor
@Getter
@Setter
public class ReservedBook extends IdBasedEntity{
    @ManyToOne
    @JoinColumn(name = "world_book_id", nullable = false)
    private WorldBook worldBook;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne
    @JoinColumn(name = "library_book_id")
    private LibraryBook libraryBook;


}
