package gb.library.official.validators;

import gb.library.common.dtos.GenreDTO;
import gb.library.common.exceptions.ValidateException;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class GenreValidator {
    public void validate(GenreDTO genreDTO){
        List<String> errors = new ArrayList<>();
        if (genreDTO.getName().isBlank()){
            errors.add("Не заполнено название");
        }
        if (genreDTO.getDescription().isBlank())
            errors.add("Не заполнено описание");
        if (!errors.isEmpty()){
            throw new ValidateException(errors);
        }
    }
}