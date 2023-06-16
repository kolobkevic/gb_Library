package gb.library.admin.roles;

import gb.library.admin.utils.paging.SearchRepository;
import gb.library.common.entities.Role;
import gb.library.common.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends SearchRepository<Role, Integer> {

    Role save(Role role);

    @Query("SELECT r FROM Role r WHERE r.name LIKE %?1% OR r.description LIKE %?1%")
    Page<Role> getAllWithFilter(String keyword, Pageable pageable);

    Role findByNameAndRoleType(String name, RoleType type);
}
