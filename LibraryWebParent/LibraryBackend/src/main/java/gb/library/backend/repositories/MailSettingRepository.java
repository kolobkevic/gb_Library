package gb.library.backend.repositories;

import gb.library.common.entities.MailSetting;
import gb.library.common.enums.SettingsKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailSettingRepository extends JpaRepository<MailSetting, Integer> {
    MailSetting findByKeyName(SettingsKey settingsKey);
}
