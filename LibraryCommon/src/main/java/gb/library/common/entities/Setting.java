package gb.library.common.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class Setting {
    @Id
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Column(nullable = false, length = 1024)
    private String value;
    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private SettingCategory category;
}
