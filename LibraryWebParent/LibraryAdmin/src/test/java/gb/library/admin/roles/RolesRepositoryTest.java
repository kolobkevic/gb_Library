package gb.library.admin.roles;

import gb.library.common.entities.Role;
import gb.library.common.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
public class RolesRepositoryTest {

    @Autowired
    private RolesRepository repository;

    @Test
    void testSave(){
        Role role = new Role();
        role.setRoleType(RoleType.ADMIN);
        role.setName("Super Admin");
        role.setDescription("Самый главный и крутой дядька");
        repository.save(role);

        role = new Role();
        role.setRoleType(RoleType.ADMIN);
        role.setName("Admin");
        role.setDescription("Старый бородатый админ");
        repository.save(role);

        List<Role> rolesList = repository.findAll();
        assertThat(rolesList.size()).isGreaterThan(1);
    }

    @Test
    void testGetPage(){
        Pageable pageable = PageRequest.of(0, 5);
        Page<Role> rolePage = repository.getAllWithFilter("Admin", pageable);
        System.out.println("Всего страниц:" + rolePage.getTotalPages());
        System.out.println("Всего элементов:" + rolePage.getTotalElements());

        assertThat(rolePage.getTotalElements()).isGreaterThan(0);
    }

    @Test
    void testFindByNameAndRoleType(){
        RoleType roleType = RoleType.ADMIN;
        String roleName = "Super Admin";// <-- description
        String name = "Root";           // <-- name
        Role role = repository.findByNameAndRoleType(name, roleType);
        assertThat(role).isNotNull();
    }
}
