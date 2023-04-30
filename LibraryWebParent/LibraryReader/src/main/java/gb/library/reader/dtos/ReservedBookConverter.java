package gb.library.reader.dtos;

import gb.library.common.entities.ReservedBook;
import org.springframework.stereotype.Component;

@Component
public class ReservedBookConverter {
    public ReservedBookDto entityToDto(ReservedBook book) {
        return new ReservedBookDto(book.getId(), book.getWorldBook().getTitle(),
                book.getLibraryBook().getInventoryNumber(), book.getUser().getEmail(),
                book.getWorldBook().getAuthor().getFirstName() + " " +
                        book.getWorldBook().getAuthor().getLastName(), book.getCreatedAt());
    }

//    public ReservedBook DtoToEntity(ReservedBookDto bookDto) {
//        ReservedBook book = new ReservedBook();
//        book.setId(bookDto.getId());
//        return new ReservedBook();
//    }
}
