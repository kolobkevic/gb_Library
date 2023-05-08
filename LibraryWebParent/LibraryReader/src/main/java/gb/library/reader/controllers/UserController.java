package gb.library.reader.controllers;

import gb.library.reader.converters.UserConverter;
import gb.library.reader.dtos.UserDto;
import gb.library.reader.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("readerUserController")
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable int userId) {
        return userConverter.entityToDto(userService.findById(userId));
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto userDto) {
        return userConverter.entityToDto(userService.update(userConverter.dtoToEntity(userDto)));
    }

    @DeleteMapping("/delete/{userId}")
    public void delete(@PathVariable int userId) {
        userService.delete(userId);
    }
}
