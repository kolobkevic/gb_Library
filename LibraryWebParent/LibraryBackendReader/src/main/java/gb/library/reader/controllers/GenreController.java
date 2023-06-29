package gb.library.reader.controllers;


import gb.library.backend.converters.GenreConverter;
import gb.library.backend.services.GenreCommonService;
import gb.library.common.dtos.GenreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@CrossOrigin("*")
@RequiredArgsConstructor
public class GenreController {
    private final GenreCommonService genreService;
    private final GenreConverter converter;

    @GetMapping
    public List<GenreDTO> findAll(@RequestParam(name = "name", required = false) String name,
                                  @RequestParam(name = "description", required = false) String description) {
        return converter.listEntity2Dto(genreService.findAll(name, description));
    }
}
