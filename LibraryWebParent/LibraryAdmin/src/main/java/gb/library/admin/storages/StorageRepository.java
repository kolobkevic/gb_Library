package gb.library.admin.storages;

import gb.library.admin.utils.paging.SearchRepository;
import gb.library.common.entities.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StorageRepository extends SearchRepository<Storage, Integer> {

    @Query("SELECT st FROM Storage st WHERE st.sector LIKE %?1% or st.zone LIKE %?1%")
    Page<Storage> getAllWithFilter(String keyword, Pageable pageable);

    Storage save(Storage storage);

    Storage findBySectorAndZone(String sector, String zone);

    @Modifying
    @Query("UPDATE Storage st SET st.available = ?2 WHERE st.id = ?1")
    public void updateAvailableStatus(Integer id, boolean available);
}
