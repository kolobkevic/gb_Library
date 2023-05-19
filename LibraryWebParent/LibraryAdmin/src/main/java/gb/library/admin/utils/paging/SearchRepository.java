package gb.library.admin.utils.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;


@NoRepositoryBean
public interface SearchRepository<T, ID> extends PagingAndSortingRepository<T, ID> {
    public Page<T> getAllWithFilter(String keyword, Pageable pageable);

    Optional<T> findById(Integer id);

    void deleteById(Integer id);

    boolean existsById(Integer id);

    List<T> findAll();

}
