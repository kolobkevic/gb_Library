package gb.library.reader.temp;

import gb.library.common.entities.LibraryBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryBookTempService {
    private final LibraryBookTempRepository repository;
    public LibraryBook findById(int id){
        return repository.findAllById(id);
    }
}
