package gb.library.backend.services;

import gb.library.backend.repositories.LibraryBookRepository;
import gb.library.common.entities.LibraryBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryBookService {
    private final LibraryBookRepository repository;

    public LibraryBook findById(int id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectInDBNotFoundException("Книга не найдена в базе, id: " + id, "Library book"));

    }
}
