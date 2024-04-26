package gb.library.backend.admin.users;

import gb.library.common.dtos.UserDataDTO;
import gb.library.common.entities.Role;
import gb.library.common.entities.User;
import gb.library.common.enums.RoleType;
import gb.library.pd.openapi.client.pd.model.ReaderPatchRequest;
import gb.library.pd.openapi.client.pd.model.ReaderPostRequest;
import gb.library.pd.openapi.client.pd.model.ReaderResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;


class UserDataMapperTest {

    private UserDataMapper mapper;

    @BeforeEach
    public void setup() {
        this.mapper = new UserDataMapper();
    }


    private Set<Role> generateRoles() {
        Role role1 = new Role();
        role1.setId(987);
        role1.setName("Role_Admin");
        role1.setDescription("Admin Role");
        role1.setRoleType(RoleType.ADMIN);
        role1.setCreatedAt(Instant.now());
        role1.setUpdatedAt(Instant.now());

        Role role2 = new Role();
        role2.setId(647);
        role2.setName("Role_User");
        role2.setDescription("User Role");
        role2.setRoleType(RoleType.USER);
        role2.setCreatedAt(Instant.now());
        role2.setUpdatedAt(Instant.now());

        Role role3 = new Role();
        role3.setId(107);
        role3.setName("Role_Employer");
        role3.setDescription("Employer Role");
        role3.setRoleType(RoleType.EMPLOYEE);
        role3.setCreatedAt(Instant.now());
        role3.setUpdatedAt(Instant.now());

        return Set.of(role1, role2, role3);
    }


    private UserDataDTO generateUserDataDTO() {
        UserDataDTO userDataDTO = new UserDataDTO();

        userDataDTO.setId(1258);
        userDataDTO.setFirstName("Name");
        userDataDTO.setLastName("Surname");
        userDataDTO.setEmail("surname@library.com");
        userDataDTO.setPassword("password");
        userDataDTO.setEnabled(true);
        userDataDTO.setRoles(generateRoles());
        userDataDTO.setBirthday(LocalDate.of(2000, Month.JANUARY, 15));
        userDataDTO.setPhone1("8-654-574-56-21");
        userDataDTO.setPhone2("8-123-847-56-21");
        userDataDTO.setAddress("г. Санкт-Петербург, ул. Кошкина, д.7, кв.12");
        userDataDTO.setPassport("15 50 654987");

        return userDataDTO;
    }


    private User generateUser() {
        User user = new User();

        user.setId(1258);
        user.setFirstName("Name");
        user.setLastName("Surname");
        user.setEmail("surname@library.com");
        user.setPassword("password");
        user.setEnabled(true);
        user.setRoles(generateRoles());

        return user;
    }


    private ReaderResponse generateReaderResponse() {
        ReaderResponse readerResponse = new ReaderResponse();

        readerResponse.setReaderId(1258484L);
        readerResponse.setName("Name2");
        readerResponse.setSurname("Surname2");
        readerResponse.setEmail("surname2@library.com");
        readerResponse.setBirthday(LocalDate.of(2000, Month.JANUARY, 15));
        readerResponse.setPhone1("8-654-574-56-21");
        readerResponse.setPhone2("8-123-847-56-21");
        readerResponse.setAddress("г. Санкт-Петербург, ул. Кошкина, д.7, кв.12");
        readerResponse.setPassport("15 50 654987");

        return readerResponse;
    }


    @Test
    public void testToDtoAndReaderResponseNull() {
        User user = generateUser();
        ReaderResponse readerResponse = new ReaderResponse();

        UserDataDTO userDataDTO = mapper.toDto(user, readerResponse);

        Assertions.assertEquals(user.getId(), userDataDTO.getId());
        Assertions.assertEquals(user.getFirstName(), userDataDTO.getFirstName());
        Assertions.assertEquals(user.getLastName(), userDataDTO.getLastName());
        Assertions.assertEquals(user.getEmail(), userDataDTO.getEmail());
        Assertions.assertEquals(user.getPassword(), userDataDTO.getPassword());
        Assertions.assertEquals(user.isEnabled(), userDataDTO.isEnabled());
        Assertions.assertEquals(user.getRoles(), userDataDTO.getRoles());

        Assertions.assertNull(userDataDTO.getBirthday());
        Assertions.assertNull(userDataDTO.getPhone1());
        Assertions.assertNull(userDataDTO.getPhone2());
        Assertions.assertNull(userDataDTO.getAddress());
        Assertions.assertNull(userDataDTO.getPassport());
    }


