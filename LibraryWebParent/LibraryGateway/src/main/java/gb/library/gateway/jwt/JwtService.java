package gb.library.gateway.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    public void validateToken(final String token) {
        Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
