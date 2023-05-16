package gb.library.admin.books.wishlist;

import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.common.AbstractDaoService;
import gb.library.common.entities.BooksWishlist;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishedBooksService implements AbstractDaoService<BooksWishlist, Integer> {

    private static final int WISHED_BOOKS_PER_PAGE = 15;

    private final WishedBooksRepository repository;

    @Override
    public List<BooksWishlist> getAllList() {
        return repository.findAll();
    }

    @Override
    public BooksWishlist getById(Integer id) throws ObjectInDBNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectInDBNotFoundException("Запись с id=" + id +
                        " в базе не найдена.", "Wished book"));
    }

    @Override
    public BooksWishlist create(BooksWishlist entity) {
        return null;
    }

    @Override
    public BooksWishlist update(BooksWishlist entity) throws ObjectInDBNotFoundException {
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ObjectInDBNotFoundException("Невозможно удалить запись с id=" + id
                    + ", т.к. она не найдена в базе.", "Wished book");
        }
        repository.deleteById(id);
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, WISHED_BOOKS_PER_PAGE, repository);
    }
}