    @Test
    public void testToDto() {
        User user = generateUser();
        ReaderResponse readerResponse = generateReaderResponse();

        UserDataDTO userDataDTO = mapper.toDto(user, readerResponse);

        Assertions.assertEquals(user.getId(), userDataDTO.getId());
        Assertions.assertEquals(user.getFirstName(), userDataDTO.getFirstName());
        Assertions.assertEquals(user.getLastName(), userDataDTO.getLastName());
        Assertions.assertEquals(user.getEmail(), userDataDTO.getEmail());
        Assertions.assertEquals(user.getPassword(), userDataDTO.getPassword());
        Assertions.assertEquals(user.isEnabled(), userDataDTO.isEnabled());
        Assertions.assertEquals(user.getRoles(), userDataDTO.getRoles());

        Assertions.assertEquals(userDataDTO.getBirthday(), readerResponse.getBirthday());
        Assertions.assertEquals(userDataDTO.getPhone1(), readerResponse.getPhone1());
        Assertions.assertEquals(userDataDTO.getPhone2(), readerResponse.getPhone2());
        Assertions.assertEquals(userDataDTO.getAddress(), readerResponse.getAddress());
        Assertions.assertEquals(userDataDTO.getPassport(), readerResponse.getPassport());
    }


    @Test
    public void testDtoToUser() {
        UserDataDTO userDataDTO = generateUserDataDTO();
        User user = mapper.dtoToUser(userDataDTO);

        Assertions.assertEquals(user.getId(), userDataDTO.getId());
        Assertions.assertEquals(user.getFirstName(), userDataDTO.getFirstName());
        Assertions.assertEquals(user.getLastName(), userDataDTO.getLastName());
        Assertions.assertEquals(user.getEmail(), userDataDTO.getEmail());
        Assertions.assertEquals(user.getPassword(), userDataDTO.getPassword());
        Assertions.assertEquals(user.isEnabled(), userDataDTO.isEnabled());
        Assertions.assertEquals(user.getRoles(), userDataDTO.getRoles());
    }


    @Test
    public void testDtoToPostRequest() {
        UserDataDTO userDataDTO = generateUserDataDTO();
        ReaderPostRequest reader = mapper.dtoToPostRequest(userDataDTO);

        Assertions.assertEquals(reader.getName(), userDataDTO.getFirstName());
        Assertions.assertEquals(reader.getSurname(), userDataDTO.getLastName());
        Assertions.assertEquals(reader.getBirthday(), userDataDTO.getBirthday());
        Assertions.assertEquals(reader.getEmail(), userDataDTO.getEmail());
        Assertions.assertEquals(reader.getPhone1(), userDataDTO.getPhone1());
        Assertions.assertEquals(reader.getPhone2(), userDataDTO.getPhone2());
        Assertions.assertEquals(reader.getAddress(), userDataDTO.getAddress());
        Assertions.assertEquals(reader.getPassport(), userDataDTO.getPassport());
    }


    @Test
    public void testDtoToPatchRequest() {
        UserDataDTO userDataDTO = generateUserDataDTO();
        ReaderPatchRequest reader = mapper.dtoToPatchRequest(userDataDTO);

        Assertions.assertEquals(reader.getName(), userDataDTO.getFirstName());
        Assertions.assertEquals(reader.getSurname(), userDataDTO.getLastName());
        Assertions.assertEquals(reader.getBirthday(), userDataDTO.getBirthday());
        Assertions.assertEquals(reader.getEmail(), userDataDTO.getEmail());
        Assertions.assertEquals(reader.getPhone1(), userDataDTO.getPhone1());
        Assertions.assertEquals(reader.getPhone2(), userDataDTO.getPhone2());
        Assertions.assertEquals(reader.getAddress(), userDataDTO.getAddress());
        Assertions.assertEquals(reader.getPassport(), userDataDTO.getPassport());
    }
}