package gb.library.reader.repositories.specifications;

import gb.library.common.entities.Author;
import gb.library.common.entities.Genre;
import gb.library.common.entities.WorldBook;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;


public class WorldBookSpecification {
    public static Specification<WorldBook> titleLike(String searchText) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", searchText)));
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

    public static Specification<WorldBook> genreLike(String searchText) {
        return (root, query, criteriaBuilder) -> {
            Join<WorldBook, Genre> bookGenre = root.join("genre");
            return criteriaBuilder.like(bookGenre.get("name"), String.format("%%%s%%", searchText));
        };
    }

    public static Specification<WorldBook> ageRatingLike(String searchText) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("ageRating"), String.format("%%%s%%", searchText)));
    }
}
