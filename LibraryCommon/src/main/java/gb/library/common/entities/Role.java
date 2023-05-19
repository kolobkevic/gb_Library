package gb.library.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
public class Role extends IdBasedEntity{
    @Column(length = 30, nullable = false, unique = true)
    private String name;
    @Column(length = 150, nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", length = 8, nullable = false)
    private RoleType roleType;

}
