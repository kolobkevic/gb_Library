package gb.library.admin.users;

import gb.library.common.entities.RegistrationType;
import gb.library.common.entities.Role;
import gb.library.common.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


import java.util.*;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepo;

    @Autowired
    private TestEntityManager entityManager;


    private static User generateNewUser(Set<Role> userRoles) {
        User newUser = new User();
        newUser.setEmail("admin@library.com");
        newUser.setRoles(userRoles);
        newUser.setPassword("123456");
        newUser.setEnabled(true);
        newUser.setFirstName("Name");
        newUser.setLastName("Surname");
        newUser.setRegistrationType(RegistrationType.MANUAL);
        return newUser;
    }

    @Test
    public void testSaveUserWithOneRole(){
        Role superAdmin = entityManager.find(Role.class, 1);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(superAdmin);

        User newUser = generateNewUser(userRoles);

        User savedUser = usersRepo.save(newUser);

        Assertions.assertThat(savedUser.getId()).isNotNull();
    }


    @Test
    public void testSaveUserWithMultipleRoles(){
        Role superAdmin = entityManager.find(Role.class, 1);
        Role admin = entityManager.find(Role.class, 2);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(superAdmin);
        userRoles.add(admin);

        User newUser = generateNewUser(userRoles);

        User savedUser = usersRepo.save(newUser);

        Assertions.assertThat(savedUser.getId()).isNotNull();
        Assertions.assertThat(savedUser.getRoles().size()).isEqualTo(2);
    }
}