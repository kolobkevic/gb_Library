package gb.library.official.converters;

import gb.library.common.dtos.GenreDTO;
import gb.library.common.entities.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public GenreDTO entityToDto(Genre genre){
        return new GenreDTO(genre.getId(), genre.getName(), genre.getDescription());

    }

}
