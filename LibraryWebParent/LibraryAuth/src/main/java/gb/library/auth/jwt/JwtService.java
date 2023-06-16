package gb.library.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    private Claims parseToken(String token) {
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

    public String generateToken(String username) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        var currentDate = new Date();
        var expiration = new Date(currentDate.getTime() + jwtLifetime.toMillis());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }
}
