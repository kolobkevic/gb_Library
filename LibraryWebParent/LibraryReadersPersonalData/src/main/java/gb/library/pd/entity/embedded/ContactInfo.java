package gb.library.pd.entity.embedded;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ContactInfo {

    @Column(name = "phone1")
    private String phone1;

    @Column(name = "phone2")
    private String phone2;

    @Column(name = "email")
    private String email;
}
