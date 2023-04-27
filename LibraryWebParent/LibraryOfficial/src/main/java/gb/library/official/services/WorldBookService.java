package gb.library.official.services;

;
import gb.library.common.dtos.WorldBookDTO;
import gb.library.common.entities.AgeRating;
import gb.library.common.entities.WorldBook;
import gb.library.official.exceptions.ResourceNotFoundException;
import gb.library.official.repositories.WorldBookRepository;
import gb.library.official.repositories.specifications.WorldBookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldBookService {
    private final WorldBookRepository worldBookRepository;


    public WorldBook findById(Integer id) {
        return worldBookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Книга не найде базе, id: " + id));
    }

    public List<WorldBook> findAll(String title, Integer authorID, Integer genreID, AgeRating ageRating, String description) {

        Specification<WorldBook> specificationAgeRating = Specification.where(null);
        if (ageRating != null) {
            specificationAgeRating = Specification.where(WorldBookSpecification.ageRatingIs(ageRating));
            AgeRating[] ageRatings = AgeRating.values();
            for (AgeRating rating : ageRatings) {
                if (rating.ordinal() < ageRating.ordinal()) {
                    specificationAgeRating = specificationAgeRating.or(WorldBookSpecification.ageRatingIs(rating));
                }
            }
        }
        Specification<WorldBook> specification = Specification.where(null);

        if (title != null && !title.isBlank())
            specification = specification.and(WorldBookSpecification.titleLike(title));

        if (description != null && !description.isBlank())
            specification = specification.and(WorldBookSpecification.descriptionLike(description));

        if (authorID != null)
            specification = specification.and(WorldBookSpecification.authorIdIs(authorID));

        if (genreID != null)
            specification = specification.and(WorldBookSpecification.genreIdIs(genreID));


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
}
