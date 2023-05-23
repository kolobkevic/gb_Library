package gb.library.backend.services;

import gb.library.backend.repositories.MailSettingRepository;
import gb.library.backend.utils.Utility;
import gb.library.common.entities.MailSetting;
import gb.library.common.entities.SettingsKey;
import gb.library.common.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailSettingRepository repository;
    private final Utility utility;
    private static final String VERIFY_ADDRESS = "/verify?code=";
    private static final String REPLACING_NAME = "[[name]]";
    private static final String REPLACING_URL = "[[URL]]";

    public List<MailSetting> getAllSettingsList() {
        return repository.findAll();
    }

    public HashMap<SettingsKey, String> getAllMailSettings() {
        return new HashMap<SettingsKey, String>(getAllSettingsList().stream()
                .collect(Collectors.toMap(MailSetting::getKeyName, MailSetting::getValue)));
    }

    public void sendVerificationEmail(HttpServletRequest request, User user)
            throws MessagingException, UnsupportedEncodingException {
        HashMap<SettingsKey, String> mailSettings = getAllMailSettings();
        JavaMailSenderImpl mailSender = utility.prepareMailSender(mailSettings);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String mailContent;
        String verifyURL;

        helper.setFrom(mailSettings.get(SettingsKey.MAIL_FROM), mailSettings.get(SettingsKey.MAIL_SENDER_NAME));
        helper.setTo(user.getEmail());
        helper.setSubject(mailSettings.get(SettingsKey.MAIL_SUBJECT));

        mailContent = mailSettings.get(SettingsKey.MAIL_CONTENT).replace(REPLACING_NAME, user.getFullName());

        verifyURL = utility.getSiteURL(request) + VERIFY_ADDRESS + user.getVerificationCode();
        mailContent = mailContent.replace(REPLACING_URL, verifyURL);

        helper.setText(mailContent, true);
        mailSender.send(message);
    }
}
