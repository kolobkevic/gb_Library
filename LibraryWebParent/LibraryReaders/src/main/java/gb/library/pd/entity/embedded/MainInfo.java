package gb.library.pd.entity.embedded;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MainInfo {

    @Column(name = "family")
    private String family;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private LocalDate birthday;
}
