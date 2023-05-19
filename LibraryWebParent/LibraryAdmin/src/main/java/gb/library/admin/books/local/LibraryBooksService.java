package gb.library.admin.books.local;

import gb.library.admin.utils.CheckUniqueResponseStatusHelper;
import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.common.AbstractDaoService;
import gb.library.common.entities.LibraryBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryBooksService implements AbstractDaoService<LibraryBook, Integer> {

    private final LibraryBooksRepository repository;

    private static final int LIBRARY_BOOKS_PER_PAGE = 10;

    @Override
    public List<LibraryBook> getAllList() {
        return repository.findAll();
    }

    @Override
    public LibraryBook getById(Integer id) throws ObjectInDBNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectInDBNotFoundException("Запись с id=" + id + " в базе не найдена.",
                        "Library books"));
    }

    @Override
    public LibraryBook create(LibraryBook entity) {
        return repository.save(entity);
    }

    @Override
    public LibraryBook update(LibraryBook entity) throws ObjectInDBNotFoundException {
        LibraryBook existedBook = repository.findById(entity.getId())
                .orElseThrow(() -> new ObjectInDBNotFoundException("Невозможно обновить запись с id="
                        + entity.getId()
                        + ", т.к. она не найдена в базе.",
                        "Library book"));
        existedBook.setWorldBook(entity.getWorldBook());
        existedBook.setIsbn(entity.getIsbn());
        existedBook.setAvailable(entity.isAvailable());
        existedBook.setPublisher(entity.getPublisher());
        existedBook.setPlacedAt(entity.getPlacedAt());

        return repository.save(existedBook);
    }

    @Override
    public void delete(Integer id) throws ObjectInDBNotFoundException {
        if (!repository.existsById(id)) {
            throw new ObjectInDBNotFoundException("Невозможно удалить запись с id=" + id
                    + ", т.к. она не найдена в базе.", "Library book");
        }
        repository.deleteById(id);
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, LIBRARY_BOOKS_PER_PAGE, repository);
    }

    @Transactional
    public void save(LibraryBook book){
        if (book.getId() == null) {
            create(book);
        } else {
            update(book);
        }
    }

    public String checkUnique(Integer id, String invNumber) {
        LibraryBook book = repository.findByInventoryNumber(invNumber);

        return CheckUniqueResponseStatusHelper.getCheckStatus(book, id);
    }

    @Transactional
    public void updateAvailableStatus(Integer id, boolean available) {
        repository.updateAvailableStatus(id, available);
    }
}
