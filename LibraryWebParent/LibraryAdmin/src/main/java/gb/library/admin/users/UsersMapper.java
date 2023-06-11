package gb.library.admin.users;

import gb.library.common.dtos.UserDTO;
import gb.library.common.entities.User;
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

}
