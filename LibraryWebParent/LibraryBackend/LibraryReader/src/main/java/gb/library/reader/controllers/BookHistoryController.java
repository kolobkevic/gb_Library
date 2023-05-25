package gb.library.reader.controllers;

import gb.library.reader.converters.BookHistoryConverter;
import gb.library.reader.dtos.BookHistoryReaderDto;
import gb.library.reader.services.BookHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController("readerBookHistoryController")
@RequiredArgsConstructor
@RequestMapping("/api/v1/book_history")
@CrossOrigin
public class BookHistoryController {
    private final BookHistoryService service;
    private final BookHistoryConverter converter;

    private final static String PAGE_INDEX_DEFAULT = "1";
    private final static String PAGE_SIZE_DEFAULT = "10";

    @GetMapping("/{userId}")
    public Page<BookHistoryReaderDto> findAll(@PathVariable int userId,
                                              @RequestParam(defaultValue = PAGE_INDEX_DEFAULT, name = "page") int pageIndex,
                                              @RequestParam(defaultValue = PAGE_SIZE_DEFAULT, name = "size") int pageSize,
                                              @RequestParam(defaultValue = "false") boolean unReturned) {
        return service.getBooksOnHands(userId, pageIndex, pageSize, unReturned).map(converter::entityToDto);
    }


}
