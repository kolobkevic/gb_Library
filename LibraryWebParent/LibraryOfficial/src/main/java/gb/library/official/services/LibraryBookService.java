package gb.library.official.services;



import gb.library.common.entities.AgeRating;
import gb.library.common.entities.IdBasedEntity;
import gb.library.common.entities.LibraryBook;
import gb.library.common.entities.WorldBook;
import gb.library.official.converters.LibraryBookConverter;
import gb.library.official.exceptions.ResourceNotFoundException;
import gb.library.official.repositories.GenreRepository;
import gb.library.official.repositories.LibraryBookRepository;
import gb.library.official.repositories.WorldBookRepository;
import gb.library.official.repositories.specifications.LibraryBookSpecification;
import gb.library.official.repositories.specifications.WorldBookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LibraryBookService {
    private final LibraryBookRepository libraryBookRepository;

    private final AuthorService authorService;

    private final GenreRepository genreRepository;

    private final WorldBookRepository worldBookRepository;




    public LibraryBook findById(Integer id) {
        return libraryBookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Книга не найде базе, id: " + id));
    }

//    public List<LibraryBook> findAll(String title, String author, String genre, String ageRatingString, String description) {
    public Page<LibraryBook> findAll(Integer page, Integer pageSize, String title, String author, String genre, String ageRatingString, String description) {
        Specification<WorldBook> worldBookSpecification = Specification.where(null);

        if (title != null && !title.isBlank())
            worldBookSpecification = worldBookSpecification.and(WorldBookSpecification.titleLike(title));

        if (description != null && !description.isBlank())
            worldBookSpecification = worldBookSpecification.and(WorldBookSpecification.descriptionLike(description));

        if (author != null && !author.isBlank()) {
            List<Integer> ids = authorService.searchAuthors(author).stream().map(author1 -> author1.getId()).toList();
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
        if (!(title+ author+genre+ ageRatingString+ description).isBlank()){

            libraryBookSpecification=libraryBookSpecification.and(LibraryBookSpecification.worldBooksIdIs(worldBookRepository.findAll(worldBookSpecification).stream().map(worldBook -> worldBook.getId()).toList()));
        }

        return libraryBookRepository.findAll(libraryBookSpecification, PageRequest.of(page-1, pageSize));
    }


    public LibraryBook save(LibraryBook libraryBook) {
        return libraryBookRepository.save(libraryBook);
    }

    public void deleteById(Integer id) {
        libraryBookRepository.deleteById(id);
    }


    public LibraryBook update(LibraryBook libraryBook) {
        LibraryBook updatedBook = libraryBookRepository.findById(libraryBook.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Книга не найде базе, id: " + libraryBook.getId()));
//        updatedBook.setTitle(libraryBook.getTitle());
//        updatedBook.setAuthor(libraryBook.getAuthor());
//        updatedBook.setGenre(libraryBook.getGenre());
//        updatedBook.setAgeRating(libraryBook.getAgeRating());
//        updatedBook.setDescription(libraryBook.getDescription());
        //todo: update
        return libraryBookRepository.save(updatedBook);
    }
    private static List<Integer> removeDuplicates(List<Integer> list) {
        Set<Integer> set = new HashSet<>(list);
        List<Integer> newlist = new ArrayList<>(set);
        return newlist;
    }
}
