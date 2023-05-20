package gb.library.reader.services;

import gb.library.backend.services.RoleService;
import gb.library.common.entities.RegistrationType;
import gb.library.common.entities.User;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("readerUserService")
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final RoleService roleService;

    private static final String USER_ROLE_NAME = "USER";

    public User findById(int id) {
        return repository.findById(id).orElseThrow(() ->
                new ObjectInDBNotFoundException("Пользователь не найден в базе", "User"));
    }

    public User update(User user) {
        User updatedUser = repository.findById(user.getId()).orElseThrow(() ->
                new ObjectInDBNotFoundException("Пользователь не найден в базе", "User"));
        updatedUser.setEmail(user.getEmail());
        updatedUser.setFullName(user.getFullName());
        updatedUser.setPassword(user.getPassword());
        repository.save(updatedUser);
        return updatedUser;
    }

    public void delete(Integer userId) {
        repository.deleteById(userId);
    }

    public User create(User user, RegistrationType type){
        user.setRegistrationType(type);
        user.setRoles(Set.of(roleService.getUserRoleByName(USER_ROLE_NAME)));
        return repository.save(user);
    }

    public boolean isEmailUnique(String email){
        User user = repository.findUserByEmail(email);
        return user == null;
    }

}
