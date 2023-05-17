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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name) && Objects.equals(description, role.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return name + " ( " + description + " )";
    }
}
