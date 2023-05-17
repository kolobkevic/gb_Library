package gb.library.reader.services;

import gb.library.common.entities.User;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.reader.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("readerUserService")
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

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

}
