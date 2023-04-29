package gb.library.reader.controllers;

import gb.library.reader.dtos.ReservedBookConverter;
import gb.library.reader.dtos.ReservedBookDto;
import gb.library.reader.services.ReservedBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reserved")
public class ReservedBookController {
    private final ReservedBooksService reservedBooksService;
    private final ReservedBookConverter converter;
    private final static String PAGE_INDEX_DEFAULT = "1";
    private final static String PAGE_SIZE_DEFAULT = "10";

    @GetMapping
    public Page<ReservedBookDto> findAll(@RequestParam(defaultValue = PAGE_INDEX_DEFAULT, name = "page") int pageIndex,
                                         @RequestParam(defaultValue = PAGE_SIZE_DEFAULT, name = "size") int pageSize) {
        return reservedBooksService.getAllPageable(pageIndex, pageSize).map(converter::entityToDto);
    }

//    @GetMapping("/sorted")
//    public Page<ReservedBook> findAllSorted(@RequestParam(defaultValue = PAGE_INDEX_DEFAULT, name = "page") int pageIndex,
//                                            @RequestParam(defaultValue = PAGE_SIZE_DEFAULT, name = "size") int pageSize,
//                                            @RequestParam(defaultValue = "title, asc") String[] sortProperties) {
//
//        if (sortProperties[0].contains(",")) {
//            return reservedBooksService.getAllPageable(pageIndex, pageSize, Sort.Direction.ASC, sortProperties);
//        } else {
//            return reservedBooksService.getAllPageable(pageIndex, pageSize, Sort.Direction.DESC, sortProperties);
//        }
//    }
}
