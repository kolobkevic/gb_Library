package gb.library.official.services;



import gb.library.common.entities.LibraryBook;
import gb.library.official.converters.LibraryBookConverter;
import gb.library.official.exceptions.ResourceNotFoundException;
import gb.library.official.repositories.GenreRepository;
import gb.library.official.repositories.LibraryBookRepository;
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

    private final LibraryBookConverter libraryBookConverter;


    public LibraryBook findById(Integer id) {
        return libraryBookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Книга не найде базе, id: " + id));
    }

//    public List<LibraryBook> findAll(String title, String author, String genre, String ageRatingString, String description) {
    public Page<LibraryBook> findAll() {
//
//
        Specification<LibraryBook> specification = Specification.where(null);
//
//        if (title != null && !title.isBlank())
//            specification = specification.and(LibraryBookSpecification.titleLike(title));
//
//        if (description != null && !description.isBlank())
//            specification = specification.and(LibraryBookSpecification.descriptionLike(description));
//
//        if (author != null && !author.isBlank()) {
//            List<Integer> ids = authorService.searchAuthors(author).stream().map(author1 -> author1.getId()).toList();
//            ids = removeDuplicates(ids);
//            specification = specification.and(LibraryBookpecification.authorIdIs(ids));
//        }
//
//        if (genre != null && !genre.isBlank()) {
//            List<Integer> genreIDs = genreRepository.findByGenre(genre).stream().map(IdBasedEntity::getId).toList();
//            specification = specification.and(LibraryBookSpecification.genreIdIs(genreIDs));
//        }
//
//
//        Specification<WorldBook> specificationAgeRating = Specification.where(null);
//        if (ageRatingString != null && !ageRatingString.isBlank()) {
//            AgeRating ageRatingValue = AgeRating.valueOf(ageRatingString);
//            specificationAgeRating = Specification.where(WorldBookSpecification.ageRatingIs(ageRatingValue));
//            AgeRating[] allAgeRatings = AgeRating.values();
//            for (AgeRating rating : allAgeRatings) {
//                if (rating.ordinal() < ageRatingValue.ordinal()) {
//                    specificationAgeRating = specificationAgeRating.or(WorldBookSpecification.ageRatingIs(rating));
//                }
//            }
//        }
//
//        specification = specification.and(specificationAgeRating);
//
//
//        return libraryBookRepository.findAll(specification);

//        return productRepository.findAll(    PageRequest.of(page - 1, 10)).map((product)->productConverter.entityToDto(product));

        return libraryBookRepository.findAll(specification, PageRequest.of(0, 20));
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
}
