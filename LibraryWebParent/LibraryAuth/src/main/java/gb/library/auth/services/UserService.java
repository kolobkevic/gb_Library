package gb.library.auth.services;

import gb.library.auth.repositories.UserRepository;
import gb.library.common.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email '%s' not found", email)));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(
                        role -> new SimpleGrantedAuthority(role.getRoleType().name())).toList()
        );
    }

    //временное решение до переноса пользователей и ролей в микросервис Auth
    @Transactional
    public UserDetails checkPasswordIsEncoded(UserDetails userDetails, String rawPassword) {
        if(rawPassword.equals(userDetails.getPassword())) {
            String newPass = passwordEncoder.encode(userDetails.getPassword());
            User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
            user.setPassword(newPass);
            userRepository.save(user);
            return new org.springframework.security.core.userdetails.User(
                    userDetails.getUsername(),
                    newPass,
                    userDetails.getAuthorities());
        }
        return userDetails;
    }
}
