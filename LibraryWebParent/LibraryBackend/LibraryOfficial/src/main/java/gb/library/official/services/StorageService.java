package gb.library.official.services;

import gb.library.common.entities.Storage;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.backend.repositories.StorageRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;


    public List<Storage> findAll() {

        return storageRepository.findAll();
    }

    public Storage save(Storage storage) {
        return storageRepository.save(storage);
    }

    public void deleteById(Integer id) {storageRepository.deleteById(id);
    }


    public Storage update(Storage storage) {
        Storage updatedGenre = storageRepository.findById(storage.getId()).orElseThrow(
                () -> new ObjectInDBNotFoundException("Место хранения не найдено базе, id: " + storage.getId(), "Storage"));
        updatedGenre.setZone(storage.getZone());
        updatedGenre.setSector(storage.getSector());
        return storageRepository.save(updatedGenre);
    }

    public List<Zone> getStorageZones(){
        Map<String, List<Sector>> zones = new HashMap<>();
        List<Storage> storages = findAll();
        for (Storage storage : storages) {
            List<Sector> sectors = zones.get(storage.getZone());
            if (sectors == null){
                sectors=new ArrayList<>();
                zones.put(storage.getZone(), sectors);
            }
            sectors.add(new Sector(storage.getId(), storage.getSector(), storage.isAvailable()));
        }

        List<Zone> zonesList = new ArrayList<>();
        for (Map.Entry<String, List<Sector>> stringListEntry : zones.entrySet()) {
            zonesList.add(new Zone(stringListEntry.getKey(), stringListEntry.getValue()));
        }

        return zonesList;
    }
    @AllArgsConstructor
    public static class Zone{
        public String zone;
        public List<Sector> sectors;
        
    }
    @AllArgsConstructor
    public static class Sector{
        public int id;
        public String sector;
        public Boolean isAvailable;

    }
}