package gb.library.backend.services;

import gb.library.backend.repositories.MailSettingRepository;
import gb.library.common.entities.MailSetting;
import gb.library.common.entities.SettingsKey;
import gb.library.common.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractMailService {
    private final MailSettingRepository repository;
    private final SpringTemplateEngine templateEngine;

    public List<MailSetting> getAllSettingsList() {
        return repository.findAll();
    }

    public HashMap<SettingsKey, String> getAllMailSettings() {
        return new HashMap<>(getAllSettingsList().stream()
                .collect(Collectors.toMap(MailSetting::getKeyName, MailSetting::getValue)));
    }

    public void sendEmail(User user, String mailContent, String subject, Context context)
            throws MessagingException, UnsupportedEncodingException {
        HashMap<SettingsKey, String> mailSettings = getAllMailSettings();
        JavaMailSenderImpl mailSender = prepareMailSender(mailSettings);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(mailSettings.get(SettingsKey.MAIL_FROM), mailSettings.get(SettingsKey.MAIL_SENDER_NAME));
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(templateEngine.process(mailContent, context), true);
        mailSender.send(message);
    }

    public JavaMailSenderImpl prepareMailSender(HashMap<SettingsKey, String> settings) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(settings.get(SettingsKey.MAIL_HOST));
        mailSender.setPort(Integer.parseInt(settings.get(SettingsKey.MAIL_PORT)));
        mailSender.setUsername(settings.get(SettingsKey.MAIL_USERNAME));
        mailSender.setPassword(settings.get(SettingsKey.MAIL_PASSWORD));
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
}
