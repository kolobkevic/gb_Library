package gb.library.backend.converters;

import gb.library.common.dtos.StorageDTO;
import gb.library.common.entities.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageConverter {
    public Storage dtoToEntity(StorageDTO storageDTO) {
        Storage storage = new Storage();
        storage.setId(storageDTO.getId());
        storage.setZone(storageDTO.getZone());
        storage.setSector(storageDTO.getSector());
        storage.setAvailable(storageDTO.isAvailable());
        return storage;
    }

    public StorageDTO entityToDto(Storage storage) {
        return new StorageDTO(storage.getId(), storage.getZone(), storage.getSector(), storage.isAvailable());
    }
}
