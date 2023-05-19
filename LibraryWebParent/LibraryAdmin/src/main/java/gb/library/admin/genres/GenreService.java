package gb.library.admin.genres;

import gb.library.admin.utils.CheckUniqueResponseStatusHelper;
import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.common.AbstractDaoService;
import gb.library.common.entities.Genre;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GenreService implements AbstractDaoService<Genre, Integer> {

    private static final int GENRES_PER_PAGE = 8;

    private final GenreRepository repository;

    @Override
    public List<Genre> getAllList() {
        return repository.findAll();
    }

    @Override
    public Genre getById(Integer id) throws ObjectInDBNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ObjectInDBNotFoundException("Жанр с id=" + id + " в базе не найден.", "Genre"));
    }

    @Override
    public Genre create(Genre entity) {
        return repository.save(entity);
    }

    @Override
    public Genre update(Genre entity) throws ObjectInDBNotFoundException {
        Genre existedGenre = repository.findById(entity.getId())
                                        .orElseThrow(() -> new ObjectInDBNotFoundException("Невозможно обновить запись с id="
                                                + entity.getId()
                                                + ", т.к. она не найдена в базе.",
                                                "Genre"));
        existedGenre.setName(entity.getName());
        existedGenre.setDescription(entity.getDescription());
        return repository.save(existedGenre);
    }

    @Override
    public void delete(Integer id) throws ObjectInDBNotFoundException{
        if (!repository.existsById(id)){
            throw new ObjectInDBNotFoundException("Невозможно удалить запись с id=" + id
                                                    + ", т.к. она не найдена в базе.", "Genre");
        }
        repository.deleteById(id);
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, GENRES_PER_PAGE, repository);
    }

    public String checkUnique(Integer id, String name) {
        Genre genre = repository.findByName(name);

        return CheckUniqueResponseStatusHelper.getCheckStatus(genre, id);
    }

    @Transactional
    public void save(Genre genre) {
        if (genre.getId() == null) {
            create(genre);
        } else {
            update(genre);
        }
    }
}
