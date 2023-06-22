package gb.library.pd.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    private final JwtTokenUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(BEARER)) {
            String jwtString = authHeader.substring(BEARER.length());
            try {

                Claims claims = jwtTokenUtil.getClaimsFromToken(jwtString);
                Long userID = jwtTokenUtil.getUserIdFromClaims(claims);
                AccessGroupType groupType = jwtTokenUtil.getAccessGroupTypeFromClaims(claims);

                SecurityContext securityContext = SecurityContextHolder.getContext();
                if (userID != null && groupType != null && securityContext.getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken token =
                            new UsernamePasswordAuthenticationToken(
                                    String.valueOf(userID),
                                    null,
                                    List.of(new SimpleGrantedAuthority(groupType.getRole())));

                    securityContext.setAuthentication(token);
                }

            } catch (JwtException e) {
                log.error(e.getMessage(), e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
