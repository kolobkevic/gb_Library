package gb.library.reader.converters;

import gb.library.common.entities.User;
import gb.library.reader.dtos.UserDto;
import gb.library.reader.services.ReservedBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("readerUserConverter")
@RequiredArgsConstructor
public class UserConverter {
    private final ReservedBooksService service;

    public UserDto entityToDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getFullName());
    }

    public User dtoToEntity(UserDto userDto) {
        User user = service.findByUserId(userDto.getUserId());
        user.setEmail(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        return user;
    }
}
