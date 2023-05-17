package gb.library.official.controllers;

import gb.library.common.entities.Author;
import gb.library.official.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

}
