package gb.library.backend.common.configs;

import gb.library.common.enums.SettingsKey;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;

@Configuration
@Getter
public class MailSender extends JavaMailSenderImpl{
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    public JavaMailSenderImpl prepareMailSender(HashMap<SettingsKey, String> settings) {
        mailSender.setHost(settings.get(SettingsKey.MAIL_HOST));
        mailSender.setPort(Integer.parseInt(settings.get(SettingsKey.MAIL_PORT)));
        mailSender.setUsername(settings.get(SettingsKey.MAIL_USERNAME));
        mailSender.setPassword(settings.get(SettingsKey.MAIL_PASSWORD));
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
}
