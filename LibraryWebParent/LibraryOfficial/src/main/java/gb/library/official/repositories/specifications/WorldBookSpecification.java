package gb.library.official.repositories.specifications;

import gb.library.common.entities.AgeRating;
import gb.library.common.entities.WorldBook;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WorldBookSpecification {

    public static Specification<WorldBook> titleLike(String title) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title)))));
    }

    public static Specification<WorldBook> descriptionLike(String description) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), String.format("%%%s%%", description)))));
    }

    //    public static Specification<WorldBook> authorIdIs(List<Integer> authorIds) {
////        return ((((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("author_id"), authorId))));
//        CriteriaBuilder.In<Integer> titles;
//        return ((((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("author_id"), titles))));
//
//
//    }
    public static Specification<WorldBook> authorIdIs(List<Integer> authorIds) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Integer id : authorIds) {
                Predicate pred = criteriaBuilder.or(criteriaBuilder.equal(root.get("author"), id.toString()));
                predicates.add(pred);
            }
            Predicate finalPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            return finalPredicate;
        };
    }

    public static Specification<WorldBook> genreIdIs(List<Integer> genreIds) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Integer id : genreIds) {
                Predicate pred = criteriaBuilder.or(criteriaBuilder.equal(root.get("genre"), id.toString()));
                predicates.add(pred);
            }
            Predicate finalPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            return finalPredicate;
        };
    }

    public static Specification<WorldBook> ageRatingIs(AgeRating ageRating) {
        return ((((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ageRating"), ageRating))));

    }


}

