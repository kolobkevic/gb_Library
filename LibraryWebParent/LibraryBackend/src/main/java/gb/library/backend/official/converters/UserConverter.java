package gb.library.backend.official.converters;

import gb.library.backend.official.dtos.UserDTO;
import gb.library.common.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    public UserDTO entityToDto(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword());
    }

    public User dtoToEntity(UserDTO userDto) {
        User user = new User();
        if (userDto.getUserId() != 0) {
            user.setId(userDto.getUserId());
        }
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
