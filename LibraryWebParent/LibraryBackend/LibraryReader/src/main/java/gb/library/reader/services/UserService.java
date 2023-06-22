package gb.library.reader.services;

import gb.library.backend.services.VerificationMailService;
import gb.library.backend.services.RoleService;
import gb.library.common.enums.RegistrationType;
import gb.library.common.enums.RoleType;
import gb.library.common.entities.User;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.backend.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Set;

@Service("readerUserService")
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository repository;
    private final RoleService roleService;
    private final VerificationMailService mailService;

    private static final RoleType USER_ROLE = RoleType.USER;

    public User findById(int id) {
        return repository.findById(id).orElseThrow(() ->
                new ObjectInDBNotFoundException("Пользователь не найден в базе", "User"));
    }

    public User update(User user) {
        User updatedUser = repository.findById(user.getId()).orElseThrow(() ->
                new ObjectInDBNotFoundException("Пользователь не найден в базе", "User"));
        updatedUser.setEmail(user.getEmail());
        updatedUser.setFullName(user.getFullName());
        updatedUser.setPassword(user.getPassword());
        repository.save(updatedUser);
        return updatedUser;
    }

    public void delete(Integer userId) {
        repository.deleteById(userId);
    }

    public User create(User user, RegistrationType type, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {
        user.setRegistrationType(type);
        user.setRoles(Set.of(roleService.getUserRoleByRoleType(USER_ROLE)));
        user.setEnabled(false);
        user.setVerificationCode(RandomStringUtils.random(64, true, true));
        sendVerificationEmail(request, user);
        return repository.save(user);
    }

    public boolean isEmailUnique(String email){
        User user = repository.findUserByEmail(email);
        return user == null;
    }

    private void sendVerificationEmail(HttpServletRequest request, User user)
            throws MessagingException, UnsupportedEncodingException {
        mailService.sendVerificationEmail(request, user);
    }

    public boolean verifyCode(String verificationCode){
        User user = repository.findUserByVerificationCode(verificationCode);
        if(user == null || user.isEnabled()){
            return false;
        } else {
            repository.enable(user.getId());
            return true;
        }
    }
}
