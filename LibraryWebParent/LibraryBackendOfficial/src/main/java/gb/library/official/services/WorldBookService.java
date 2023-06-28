package gb.library.official.services;


import gb.library.backend.services.WorldBookCommonService;
import gb.library.common.enums.AgeRating;
import gb.library.common.entities.IdBasedEntity;
import gb.library.common.entities.WorldBook;
import gb.library.backend.repositories.GenreRepository;
import gb.library.backend.repositories.WorldBookRepository;
import gb.library.backend.specifications.WorldBookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WorldBookService {
    private final WorldBookRepository worldBookRepository;
    private final AuthorService authorService;
    private final GenreRepository genreRepository;
    private final WorldBookCommonService worldBookCommonService;


    public WorldBook findById(Integer id) {
        return worldBookCommonService.findById(id);
    }

    public List<WorldBook> findAll(String title, String author, String genre, String ageRatingString, String description) {

        Specification<WorldBook> specification = Specification.where(null);

        if (title != null && !title.isBlank())
            specification = specification.and(WorldBookSpecification.titleLike(title));

        if (description != null && !description.isBlank())
            specification = specification.and(WorldBookSpecification.descriptionLike(description));

        if (author != null && !author.isBlank()) {
            List<Integer> ids = authorService.searchAuthors(author).stream().map(IdBasedEntity::getId).toList();
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


    public WorldBook update(WorldBook worldBook) {
        return worldBookCommonService.update(worldBook);
    }

    private static List<Integer> removeDuplicates(List<Integer> list) {
        Set<Integer> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }
}
