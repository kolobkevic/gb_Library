package gb.library.backend.repositories;

import gb.library.common.entities.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> , JpaSpecificationExecutor<Storage> {

    Optional<Storage> findBySectorAndZone(String sector, String zone);
}

