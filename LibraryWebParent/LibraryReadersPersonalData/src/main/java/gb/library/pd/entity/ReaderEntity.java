package gb.library.pd.entity;

import gb.library.pd.entity.embedded.ContactInfo;
import gb.library.pd.entity.embedded.MainInfo;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "readers")
public class ReaderEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reader_id")
    private Long readerId;

    @Embedded
    private MainInfo mainInfo;

    @Embedded
    private ContactInfo contactInfo;


}
