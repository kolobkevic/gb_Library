package gb.library.official.services;


import gb.library.common.enums.AgeRating;
import gb.library.common.entities.IdBasedEntity;
import gb.library.common.entities.LibraryBook;
import gb.library.common.entities.WorldBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.backend.repositories.GenreRepository;
import gb.library.backend.repositories.LibraryBookRepository;
import gb.library.backend.repositories.WorldBookRepository;
import gb.library.backend.specifications.LibraryBookSpecification;
import gb.library.backend.specifications.WorldBookSpecification;
import gb.library.common.exceptions.ValidateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LibraryBookService {
    private final LibraryBookRepository libraryBookRepository;

    private final AuthorService authorService;

    private final GenreRepository genreRepository;

    private final WorldBookRepository worldBookRepository;


    public LibraryBook findById(Integer id) {
        return libraryBookRepository.findById(id).orElseThrow(
                () -> new ObjectInDBNotFoundException("Книга не найде базе, id: " + id, "Library Book"));
    }

    @Transactional
    public Page<LibraryBook> findAll(Integer page, Integer pageSize, String title, String author, String genre, String ageRatingString, String description) {
        Specification<WorldBook> worldBookSpecification = Specification.where(null);

        if (title != null && !title.isBlank())
            worldBookSpecification = worldBookSpecification.and(WorldBookSpecification.titleLike(title));

        if (description != null && !description.isBlank())
            worldBookSpecification = worldBookSpecification.and(WorldBookSpecification.descriptionLike(description));

        if (author != null && !author.isBlank()) {
            List<Integer> ids = authorService.searchAuthors(author).stream().map(IdBasedEntity::getId).toList();
            ids = removeDuplicates(ids);
            worldBookSpecification = worldBookSpecification.and(WorldBookSpecification.authorIdIs(ids));
        }

        if (genre != null && !genre.isBlank()) {
            List<Integer> genreIDs = genreRepository.findByGenre(genre).stream().map(IdBasedEntity::getId).toList();
            worldBookSpecification = worldBookSpecification.and(WorldBookSpecification.genreIdIs(genreIDs));
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
        worldBookSpecification = worldBookSpecification.and(specificationAgeRating);

        Specification<LibraryBook> libraryBookSpecification = Specification.where(null);
        if (!(title + author + genre + ageRatingString + description).isBlank()) {

            libraryBookSpecification = libraryBookSpecification
                    .and(LibraryBookSpecification.worldBooksIdIs(worldBookRepository.findAll(worldBookSpecification)
                            .stream()
                            .map(IdBasedEntity::getId)
                            .toList()));
        }
        return libraryBookRepository.findAll(libraryBookSpecification, PageRequest.of(page - 1, pageSize));
    }

    @Transactional
    public LibraryBook save(LibraryBook libraryBook) {
        if (libraryBookRepository.findByInventoryNumber(libraryBook.getInventoryNumber()).isPresent()){
            throw new ValidateException(List.of("Инвентарный номер: " + libraryBook.getInventoryNumber() + " уже существует"));
        }
        return libraryBookRepository.save(libraryBook);
    }

    public void deleteById(Integer id) {
        libraryBookRepository.deleteById(id);
    }

    @Transactional
    public LibraryBook update(LibraryBook updatedLibraryBook) {
        Optional<LibraryBook> optionalLibraryBook = libraryBookRepository.findByInventoryNumber(updatedLibraryBook.getInventoryNumber());
        if (optionalLibraryBook.isPresent()) {
            if (updatedLibraryBook.getId().equals(optionalLibraryBook.get().getId())) {
                return libraryBookRepository.save(updatedLibraryBook);
            } else {
                throw new ValidateException(List.of("Инвентарный номер: " + updatedLibraryBook.getInventoryNumber() + " принадлежит другой книге"));
            }
        } else {
            libraryBookRepository.findById(updatedLibraryBook.getId()).orElseThrow(
                    () -> new ObjectInDBNotFoundException("Книга не найдена базе, id: " + updatedLibraryBook.getId(), "Library Book"));
            return libraryBookRepository.save(updatedLibraryBook);
        }
    }

    private static List<Integer> removeDuplicates(List<Integer> list) {
        Set<Integer> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }
}
