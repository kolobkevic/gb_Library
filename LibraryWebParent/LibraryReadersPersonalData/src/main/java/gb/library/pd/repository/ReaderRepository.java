package gb.library.pd.repository;

import gb.library.pd.entity.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<ReaderEntity, Long> {

    ReaderEntity findByReaderId(Long readerId);

    @Query(value = "SELECT * FROM readers OFFSET ?1 LIMIT ?2", nativeQuery = true)
    List<ReaderEntity> findAllOffsetLimit(long offset, int limit);

    @Modifying
    @Query(value = "DELETE FROM readers WHERE reader_id = ?1", nativeQuery = true)
    void removeReaderByReaderId(Long readerId);
}
