package gb.library.admin.security;

import gb.library.common.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AdminModuleSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()

                .authorizeHttpRequests()
                .requestMatchers(
                        "/",
                        "/authors/**",
                        "/world-books/**",
                        "/library-books/**",
                        "/wished-books/**",
                        "/ordered-books/**",
                        "/reserved-books/**",
                        "/genres/**",
                        "/roles/**",
                        "/storages/**",
                        "/users/**"
                ).hasRole(RoleType.ADMIN.toString())

                .requestMatchers("/login").permitAll()

                .requestMatchers(
                        "/style.css",
                        "/fontawesome/**",
                        "/js/**",
                        "/webfonts/**",
                        "/webjars/**"
                ).permitAll()

                .anyRequest().denyAll()

                .and()
                .formLogin()
                .loginPage("/login")

                .and()
                .logout()
                .logoutSuccessUrl("/");

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void authenticationManagerConfigure(AuthenticationManagerBuilder authenticationManagerBuilder,
                                               PasswordEncoder passwordEncoder,
                                               UserDetailsService userDetailsService,
                                               @Value("${super-admin.login}") String superAdminLogin,
                                               @Value("${super-admin.password}") String superAdminPassword) throws Exception {

        authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser(superAdminLogin)
                .password(passwordEncoder.encode(superAdminPassword))
                .roles(RoleType.ADMIN.toString());

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        authenticationManagerBuilder.authenticationProvider(provider);
    }
}
