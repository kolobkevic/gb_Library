package gb.library.pd.entity.embedded;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MainInfo {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "passport")
    private String passport;

}
