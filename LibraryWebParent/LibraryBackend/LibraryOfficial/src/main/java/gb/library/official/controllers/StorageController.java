package gb.library.official.controllers;

import gb.library.common.entities.Storage;
import gb.library.official.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/storage")
@CrossOrigin
public class StorageController {

    private final StorageService storageService;


    @GetMapping("/zones")
    public List<StorageService.Zone> getAllZones() {
        return storageService.getStorageZones();
    }

    @GetMapping("/zones/{zoneTitle}")
    public StorageService.Zone findZoneByTitle(@PathVariable String zoneTitle) {
        return storageService.findZoneByTitle(zoneTitle);
    }

    @PostMapping
    public Storage save(@RequestBody Storage storage) {
        return storageService.save(storage);
    }
}
