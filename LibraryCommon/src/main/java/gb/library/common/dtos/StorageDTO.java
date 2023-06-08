package gb.library.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StorageDTO {
    private Integer id;
    private String zone;
    private String sector;
    private boolean isAvailable;
}
