package gb.library.reader.temp;

import gb.library.common.entities.LibraryBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryBookService {
    private final LibraryBookRepository repository;
    public LibraryBook findById(int id){
        return repository.findAllById(id);
    }
}
