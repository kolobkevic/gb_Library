package gb.library.admin.users;

import gb.library.common.dtos.UserDTO;
import gb.library.common.entities.User;
import gb.library.pd.openapi.client.pd.model.ReaderPatchRequest;
import gb.library.pd.openapi.client.pd.model.ReaderPostRequest;
import gb.library.pd.openapi.client.pd.model.ReaderResponse;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {

    public UserDTO toDto(User user, ReaderResponse reader) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setRoles(user.getRoles());

        userDTO.setBirthday(reader.getBirthday());
        userDTO.setPhone1(reader.getPhone1());
        userDTO.setPhone2(reader.getPhone2());
        userDTO.setAddress(reader.getAddress());
        userDTO.setPassport(reader.getPassport());

        return userDTO;
    }


    public User dtoToUser(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(userDTO.isEnabled());
        user.setRoles(userDTO.getRoles());

        return user;
    }

    public ReaderPostRequest dtoToPostRequest(UserDTO userDTO) {
        ReaderPostRequest request = new ReaderPostRequest();

        request.setName(userDTO.getFirstName());
        request.setSurname(userDTO.getLastName());
        request.setBirthday(userDTO.getBirthday());
        request.setEmail(userDTO.getEmail());
        request.setPhone1(userDTO.getPhone1());
        request.setPhone2(userDTO.getPhone2());
        request.setAddress(userDTO.getAddress());
        request.setPassport(userDTO.getPassport());

        return request;
    }


    public ReaderPatchRequest dtoToPatchRequest(UserDTO userDTO) {
        ReaderPatchRequest request = new ReaderPatchRequest();

        request.setName(userDTO.getFirstName());
        request.setSurname(userDTO.getLastName());
        request.setBirthday(userDTO.getBirthday());
        request.setEmail(userDTO.getEmail());
        request.setPhone1(userDTO.getPhone1());
        request.setPhone2(userDTO.getPhone2());
        request.setAddress(userDTO.getAddress());
        request.setPassport(userDTO.getPassport());

        return request;
    }
}
