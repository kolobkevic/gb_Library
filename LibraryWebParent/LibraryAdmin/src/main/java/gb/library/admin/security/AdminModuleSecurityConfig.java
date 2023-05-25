package gb.library.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AdminModuleSecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/home").permitAll()
//                        .anyRequest().authenticated()
//                )

        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()    //старая версия, временое решение
                //.anyRequest().authenticated()   //временно ОТКЛ
                .anyRequest().permitAll()       //временно ВКЛ
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .usernameParameter("email")
                        .permitAll()
                .and()
                    .logout().permitAll()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

}
