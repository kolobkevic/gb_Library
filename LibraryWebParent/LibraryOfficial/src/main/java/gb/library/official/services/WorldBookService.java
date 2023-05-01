package gb.library.official.services;

;
import gb.library.common.dtos.WorldBookDTO;
import gb.library.common.entities.AgeRating;
import gb.library.common.entities.Author;
import gb.library.common.entities.IdBasedEntity;
import gb.library.common.entities.WorldBook;
import gb.library.official.exceptions.ResourceNotFoundException;
import gb.library.official.repositories.GenreRepository;
import gb.library.official.repositories.WorldBookRepository;
import gb.library.official.repositories.specifications.WorldBookSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WorldBookService {
    private final WorldBookRepository worldBookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final GenreRepository genreRepository;


    public WorldBook findById(Integer id) {
        return worldBookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Книга не найде базе, id: " + id));
    }

    public List<WorldBook> findAll(String title, String author, String genre, String ageRatingString, String description) {


        Specification<WorldBook> specification = Specification.where(null);

        if (title != null && !title.isBlank())
            specification = specification.and(WorldBookSpecification.titleLike(title));

        if (description != null && !description.isBlank())
            specification = specification.and(WorldBookSpecification.descriptionLike(description));

        if (author != null && !author.isBlank()) {
            List<Integer> ids = authorService.searchAuthors(author).stream().map(author1 -> author1.getId()).toList();
            ids = removeDuplicates(ids);
            specification = specification.and(WorldBookSpecification.authorIdIs(ids));
        }

        if (genre != null && !genre.isBlank()) {
            List<Integer> genreIDs = genreRepository.findByGenre(genre).stream().map(IdBasedEntity::getId).toList();
            specification = specification.and(WorldBookSpecification.genreIdIs(genreIDs));
        }


        Specification<WorldBook> specificationAgeRating = Specification.where(null);
        if (ageRatingString != null && !ageRatingString.isBlank()) {
            AgeRating ageRatingValue = AgeRating.valueOf(ageRatingString);
            specificationAgeRating = Specification.where(WorldBookSpecification.ageRatingIs(ageRatingValue));
            AgeRating[] allAgeRatings = AgeRating.values();
            for (AgeRating rating : allAgeRatings) {
                if (rating.ordinal() < ageRatingValue.ordinal()) {
                    specificationAgeRating = specificationAgeRating.or(WorldBookSpecification.ageRatingIs(rating));
                }
            }
        }

        specification = specification.and(specificationAgeRating);


        return worldBookRepository.findAll(specification);
    }


    public WorldBook save(WorldBook worldBook) {
        return worldBookRepository.save(worldBook);
    }

    public void deleteById(Integer id) {
        worldBookRepository.deleteById(id);
    }


    public WorldBook update(WorldBookDTO worldBookDTO) {
        WorldBook updatedWorldBook = worldBookRepository.findById(worldBookDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Жанр не найде базе, id: " + worldBookDTO.getId()));
        updatedWorldBook.setTitle(worldBookDTO.getTitle());
        updatedWorldBook.setAuthor(worldBookDTO.getAuthor());
        updatedWorldBook.setGenre(worldBookDTO.getGenre());
        updatedWorldBook.setAgeRating(worldBookDTO.getAgeRating());
        updatedWorldBook.setDescription(worldBookDTO.getDescription());
        return worldBookRepository.save(updatedWorldBook);
    }

    private static List<Integer> removeDuplicates(List<Integer> list) {
        Set<Integer> set = new HashSet<>(list);
        List<Integer> newlist = new ArrayList<>(set);
        return newlist;
    }
}
