package gb.library.backend.specifications;

import gb.library.common.entities.AgeRating;
import gb.library.common.entities.Author;
import gb.library.common.entities.Genre;
import gb.library.common.entities.WorldBook;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WorldBookSpecification {

    public static Specification<WorldBook> titleLike(String title) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title)))));
    }

    public static Specification<WorldBook> descriptionLike(String description) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), String.format("%%%s%%", description)))));
    }

    public static Specification<WorldBook> authorIdIs(List<Integer> authorIds) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Integer id : authorIds) {
                Predicate pred = criteriaBuilder.or(criteriaBuilder.equal(root.get("author"), id.toString()));
                predicates.add(pred);
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<WorldBook> authorFirstNameLike(String searchText) {
        return (root, query, criteriaBuilder) -> {
            Join<WorldBook, Author> authorBook = root.join("author");
            return criteriaBuilder.like(authorBook.get("firstName"), String.format("%%%s%%", searchText));
        };
    }

    public static Specification<WorldBook> authorSecondNameLike(String searchText) {
        return (root, query, criteriaBuilder) -> {
            Join<WorldBook, Author> authorBook = root.join("author");
            return criteriaBuilder.like(authorBook.get("lastName"), String.format("%%%s%%", searchText));
        };
    }

    public static Specification<WorldBook> genreIdIs(List<Integer> genreIds) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Integer id : genreIds) {
                Predicate pred = criteriaBuilder.or(criteriaBuilder.equal(root.get("genre"), id.toString()));
                predicates.add(pred);
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<WorldBook> ageRatingIs(AgeRating ageRating) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ageRating"), ageRating))));

    }


    public static Specification<WorldBook> genreLike(String searchText) {
        return (root, query, criteriaBuilder) -> {
            Join<WorldBook, Genre> bookGenre = root.join("genre");
            return criteriaBuilder.like(bookGenre.get("name"), String.format("%%%s%%", searchText));
        };
    }

}

