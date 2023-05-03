package gb.library.reader.temp;

import gb.library.common.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public User findById(int id){
        return repository.findAllById(id);
    }
}
