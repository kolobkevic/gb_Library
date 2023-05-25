package gb.library.common.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "mail_settings")
@Getter
public class MailSetting {
    @Id
    @Column(name = "key_name", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private SettingsKey keyName;
    @Column(nullable = false, length = 500)
    private String value;

}
