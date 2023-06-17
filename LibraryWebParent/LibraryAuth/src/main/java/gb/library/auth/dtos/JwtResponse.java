package gb.library.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class JwtResponse {
    private String token;
}