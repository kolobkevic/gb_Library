package gb.library.official.controllers;

import gb.library.backend.converters.StorageConverter;
import gb.library.common.dtos.StorageDTO;
import gb.library.common.entities.Storage;
import gb.library.official.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/storage")
@CrossOrigin("*")
public class StorageController {
    private final StorageService storageService;
    private final StorageConverter storageConverter;


    @GetMapping("/zones")
    public List<StorageService.Zone> getAllZones(
            @RequestParam(name = "zone", required = false) String zone,
            @RequestParam(name = "sector", required = false) String sector,
            @RequestParam(name = "available", required = false) String available) {
        return storageService.getStorageZones(zone, sector, available);
    }

    @GetMapping("/zones/{zoneTitle}")
    public StorageService.Zone findZoneByTitle(@PathVariable String zoneTitle) {
        return storageService.findZoneByTitle(zoneTitle);
    }

    @GetMapping
    public StorageDTO findByZoneAndSector(@RequestParam(name = "zone", required = true, defaultValue = "zone1") String zone,
                                          @RequestParam(name = "sector", required = true, defaultValue = "sector1") String sector) {
        return storageConverter.entityToDto(storageService.findByZoneAndSector(zone, sector));
    }

    @PostMapping
    public StorageDTO save(@RequestBody StorageDTO storageDTO) {
        return storageConverter.entityToDto(storageService.save(storageConverter.dtoToEntity(storageDTO)));
    }

    @PutMapping
    public StorageDTO update(@RequestBody StorageDTO storageDTO) {
        System.out.println(storageDTO.getZone() + " " + storageDTO.getSector());
        return storageConverter.entityToDto(storageService.update(storageConverter.dtoToEntity(storageDTO)));
    }

    @DeleteMapping("/{zoneTitle}")
    public void delete(@PathVariable String zoneTitle) {
        storageService.deleteAllByZone(zoneTitle);
    }

    @DeleteMapping("sectors/{id}")
    public void deleteSectorById(@PathVariable Integer id) {
        storageService.deleteById(id);
    }
}
