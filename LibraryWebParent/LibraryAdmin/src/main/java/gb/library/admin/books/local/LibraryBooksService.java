package gb.library.admin.books.local;

import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.common.AbstractDaoService;
import gb.library.common.entities.LibraryBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
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
        return null;
    }

    @Override
    public LibraryBook create(LibraryBook entity) {
        return null;
    }

    @Override
    public LibraryBook update(LibraryBook entity) throws ObjectInDBNotFoundException {
        return null;
    }

    @Override
    public void delete(Integer id) throws ObjectInDBNotFoundException {

    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, LIBRARY_BOOKS_PER_PAGE, repository);
    }

    public void save(LibraryBook book){
        if (book.getId() == null) {
            create(book);
        } else {
            update(book);
        }
    }
}
