package gb.library.backend.common.converters;

import gb.library.common.dtos.GenreDTO;
import gb.library.common.entities.Genre;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreConverter {

    private final ModelMapper mapper;

    public GenreDTO entityToDto(Genre entity){
        return mapper.map(entity, GenreDTO.class);
    }

    public Genre dtoToEntity(GenreDTO dto){
        return mapper.map(dto, Genre.class);
    }

    public List<GenreDTO> listEntity2Dto(List<Genre> listEntity){
        return listEntity.stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }
}
