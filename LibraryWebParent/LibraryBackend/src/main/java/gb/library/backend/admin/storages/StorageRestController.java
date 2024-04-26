package gb.library.backend.admin.storages;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StorageRestController {

    private final StorageService service;

    @PostMapping("/storages/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("sector") String sector,
                              @Param("zone") String zone) {
        return service.checkUnique(id, sector, zone);
    }
}
