package gb.library.common.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "mail_settings")
public class MailSetting {
    @Id
    @Column(name = "key_name", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private SettingsKey keyName;
    @Column(nullable = false, length = 124)
    private String value;

}
