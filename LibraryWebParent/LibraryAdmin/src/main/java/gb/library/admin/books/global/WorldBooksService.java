package gb.library.admin.books.global;

import gb.library.admin.utils.CheckUniqueResponseStatusHelper;
import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.common.AbstractDaoService;
import gb.library.common.entities.Author;
import gb.library.common.entities.WorldBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WorldBooksService implements AbstractDaoService<WorldBook, Integer> {

    private static final int WORLD_BOOKS_PER_PAGE = 15;

    private final WorldBooksRepository repository;

    @Override
    public List<WorldBook> getAllList() {
        return repository.findAll();
    }

    @Override
    public WorldBook getById(Integer id) throws ObjectInDBNotFoundException {
        return repository.findById(id)
                            .orElseThrow(() -> new ObjectInDBNotFoundException("Запись с id=" + id + " в базе не найдена.",
                                                                                "World books"));
    }

    @Override
    public WorldBook create(WorldBook entity) {
        return repository.save(entity);
    }

    @Override
    public WorldBook update(WorldBook entity) throws ObjectInDBNotFoundException {
        WorldBook existedBook = repository.findById(entity.getId())
                .orElseThrow(() -> new ObjectInDBNotFoundException("Невозможно обновить запись с id="
                                                                    + entity.getId()
                                                                    + ", т.к. она не найдена в базе.",
                                                                    "World book"));
        existedBook.setAuthor(entity.getAuthor());
        existedBook.setTitle(entity.getTitle());
        existedBook.setGenre(entity.getGenre());
        existedBook.setAgeRating(entity.getAgeRating());
        existedBook.setDescription(entity.getDescription());
        return repository.save(existedBook);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ObjectInDBNotFoundException("Невозможно удалить запись с id=" + id
                    + ", т.к. она не найдена в базе.", "World book");
        }
        repository.deleteById(id);
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, WORLD_BOOKS_PER_PAGE, repository);
    }

    public String checkUnique(Integer id, String title, Author author) {
        WorldBook book = repository.findByTitleAndAuthor(title, author);

        return CheckUniqueResponseStatusHelper.getCheckStatus(book, id);
    }

    @Transactional
    public void save(WorldBook book) {
        if (book.getId() == null) {
            create(book);
        } else {
            update(book);
        }
    }
}
