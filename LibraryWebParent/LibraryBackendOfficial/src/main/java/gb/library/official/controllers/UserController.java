package gb.library.official.controllers;

import gb.library.official.dtos.UserDTO;
import gb.library.official.converters.UserConverter;
import gb.library.official.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController("readerUserController")
@RequestMapping("/api/v1/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;


    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Integer id) {
        return userConverter.entityToDto(userService.findById(id));
    }
}

