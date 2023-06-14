package gb.library.backend.converters;

import gb.library.common.dtos.StorageDTO;
import gb.library.common.entities.Storage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageConverter {

    private final ModelMapper mapper;

    public Storage dtoToEntity(StorageDTO storageDTO) {
        return mapper.map(storageDTO, Storage.class);
    }

    public StorageDTO entityToDto(Storage storage) {
        return mapper.map(storage, StorageDTO.class);
    }
}
