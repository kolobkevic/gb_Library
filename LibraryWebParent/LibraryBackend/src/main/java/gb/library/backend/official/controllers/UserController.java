package gb.library.backend.official.controllers;

import gb.library.backend.official.dtos.UserDTO;
import gb.library.backend.official.services.UserService;
import gb.library.backend.official.converters.UserConverter;
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

