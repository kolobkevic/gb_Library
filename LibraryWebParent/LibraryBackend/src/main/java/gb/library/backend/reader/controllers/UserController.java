package gb.library.backend.reader.controllers;

import gb.library.common.enums.RegistrationType;
import gb.library.reader.converters.UserConverter;
import gb.library.reader.dtos.UserReaderDto;
import gb.library.reader.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Set;

@RestController("readerUserController")
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;


    @GetMapping
    public UserReaderDto findByLogin(@RequestHeader String username) {
        return userConverter.entityToDto(userService.findByEmail(username));
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

    @DeleteMapping
    public void delete(@RequestHeader String username) {
        userService.deleteUserByEmail(username);
    }

    @GetMapping("/check_email")
    public String checkEmailUnique(@RequestParam String email){
        return userService.isEmailUnique(email) ? "Unique" : "Duplicated";
    }

    @GetMapping("/verify")
    public Set<String> verifyAccount(@RequestParam("code") String code){
        boolean verified = userService.verifyCode(code);
        return verified ? Collections.singleton("verify_success") : Collections.singleton("verify_fail") ;
    }
}
