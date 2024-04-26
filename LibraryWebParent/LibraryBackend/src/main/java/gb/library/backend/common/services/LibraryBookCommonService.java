package gb.library.backend.common.services;

import gb.library.backend.common.repositories.LibraryBookRepository;
import gb.library.common.entities.LibraryBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryBookCommonService {
    private final LibraryBookRepository repository;

    public LibraryBook findById(int id){
        return repository.findById(id)
                .orElseThrow(() -> new ObjectInDBNotFoundException("Книга не найдена в базе, id: " + id, "Library book"));

    }

    public List<LibraryBook> findAllByWorldBookId(Integer id) {
        return Collections.unmodifiableList(repository.findAllByWorldBookId(id));
    }
}
