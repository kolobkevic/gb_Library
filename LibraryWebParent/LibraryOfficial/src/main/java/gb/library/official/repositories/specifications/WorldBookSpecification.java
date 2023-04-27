package gb.library.official.repositories.specifications;

import gb.library.common.entities.AgeRating;
import gb.library.common.entities.WorldBook;
import org.springframework.data.jpa.domain.Specification;

public class WorldBookSpecification {

    public static Specification<WorldBook> titleLike(String title) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title)))));
    }

    public static Specification<WorldBook> descriptionLike(String description) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), String.format("%%%s%%", description)))));
    }
    public static Specification<WorldBook> authorIdIs(int authorId) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("author_id"), authorId))));

    }
    public static Specification<WorldBook> genreIdIs(int genreId) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genre_id"), genreId))));

    }

    public static Specification<WorldBook> ageRatingIs(AgeRating ageRating) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("age_rating"), ageRating.name()))));

    }


}

