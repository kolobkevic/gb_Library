package gb.library.backend.common.specifications;

import gb.library.common.entities.LibraryBook;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LibraryBookSpecification {


    public static Specification<LibraryBook> worldBooksIdIs(List<Integer> worldBooksIDs) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Integer id : worldBooksIDs) {
                Predicate pred = criteriaBuilder.or(criteriaBuilder.equal(root.get("worldBook"), id.toString()));
                predicates.add(pred);
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }


}

