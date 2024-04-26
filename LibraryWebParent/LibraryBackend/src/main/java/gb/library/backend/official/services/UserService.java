package gb.library.backend.official.services;

import gb.library.backend.common.repositories.UserRepository;
import gb.library.common.entities.User;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectInDBNotFoundException("Пользователь не найден", "User"));
    }
}
