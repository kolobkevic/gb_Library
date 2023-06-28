package gb.library.reader.converters;

import gb.library.backend.converters.WorldBookConverter;
import gb.library.common.entities.BooksWishlist;
import gb.library.reader.dtos.BooksWishlistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BooksWishlistConverter {
    private final WorldBookConverter worldBookConverter;
    private final UserConverter userConverter;

    public BooksWishlistDto entityToDto(BooksWishlist booksWishlist) {
        return new BooksWishlistDto(booksWishlist.getId(),
                worldBookConverter.entityToDto(booksWishlist.getBook()),
                userConverter.entityToDto(booksWishlist.getUser()));
    }

    public BooksWishlist dtoToEntity(BooksWishlistDto booksWishlistDto) {
        BooksWishlist booksWishlist = new BooksWishlist();
        booksWishlist.setId(booksWishlistDto.getId());
        booksWishlist.setBook(worldBookConverter.dtoToEntity(booksWishlistDto.getBook()));
        booksWishlist.setUser(userConverter.dtoToEntity(booksWishlistDto.getUser()));
        return booksWishlist;
    }
}
