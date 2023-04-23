package gb.library.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books_wishlist")
@NoArgsConstructor
@Getter
@Setter
public class BooksWishlist extends IdBasedEntity{
    @ManyToOne()
    @JoinColumn(name = "book_id", nullable = false)
    private WorldBook book;
    @ManyToOne()
    @JoinColumn(name = "reader_id", nullable = false)
    private User user;

}
