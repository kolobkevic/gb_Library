package gb.library.reader.controllers;


import gb.library.backend.converters.LibraryBookConverter;
import gb.library.backend.services.LibraryBookCommonService;
import gb.library.common.dtos.LibraryBookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/library_books")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LibraryBookController {
    private final LibraryBookCommonService libraryBookCommonService;
    private final LibraryBookConverter libraryBookConverter;

    @GetMapping("/{id}")
    public List<LibraryBookDTO> findAllByWorldBookId(@PathVariable Integer id) {
        return libraryBookCommonService.findAllByWorldBookId(id).stream().map(libraryBookConverter::entityToDto).toList();
    }
}
