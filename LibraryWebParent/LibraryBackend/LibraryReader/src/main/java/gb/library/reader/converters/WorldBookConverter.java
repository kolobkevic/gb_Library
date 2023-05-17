package gb.library.reader.converters;

import gb.library.common.entities.AgeRating;
import gb.library.common.entities.WorldBook;
import gb.library.reader.dtos.WorldBookDto;
import gb.library.reader.services.ReservedBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("readerWorldBookConverter")
@RequiredArgsConstructor
public class WorldBookConverter {
    private final ReservedBooksService service;

    public WorldBookDto entityToDto(WorldBook book) {
        return new WorldBookDto(book.getId(), book.getAuthor().getFullName(), book.getTitle(), book.getGenre().getName(),
                book.getAgeRating().name(), book.getDescription());
    }

    public WorldBook dtoToEntity(WorldBookDto worldBookDto) {
        WorldBook book = service.findWorldBookById(worldBookDto.getWorldBookId());
        book.getAuthor().setFullName(worldBookDto.getAuthor());
        book.setTitle(worldBookDto.getTitle());
        book.getGenre().setName(worldBookDto.getGenre());
        book.setAgeRating(AgeRating.valueOf(worldBookDto.getAgeRating()));
        book.setDescription(worldBookDto.getDescription());
        return book;
    }
}
