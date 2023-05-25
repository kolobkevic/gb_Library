package gb.library.reader.controllers;

import gb.library.common.entities.RegistrationType;
import gb.library.reader.converters.UserConverter;
import gb.library.reader.dtos.UserReaderDto;
import gb.library.reader.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController("readerUserController")
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;

    @GetMapping("/{userId}")
    public UserReaderDto findById(@PathVariable int userId) {
        return userConverter.entityToDto(userService.findById(userId));
    }

    @PostMapping("/create")
    public UserReaderDto create(@RequestBody UserReaderDto userDto, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        return userConverter
                .entityToDto(userService.create(userConverter.dtoToEntity(userDto), RegistrationType.MANUAL, request));
    }

    @PutMapping
    public UserReaderDto update(@RequestBody UserReaderDto userDto) {
        return userConverter.entityToDto(userService.update(userConverter.dtoToEntity(userDto)));
    }

    @DeleteMapping("/delete/{userId}")
    public void delete(@PathVariable int userId) {
        userService.delete(userId);
    }

    @GetMapping("/check_email")
    public String checkEmailUnique(@RequestParam String email){
        return userService.isEmailUnique(email) ? "Unique" : "Duplicated";
    }
}
