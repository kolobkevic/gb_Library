package gb.library.backend.common.services;

import gb.library.backend.common.repositories.MailSettingRepository;
import gb.library.common.entities.MailSetting;
import gb.library.common.enums.SettingsKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailSettingsService {
    private final MailSettingRepository repository;

    public List<MailSetting> getAllSettingsList() {
        return repository.findAll();
    }

    public Map<SettingsKey, String> getAllMailSettings() {
        return new HashMap<>(getAllSettingsList().stream()
                .collect(Collectors.toMap(MailSetting::getKeyName, MailSetting::getValue)));
    }

    public void save(MailSetting mailSetting){
        repository.save(mailSetting);
    }

    public MailSetting update(MailSetting mailSetting){
        MailSetting updatedSettings = repository.findByKeyName(mailSetting.getKeyName());
        updatedSettings.setValue(mailSetting.getValue());
        updatedSettings.setKeyName(mailSetting.getKeyName());
        return repository.save(updatedSettings);
    }
}
