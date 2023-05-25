package gb.library.backend.converters;

import gb.library.backend.services.AuthorCommonService;
import gb.library.common.dtos.AuthorDTO;
import gb.library.common.dtos.GenreDTO;
import gb.library.common.dtos.WorldBookDTO;
import gb.library.common.entities.Author;
import gb.library.common.entities.WorldBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorldBookConverter {
    private final GenreConverter genreConverter;
    private final AuthorCommonService authorCommonService;

    public WorldBookDTO entityToDto(WorldBook worldBook) {
        return new WorldBookDTO(worldBook.getId(),
                worldBook.getTitle(),
                new AuthorDTO(worldBook.getAuthor().getId(), worldBook.getAuthor().getFirstName(), worldBook.getAuthor().getLastName()),
                genreConverter.entityToDto(worldBook.getGenre()),
                worldBook.getAgeRating(),
                worldBook.getDescription());

    }

    public WorldBook dtoToEntity(WorldBookDTO worldBookDTO) {
        WorldBook worldBook = new WorldBook();
        if (worldBookDTO.getId() != 0)
            worldBook.setId(worldBookDTO.getId());


        worldBook.setTitle(worldBookDTO.getTitle());
        worldBook.setAuthor(authorCommonService.findById(worldBookDTO.getAuthorDTO().getId()));
        worldBook.setGenre(genreConverter.dtoToEntity(worldBookDTO.getGenreDTO()));
        worldBook.setAgeRating(worldBookDTO.getAgeRating());
        worldBook.setDescription(worldBookDTO.getDescription());
        return worldBook;

    }


}
