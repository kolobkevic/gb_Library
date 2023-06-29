package gb.library.backend.converters;

import gb.library.common.dtos.AuthorDTO;
import gb.library.common.dtos.GenreDTO;
import gb.library.common.dtos.WorldBookDTO;
import gb.library.common.entities.Author;
import gb.library.common.entities.Genre;
import gb.library.common.entities.WorldBook;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WorldBookConverter {

    private final ModelMapper mapper;

    public WorldBookDTO entityToDto(WorldBook worldBook) {

        WorldBookDTO dto = mapper.map(worldBook, WorldBookDTO.class);
        dto.setAuthorDTO(mapper.map(worldBook.getAuthor(), AuthorDTO.class));
        dto.setGenreDTO(mapper.map(worldBook.getGenre(), GenreDTO.class));

        return dto;
    }

    public WorldBook dtoToEntity(WorldBookDTO dto) {

        WorldBook book = mapper.map(dto, WorldBook.class);
        book.setAuthor(mapper.map(dto.getAuthorDTO(), Author.class));
        book.setGenre(mapper.map(dto.getGenreDTO(), Genre.class));

        return book;
    }

    public List<WorldBookDTO> listEntities2dto(List<WorldBook> bookList){
        return bookList.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }

}
