package gb.library.official.services;

import gb.library.backend.specifications.StorageSpecification;
import gb.library.common.entities.Storage;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.backend.repositories.StorageRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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


    @Transactional
    public Storage update(Storage storageToUpdate) {
        Storage currentStorage = storageRepository.findById(storageToUpdate.getId()).orElseThrow(
                () -> new ObjectInDBNotFoundException("Место хранения не найдено базе, id: " + storageToUpdate.getId(), "Storage"));
        currentStorage.setZone(storageToUpdate.getZone());
        currentStorage.setSector(storageToUpdate.getSector());
        currentStorage.setAvailable(storageToUpdate.isAvailable());
        return storageRepository.save(currentStorage);
    }

    public List<Zone> getStorageZones(String zone, String sector, String available) {
        Specification<Storage> specification = Specification.where(null);

        if (zone != null && !zone.isBlank()) {
            specification = specification.and(StorageSpecification.zoneLike(zone));
        }
        if (sector != null && !sector.isBlank()) {
            specification = specification.and(StorageSpecification.sectorLike(sector));
        }
        if (available != null && !available.isBlank()) {
            specification = specification.and(StorageSpecification.availableCheck(Boolean.parseBoolean(available)));
        }

        Map<String, List<Sector>> zones = new HashMap<>();
        List<Storage> storages = storageRepository.findAll(specification);

        for (Storage storage : storages) {
            List<Sector> sectors = zones.computeIfAbsent(storage.getZone(), k -> new ArrayList<>());
            sectors.add(new Sector(storage.getId(), storage.getSector(), storage.isAvailable()));
        }

        List<Zone> zonesList = new ArrayList<>();
        for (Map.Entry<String, List<Sector>> stringListEntry : zones.entrySet()) {
            zonesList.add(new Zone(stringListEntry.getKey(), stringListEntry.getValue()));
        }

        return zonesList;
    }

    public Zone findZoneByTitle(String zoneTitle) {
        Map<String, List<Sector>> zoneData = new HashMap<>();
        List<Storage> storages = storageRepository.findAllByZone(zoneTitle);
        for (Storage storage : storages) {
            List<Sector> sectors = zoneData.computeIfAbsent(storage.getZone(), k -> new ArrayList<>());
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
        public Boolean available;

    }

    @Transactional
    public void deleteAllByZone(String zone) {
        storageRepository.deleteAllByZone(zone);
    }
}
