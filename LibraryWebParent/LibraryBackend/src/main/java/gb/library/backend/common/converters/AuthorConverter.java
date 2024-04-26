package gb.library.backend.common.converters;

import gb.library.common.dtos.AuthorDTO;
import gb.library.common.entities.Author;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthorConverter {

    private final ModelMapper mapper;

    public Author dto2entity(AuthorDTO dto){
        return mapper.map(dto, Author.class);
    }

    public AuthorDTO entity2dto(Author entity){
        return mapper.map(entity, AuthorDTO.class);
    }

    public List<AuthorDTO> listEntity2dto(List<Author> listEntities){
        return listEntities.stream().map(this::entity2dto).collect(Collectors.toList());
    }
}
