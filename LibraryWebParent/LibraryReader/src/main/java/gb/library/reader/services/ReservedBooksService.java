package gb.library.reader.services;

import gb.library.common.AbstractDaoService;
import gb.library.common.entities.LibraryBook;
import gb.library.common.entities.ReservedBook;
import gb.library.common.entities.User;
import gb.library.common.entities.WorldBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.official.services.WorldBookService;
import gb.library.reader.repositories.ReservedBooksRepository;
import gb.library.reader.temp.LibraryBookService;
import gb.library.reader.temp.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservedBooksService implements AbstractDaoService<ReservedBook, Integer> {
    private final ReservedBooksRepository repository;
    private final UserService userService;
    private final LibraryBookService libraryBookService;
    private final WorldBookService worldBookService;

    @Override
    public List<ReservedBook> getAllList() {
        return repository.findAll();
    }

    @Override
    public ReservedBook getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectInDBNotFoundException("Запись с id=" + id +
                " в базе не найдена.", "ReservedBook"));
    }

    @Override
    public ReservedBook create(ReservedBook reservedBook) {
        return repository.save(reservedBook);
    }

    @Override
    public ReservedBook update(ReservedBook reservedBook) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Page<ReservedBook> getAllPageable(int userId, int pageIndex, int pageSize) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return repository.findAllByUserId(userId, PageRequest.of(pageIndex - 1, pageSize));
    }

    public Page<ReservedBook> getAllPageable(int userId, int pageIndex, int pageSize, Sort.Direction direction,
                                             String[] properties) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return repository.findAllByUserId(userId, PageRequest.of(pageIndex - 1, pageSize, direction, properties));
    }

    public WorldBook findWorldBookById(int id) {
        return worldBookService.findById(id);
    }

    public LibraryBook findLibraryBookById(int id) {
        return libraryBookService.findById(id);
    }

    public User findByUserId(int id) {
        return userService.findById(id);
    }
}
