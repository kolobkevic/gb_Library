package gb.library.backend.common.specifications;

import gb.library.common.entities.Storage;
import org.springframework.data.jpa.domain.Specification;

public class StorageSpecification {
    public static Specification<Storage> zoneLike(String zone) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("zone"), String.format("%%%s%%", zone)));
    }

    public static Specification<Storage> sectorLike(String sector) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("sector"), String.format("%%%s%%", sector)));
    }

    public static Specification<Storage> availableCheck(boolean available) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("available"), available));
    }
}
