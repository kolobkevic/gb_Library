package gb.library.admin.roles;

import gb.library.admin.utils.CheckUniqueResponseStatusHelper;
import gb.library.common.entities.Role;
import gb.library.common.enums.RoleType;

import gb.library.common.exceptions.ObjectInDBNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolesServiceTest {

    @Mock
    private RolesRepository repository;
    @InjectMocks
    private RolesService service;


    private static Role getNewRole() {
        Role role = new Role();
        role.setRoleType(RoleType.ADMIN);
        role.setName("SuperAdmin");
        role.setDescription("Самый главный и крутой дядька");
        return role;
    }

    private static Role getExistedRole() {
        Role existedRole = new Role();
        existedRole.setId(1);
        existedRole.setRoleType(RoleType.EMPLOYEE);
        existedRole.setName("Admin");
        existedRole.setDescription("Старый бородатый админ");
        return existedRole;
    }

    @DisplayName("JUnit тест для save() метода - create(role) вариант")
    @Test
    public void testCreateRole(){
        Role role = getNewRole();

        when(repository.save(role)).thenReturn(new Role());
        service.save(role);

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }



    @DisplayName("JUnit тест для save() метода - update(role) вариант - ОБНОВЛЕНИЕ")
    @Test
    public void testUpdateRoleSuccess(){
        int roleId = 1;

        Role role = getNewRole();
        role.setId(roleId);

        Role existedRole = getExistedRole();


        lenient().when(repository.save(role)).thenReturn(getNewRole());
        lenient().when(repository.findById(role.getId())).thenReturn(Optional.of(existedRole));

        service.save(role);

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(repository, Mockito.times(1)).findById(roleId);
    }

    @DisplayName("JUnit тест для save() метода - update(role) вариант - Ошибка \"Не найден по id\"")
    @Test
    public void testUpdateRoleException(){
        int roleId = 1;

        Role role = getNewRole();
        role.setId(roleId);

        lenient().when(repository.save(role)).thenReturn(getNewRole());
        lenient().when(repository.findById(role.getId())).thenThrow(new ObjectInDBNotFoundException("Error in test", "Test"));

        ObjectInDBNotFoundException ex = org.junit.jupiter.api.Assertions.assertThrows(ObjectInDBNotFoundException.class,
                () -> service.save(role));

        System.out.println(ex.getMessage());

        Assertions.assertThat(ex.getEntityName()).isEqualTo("Test");

        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(repository, Mockito.times(1)).findById(role.getId());

    }

    @DisplayName("Тест проверки уникальности роли - статус ОК")
    @Test
    public void testCheckUniqueStatusOK(){
        Integer id = 2;
        String title = "Sysadmin";
        String type = "ADMIN";

        Role exRole = getNewRole();
        exRole.setId(2);
        when(repository.findByNameAndRoleType(title, RoleType.ADMIN)).thenReturn(exRole);

        String status = service.checkUnique(id, title, type);
        System.out.println(status);

        Assertions.assertThat(status).isEqualTo(CheckUniqueResponseStatusHelper.OK);
    }

    @DisplayName("Тест проверки уникальности роли - статус Duplicated")
    @Test
    public void testCheckUniqueStatusDuplicated(){
        Integer id = 2;
        String title = "Sysadmin";
        String type = "ADMIN";

        Role exRole = getNewRole();
        exRole.setId(3);
        when(repository.findByNameAndRoleType(title, RoleType.ADMIN)).thenReturn(exRole);

        String status = service.checkUnique(id, title, type);
        System.out.println(status);

        Assertions.assertThat(status).isEqualTo(CheckUniqueResponseStatusHelper.DUPLICATED);
    }

    @DisplayName("Тест проверки уникальности роли - ОШИБКА при проверке")
    @Test
    public void testCheckUniqueStatusError(){
        Integer id = 2;
        String title = "Sysadmin";
        String type = "ADMIN";

        Role exRole = getNewRole();
        exRole.setId(2);
        when(repository.findByNameAndRoleType(title, RoleType.ADMIN)).thenThrow(new IllegalArgumentException());

        String status = service.checkUnique(id, title, type);
        System.out.println(status);

        Assertions.assertThat(status).isEqualTo(CheckUniqueResponseStatusHelper.ERROR);
    }

}