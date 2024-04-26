package gb.library.backend.common.services;

import gb.library.backend.common.repositories.StorageRepository;
import gb.library.common.entities.Storage;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageCommonService {
    private final StorageRepository repository;

    public Storage findById(Integer id){
        return repository.findById(id)
                        .orElseThrow(() -> new ObjectInDBNotFoundException("Место хранения не найдено в базе, id: " + id, "Storage"));
    }

    public Storage findBySectorAndZone(String sector, String zone) {
        return repository.findBySectorAndZone(sector, zone)
                .orElseThrow(() -> new ObjectInDBNotFoundException("Место хранения не найдено в базе, sector: " + sector + ", zone:" + zone, "Storage"));
    }
}
