package gb.library.backend.common.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDTO {
    private Date timestamp;

    private int status;

    private String path;

    private List<String> errors = new ArrayList<>();

    public void addError(String message){
        errors.add(message);
    }
}
