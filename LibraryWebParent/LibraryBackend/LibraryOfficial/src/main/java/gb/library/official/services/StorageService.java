package gb.library.official.services;

import gb.library.common.entities.Storage;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.backend.repositories.StorageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public void deleteById(Integer id) {
        storageRepository.deleteById(id);
    }


    public Storage update(Storage storageToUpdate) {
        System.out.println(storageToUpdate.getZone() + " " + storageToUpdate.getSector());
        Storage currentStorage = storageRepository.findById(storageToUpdate.getId()).orElseThrow(
                () -> new ObjectInDBNotFoundException("Место хранения не найдено базе, id: " + storageToUpdate.getId(), "Storage"));
        currentStorage.setZone(storageToUpdate.getZone());
        currentStorage.setSector(storageToUpdate.getSector());
        currentStorage.setAvailable(storageToUpdate.isAvailable());
        return storageRepository.save(currentStorage);
    }

    public List<Zone> getStorageZones() {
        Map<String, List<Sector>> zones = new TreeMap<>();
        List<Storage> storages = findAll();
        for (Storage storage : storages) {
            List<Sector> sectors = zones.get(storage.getZone());
            if (sectors == null) {
                sectors = new ArrayList<>();
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

    public Zone findZoneByTitle(String zoneTitle) {
        Map<String, List<Sector>> zoneData = new TreeMap<>();
        List<Storage> storages = storageRepository.findAllByZone(zoneTitle);
        for (Storage storage : storages) {
            List<Sector> sectors = zoneData.get(storage.getZone());
            if (sectors == null) {
                sectors = new ArrayList<>();
                zoneData.put(storage.getZone(), sectors);
            }
            sectors.add(new Sector(storage.getId(), storage.getSector(), storage.isAvailable()));
        }
        return new Zone(zoneTitle, zoneData.get(zoneTitle));
    }

    public Storage findByZoneAndSector(String zone, String sector) {
        return storageRepository.findByZoneAndSector(zone, sector).orElseThrow(
                () -> new ObjectInDBNotFoundException(String.format("Место хранения не найдена по заданным параметрам (%s, %s)", zone, sector), "Storage"));
    }

    @AllArgsConstructor
    public static class Zone {
        public String zone;
        public List<Sector> sectors;

    }

    @AllArgsConstructor
    public static class Sector {
        public int id;
        public String sector;
        public Boolean isAvailable;

    }

    @Transactional
    public void deleteAllByZone(String zone) {
        storageRepository.deleteAllByZone(zone);
    }
}
