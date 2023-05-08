package gb.library.reader.controllers;

import gb.library.reader.converters.BookOnHandsConverter;
import gb.library.reader.dtos.BookOnHandsDto;
import gb.library.reader.services.BookOnHandsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController("readerBookOnHandsController")
@RequiredArgsConstructor
@RequestMapping("/api/v1/book_on_hands") //придумать что-то получше
@CrossOrigin
public class BookOnHandsController {
    private final BookOnHandsService service;
    private final BookOnHandsConverter converter;

    private final static String PAGE_INDEX_DEFAULT = "1";
    private final static String PAGE_SIZE_DEFAULT = "10";

    @GetMapping("/{userId}")
    public Page<BookOnHandsDto> findAll(@PathVariable int userId,
                                        @RequestParam(defaultValue = PAGE_INDEX_DEFAULT, name = "page") int pageIndex,
                                        @RequestParam(defaultValue = PAGE_SIZE_DEFAULT, name = "size") int pageSize){
        return service.getAllPageable(userId, pageIndex, pageSize).map(converter::entityToDto);
    }


}
