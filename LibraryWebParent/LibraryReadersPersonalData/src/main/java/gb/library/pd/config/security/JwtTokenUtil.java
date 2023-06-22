package gb.library.pd.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.*;


@Component
public class JwtTokenUtil {

    static private final String GROUP = "group";

    private final SecretKey key;
    private Duration lifetime;


    public JwtTokenUtil(@Value("${jwt.lifetime}") Duration lifetime) {
        // Ключ для подписи токена
        byte[] randomBytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        this.key = Keys.hmacShaKeyFor(randomBytes);
        Arrays.fill(randomBytes, (byte) 0);

        // Время жизни токена
        this.lifetime = lifetime;
    }


    public String generateToken(Long userId, AccessGroupType accessGroupType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(GROUP, accessGroupType);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + lifetime.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(key)
                .compact();
    }


    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public Long getUserIdFromClaims(Claims claims) {
        return Long.valueOf(claims.getSubject());
    }


    public AccessGroupType getAccessGroupTypeFromClaims(Claims claims) {
        return AccessGroupType.valueOf((String) claims.get(GROUP));
    }


    public Duration getLifetime() {
        return lifetime;
    }


    public void setLifetime(Duration lifetime) {
        this.lifetime = lifetime;
    }
}