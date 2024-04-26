package gb.library.backend.common.services;

import gb.library.backend.common.repositories.GenreRepository;
import gb.library.backend.common.specifications.GenreSpecification;
import gb.library.common.entities.Genre;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreCommonService {
    private final GenreRepository repository;

    public Genre findById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ObjectInDBNotFoundException("Жанр не найде базе, id: " + id, "GenreCommonService"));
    }

    public List<Genre> findAll(String genre, String description) {
        Specification<Genre> specification = Specification.where(null);

        if (genre != null && !genre.isBlank())
            specification = specification.and(GenreSpecification.genreLike(genre));

        if (description != null && !description.isBlank()) {
            specification = specification.and(GenreSpecification.descriptionLike(description));
        }
        return repository.findAll(specification);
    }

}
