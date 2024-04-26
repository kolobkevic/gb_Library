package gb.library.backend.common.specifications;

import gb.library.common.entities.Genre;
import org.springframework.data.jpa.domain.Specification;

public class GenreSpecification {

    public static Specification<Genre> genreLike(String genre) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), String.format("%%%s%%", genre)));
    }

    public static Specification<Genre> descriptionLike(String description) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), String.format("%%%s%%", description)));
    }

}

