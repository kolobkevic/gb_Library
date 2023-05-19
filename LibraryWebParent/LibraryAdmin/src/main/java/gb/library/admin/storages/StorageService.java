package gb.library.admin.storages;

import gb.library.admin.utils.CheckUniqueResponseStatusHelper;
import gb.library.admin.utils.paging.PagingAndSortingHelper;
import gb.library.common.AbstractDaoService;
import gb.library.common.entities.Storage;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService implements AbstractDaoService<Storage, Integer> {

    private static final int STORAGE_PER_PAGE = 10;
    private final StorageRepository repository;

    @Override
    public List<Storage> getAllList() {
        return repository.findAll();
    }

    @Override
    public Storage getById(Integer id) throws ObjectInDBNotFoundException{
        return repository.findById(id)
                .orElseThrow(()-> new ObjectInDBNotFoundException("Запись с id=" + id + " в базе не найдена.",
                                                                    "Места хранения"));
    }

    @Override
    public Storage create(Storage entity) {
        return repository.save(entity);
    }

    @Override
    public Storage update(Storage entity) {
        Storage existedStorage = repository.findById(entity.getId())
                .orElseThrow(() -> new ObjectInDBNotFoundException("Невозможно обновить запись с id="
                                                                    + entity.getId()
                                                                    + ", т.к. она не найдена в базе.",
                                                                    "Storage"));
        existedStorage.setZone(entity.getZone());
        existedStorage.setSector(entity.getSector());
        existedStorage.setAvailable(entity.isAvailable());
        return repository.save(existedStorage);
    }

    @Override
    public void delete(Integer id) throws ObjectInDBNotFoundException{
        if (!repository.existsById(id)) {
            throw new ObjectInDBNotFoundException("Невозможно удалить запись с id=" + id
                                                     + ", т.к. она не найдена в базе.", "World book");
        }
        repository.deleteById(id);
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper){
        helper.listEntities(pageNum, STORAGE_PER_PAGE, repository);
    }

    public String checkUnique(Integer id, String sector, String zone){
        Storage storage = repository.findBySectorAndZone(sector, zone);

        return CheckUniqueResponseStatusHelper.getCheckStatus(storage, id);
    }

    @Transactional
    public void save(Storage storage) {
        if (storage.getId() == null) {
            create(storage);
        } else {
            update(storage);
        }
    }

    @Transactional
    public void updateAvailableStatus(Integer id, boolean available) {
        repository.updateAvailableStatus(id, available);
    }
}
