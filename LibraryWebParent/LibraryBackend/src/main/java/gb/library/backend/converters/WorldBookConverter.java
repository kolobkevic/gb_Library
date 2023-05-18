package gb.library.backend.converters;

import gb.library.common.dtos.WorldBookDTO;
import gb.library.common.entities.WorldBook;
import org.springframework.stereotype.Component;

@Component
public class WorldBookConverter {
    public WorldBookDTO entityToDto(WorldBook worldBook){
        return new WorldBookDTO(worldBook.getId(),
                worldBook.getTitle(),
                worldBook.getAuthor(),
                worldBook.getGenre(),
                worldBook.getAgeRating(),
                worldBook.getDescription());

    }
    public WorldBook dtoToEntity(WorldBookDTO worldBookDTO){
        WorldBook worldBook =new WorldBook();
        if (worldBookDTO.getId()!=0)
            worldBook.setId(worldBookDTO.getId());


        worldBook.setTitle(worldBookDTO.getTitle());
        worldBook.setAuthor(worldBookDTO.getAuthor());
        worldBook.setGenre(worldBookDTO.getGenre());
        worldBook.setAgeRating(worldBookDTO.getAgeRating());
        worldBook.setDescription(worldBookDTO.getDescription());
        return worldBook;

    }


}
