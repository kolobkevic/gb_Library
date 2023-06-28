package gb.library.pd.config;

import gb.library.pd.config.security.AccessGroupType;
import gb.library.pd.config.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String ROLE_ADMINISTRATOR = AccessGroupType.ADMINISTRATOR.toString();
    private static final String ROLE_EMPLOYEE = AccessGroupType.EMPLOYEE.toString();
    private static final String ROLE_USER = AccessGroupType.USER.toString();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                .authorizeHttpRequests()

                // Доступ имеют только администраторы и персонал
                .requestMatchers(HttpMethod.GET, "/api/v1/reader")
                .hasAnyRole(ROLE_ADMINISTRATOR, ROLE_EMPLOYEE)

                // Доступ имеют только администраторы и персонал
                .requestMatchers(HttpMethod.POST, "/api/v1/reader")
                .hasAnyRole(ROLE_ADMINISTRATOR, ROLE_EMPLOYEE)

                // Полный доступ имеют администратора, персонал.
                // Пользователи имеют ограниченный доступ, а именно readerId запроса должен совпадать
                // с ID пользователя, запрашивающего соответствующий ресурс
                .requestMatchers(HttpMethod.GET, "/api/v1/reader/{readerId}")
                .access(new WebExpressionAuthorizationManager(
                        """
                                hasRole(T(gb.library.pd.config.security.AccessGroupType).ADMINISTRATOR.toString()) ||
                                hasRole(T(gb.library.pd.config.security.AccessGroupType).EMPLOYEE.toString()) ||
                                (
                                    hasRole(T(gb.library.pd.config.security.AccessGroupType).USER.toString()) &&
                                    #readerId == authentication.name
                                )
                                """
                ))

                // Доступ имеют только администраторы и персонал
                .requestMatchers(HttpMethod.PATCH, "/api/v1/reader/*")
                .hasAnyRole(ROLE_ADMINISTRATOR, ROLE_EMPLOYEE)

                // Доступ имеют только администраторы
                .requestMatchers(HttpMethod.DELETE, "/api/v1/reader/*")
                .hasRole(ROLE_ADMINISTRATOR)

                // Доступ к документации API имеют все
                .requestMatchers(HttpMethod.GET, "/v3/api-docs")
                .permitAll()

                .anyRequest().denyAll();

        return http.build();
    }
}