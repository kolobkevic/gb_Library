package gb.library.backend.utils;

import gb.library.common.entities.SettingsKey;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class Utility {

    public String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public JavaMailSenderImpl prepareMailSender(HashMap<SettingsKey, String> settings){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(settings.get(SettingsKey.MAIL_HOST));
        mailSender.setPort(Integer.parseInt(settings.get(SettingsKey.MAIL_PORT)));
        mailSender.setUsername(settings.get(SettingsKey.MAIL_USERNAME));
        mailSender.setPassword(settings.get(SettingsKey.MAIL_PASSWORD));
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
}
