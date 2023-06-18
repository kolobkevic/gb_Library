package gb.library.gateway.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    public Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build();

        try {
            return jwtParser.parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SecurityException |
                 IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean validateToken(String token) {
        return parseToken(token) != null;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        if(claims != null){
            return claims.getSubject();
        }
        return null;
    }
}
