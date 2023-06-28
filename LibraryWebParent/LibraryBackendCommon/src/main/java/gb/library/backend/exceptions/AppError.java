package gb.library.backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppError {
    private int code;
    private String message;
    private String droppedBy;

    public AppError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
