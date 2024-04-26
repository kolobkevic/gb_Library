package gb.library.backend.common.services;

import gb.library.backend.common.configs.MailSender;
import gb.library.common.enums.SettingsKey;
import gb.library.common.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@RequiredArgsConstructor
public abstract class AbstractMailService {
    private final SpringTemplateEngine templateEngine;
    private final MailSettingsService mailService;
    private final MailSender mailSender;

    public void sendEmail(User user, String mailContent, String subject, Context context)
            throws MessagingException, UnsupportedEncodingException {
        HashMap<SettingsKey, String> mailSettings = mailService.getAllMailSettings();
        MimeMessage message = mailSender.prepareMailSender(mailSettings).createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(mailSettings.get(SettingsKey.MAIL_FROM), mailSettings.get(SettingsKey.MAIL_SENDER_NAME));
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(templateEngine.process(mailContent, context), true);
        mailSender.getMailSender().send(message);
    }
}
