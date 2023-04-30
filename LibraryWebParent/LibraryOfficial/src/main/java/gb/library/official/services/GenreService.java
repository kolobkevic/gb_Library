package gb.library.official.services;

import gb.library.common.dtos.GenreDTO;
import gb.library.common.entities.Genre;
import gb.library.official.exceptions.ResourceNotFoundException;
import gb.library.official.repositories.GenreRepository;
import gb.library.official.repositories.specifications.GenreSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Genre findById(Integer id) {
        return genreRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Жанр не найде базе, id: " + id));
    }

    public List<Genre> findAll(String genre, String description) {
        Specification<Genre> specification = Specification.where(null);

        if (genre != null && !genre.isBlank())
            specification = specification.and(GenreSpecification.genreLike(genre));

        if (description != null && !description.isBlank()) {
            specification = specification.and(GenreSpecification.descriptionLike(description));
        }
        return genreRepository.findAll(specification);
    }




    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteById(Integer id) {
        genreRepository.deleteById(id);
    }


    public Genre update(GenreDTO genreDTO) {
        Genre updatedGenre = genreRepository.findById(genreDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Жанр не найде базе, id: " + genreDTO.getId()));
        updatedGenre.setName(genreDTO.getName());
        updatedGenre.setDescription(genreDTO.getDescription());
        return genreRepository.save(updatedGenre);
    }
}
