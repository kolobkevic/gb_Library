package gb.library.admin.authors;

import gb.library.admin.utils.CheckUniqueResponseStatusHelper;
import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.common.AbstractDaoService;
import gb.library.common.entities.Author;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthorService implements AbstractDaoService<Author, Integer> {

    private static final int AUTHORS_PER_PAGE = 10;

    private final AuthorRepository repository;

    @Override
    public List<Author> getAllList() {
        return repository.findAll();
    }

    @Override
    public Author getById(Integer id) throws ObjectInDBNotFoundException {
        return repository.findById(id).orElseThrow(()-> new ObjectInDBNotFoundException("Автор с id=" + id + " в базе не найден.", "Author"));
    }

    @Override
    public Author create(Author entity) {
        return repository.save(entity);
    }

    @Override
    public Author update(Author entity) throws ObjectInDBNotFoundException{
        Author existedAuthor = repository.findById(entity.getId())
                                        .orElseThrow(() -> new ObjectInDBNotFoundException("Невозможно обновить запись с id="
                                                                                            + entity.getId()
                                                                                            + ", т.к. она не найдена в базе.",
                                                                                            "Author"));
        existedAuthor.setFirstName(entity.getFirstName());
        existedAuthor.setLastName(entity.getLastName());
        return repository.save(existedAuthor);
    }

    @Override
    public void delete(Integer id) throws ObjectInDBNotFoundException {
        if (!repository.existsById(id)) {
            throw new ObjectInDBNotFoundException("Невозможно удалить запись с id=" + id
                                                    + ", т.к. она не найдена в базе.", "Author");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void save(Author author) {
        if (author.getId() == null) {
            create(author);
        } else {
            update(author);
        }
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, AUTHORS_PER_PAGE, repository);
    }

    public String checkUnique(Integer id, String firstName, String lastName){

        Author author = repository.findByFirstNameAndLastName(firstName, lastName);

        return CheckUniqueResponseStatusHelper.getCheckStatus(author, id);
    }
}
