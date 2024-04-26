package gb.library.backend.common.services;

import gb.library.backend.common.configs.MailSender;
import gb.library.common.entities.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class VerificationMailService extends AbstractMailService{
    private static final String VERIFY_ADDRESS = "/verify?code=";
    private static final String MESSAGE_SUBJECT = "Verification email";
    private static final String MAIL_CONTENT = "verification_mail.html";
    private final Context context = new Context();

    public VerificationMailService(SpringTemplateEngine templateEngine, MailSettingsService mailService,
                                   MailSender mailSender) {
        super(templateEngine, mailService, mailSender);
    }

    public void sendVerificationEmail(HttpServletRequest request, User user)
            throws MessagingException, UnsupportedEncodingException {
        generateMailContent(request, user);
        super.sendEmail(user, MAIL_CONTENT, MESSAGE_SUBJECT, context);
    }

    private void generateMailContent(HttpServletRequest request, User user) {
        String verifyURL = getSiteURL(request) + VERIFY_ADDRESS + user.getVerificationCode();
        Map<String, Object> contextVariables = new HashMap<>();
        contextVariables.put("username", user.getFullName());
        contextVariables.put("verifyURL", verifyURL);
        context.setVariables(contextVariables);
    }

    public String getSiteURL(HttpServletRequest request) {
        return "http://localhost:5555/reader/api/v1/users";
    }
}
