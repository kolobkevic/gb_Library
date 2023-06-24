package gb.library.reader.controllers;


import gb.library.reader.converters.BooksWishlistConverter;
import gb.library.reader.dtos.BooksWishlistDto;
import gb.library.reader.services.BooksWishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/wishlist")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BooksWishlistController {
    private final BooksWishlistService booksWishlistService;
    private final BooksWishlistConverter booksWishlistConverter;

    @GetMapping("/{userLogin}")
    public Page<BooksWishlistDto> findAllByUserLogin(@PathVariable String userLogin,
            @RequestParam(name = "p", defaultValue = "1", required = false) Integer pageIndex,
            @RequestParam(name = "page_size", defaultValue = "10", required = false) Integer pageSize) {
        if (pageIndex < 1) pageIndex = 1;
        return booksWishlistService.findAll(userLogin,pageIndex - 1, pageSize).map(booksWishlistConverter::entityToDto);
    }

    @GetMapping("/{userLogin}/add/{worldBookId}")
    public void addWorldBookToWishlist(@PathVariable String userLogin, @PathVariable Integer worldBookId) {
        booksWishlistService.addToWishlist(userLogin, worldBookId);
    }

    @DeleteMapping("/{recordId}")
    public void delete(@PathVariable Integer recordId) {
        booksWishlistService.delete(recordId);
    }
}
