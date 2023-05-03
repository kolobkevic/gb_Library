package gb.library.reader.temp;

import gb.library.common.entities.WorldBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorldBookService {
    private final WorldBookRepository repository;
    public WorldBook findById(int id){
        return repository.findAllById(id);
    }
}
