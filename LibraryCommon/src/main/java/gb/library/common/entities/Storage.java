package gb.library.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "storages")
@NoArgsConstructor
@Getter
@Setter
public class Storage extends IdBasedEntity{
    @Column(name = "zone", nullable = false, length = 40)
    private String zone;
    @Column(name = "sector", nullable = false, length = 10)
    private String sector;
    //шкаф
    //private String closet;

    private boolean available;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "placedAt")   //вот тут вопросик правильно ли
    private List<LibraryBook> booksList;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

}
