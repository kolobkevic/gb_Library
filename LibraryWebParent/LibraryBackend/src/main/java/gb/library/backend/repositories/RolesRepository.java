package gb.library.backend.repositories;

import gb.library.common.entities.Role;
import gb.library.common.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleType(RoleType roleType);
}
