package gb.library.reader.controllers;

import gb.library.common.entities.ReservedBook;
import gb.library.reader.converters.ReservedBookConverter;
import gb.library.reader.dtos.ReservedBookDto;
import gb.library.reader.services.ReservedBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reserved")
@CrossOrigin
public class ReservedBookController {
    private final ReservedBooksService reservedBooksService;
    private final ReservedBookConverter converter;
    private final static String PAGE_INDEX_DEFAULT = "1";
    private final static String PAGE_SIZE_DEFAULT = "10";

    @GetMapping("/{userId}")
    public Page<ReservedBookDto> findAll(@RequestParam(defaultValue = PAGE_INDEX_DEFAULT, name = "page") int pageIndex,
                                         @RequestParam(defaultValue = PAGE_SIZE_DEFAULT, name = "size") int pageSize,
                                         @PathVariable int userId) {
        return reservedBooksService.getAllPageable(userId, pageIndex, pageSize).map(converter::entityToDto);
    }

//    @GetMapping("/{userId}/sorted") // TODO Доделать сортировку
//    public Page<ReservedBookDto> findAllSorted(@RequestParam(defaultValue = PAGE_INDEX_DEFAULT, name = "page") int pageIndex,
//                                               @RequestParam(defaultValue = PAGE_SIZE_DEFAULT, name = "size") int pageSize,
//                                               @RequestParam(defaultValue = "title, asc") String[] sort,
//                                               @PathVariable int userId) {
//
//        if (sort[0].contains(",")) {
//            return reservedBooksService.getAllPageable(userId, pageIndex, pageSize, Sort.Direction.ASC, sort)
//                    .map(converter::entityToDto);
//        } else {
//            return reservedBooksService.getAllPageable(userId, pageIndex, pageSize, Sort.Direction.DESC, sort)
//                    .map(converter::entityToDto);
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public void deleteFromReserved(@PathVariable int id) {
        reservedBooksService.delete(id);
    }

    @PostMapping("/create")
    public ReservedBookDto reserveBook(@RequestBody ReservedBookDto reservedBookDto) {
        ReservedBook book = reservedBooksService.create(converter.DtoToEntity(reservedBookDto));
        return converter.entityToDto(book);
    }
}
