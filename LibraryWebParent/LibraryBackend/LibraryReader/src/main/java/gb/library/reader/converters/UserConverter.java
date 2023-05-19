package gb.library.reader.converters;

import gb.library.common.entities.User;
import gb.library.reader.dtos.UserReaderDto;
import gb.library.reader.services.ReservedBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("readerUserConverter")
@RequiredArgsConstructor
public class UserConverter {
    private final ReservedBooksService service;

    public UserReaderDto entityToDto(User user) {
        return new UserReaderDto(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword());
    }

    public User dtoToEntity(UserReaderDto userDto) {
        User user = service.findByUserId(userDto.getUserId());
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
