package gb.library.reader.services;


import gb.library.backend.repositories.WorldBookRepository;
import gb.library.backend.specifications.WorldBookSpecification;
import gb.library.common.enums.AgeRating;
import gb.library.common.entities.WorldBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksCatalogService {

    private final WorldBookRepository worldBookRepository;


    public Page<WorldBook> findAll(Integer pageIndex, Integer pageSize, String searchText, List<String> chosenGenres, List<String> chosenAgeRatings) {

        Specification<WorldBook> searchSpecification = Specification.where(null);
        Specification<WorldBook> genreSpecification = Specification.where(null);
        Specification<WorldBook> ageRatingSpecification = Specification.where(null);

        if (searchText != null) {
            searchSpecification = searchSpecification.or(WorldBookSpecification.titleLike(searchText));
            searchSpecification = searchSpecification.or(WorldBookSpecification.authorFirstNameLike(searchText));
            searchSpecification = searchSpecification.or(WorldBookSpecification.authorSecondNameLike(searchText));
        }
        if (chosenGenres != null) {
            for (String chosenGenre : chosenGenres) {
                genreSpecification = genreSpecification.or(WorldBookSpecification.genreLike(chosenGenre));
            }
        }
        if (chosenAgeRatings != null) {
            for (String chosenAgeRate : chosenAgeRatings) {
                ageRatingSpecification = ageRatingSpecification.or(WorldBookSpecification.ageRatingIs(AgeRating.valueOf(chosenAgeRate)));
            }
        }

        searchSpecification = searchSpecification.and(genreSpecification);
        searchSpecification = searchSpecification.and(ageRatingSpecification);
        return worldBookRepository.findAll(searchSpecification, PageRequest.of(pageIndex, pageSize));
    }

    public WorldBook findById(Integer id) {
        return worldBookRepository.findById(id).orElseThrow(() ->
                new ObjectInDBNotFoundException(String.format("Книга с id(%d) не найдена", id), "WorldBook"));
    }
}