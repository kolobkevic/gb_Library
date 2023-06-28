package gb.library.pd.service;

import gb.library.pd.config.security.AccessGroupType;
import gb.library.pd.config.security.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;


@SpringBootTest
@ActiveProfiles("test")
public class JwtTokenUtilTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @ParameterizedTest
    @MethodSource("dataForGenerateToken")
    public void testGenerateToken(Long userId, AccessGroupType userAccessGroup) {
        String token = jwtTokenUtil.generateToken(userId, userAccessGroup);

        Claims claims = jwtTokenUtil.getClaimsFromToken(token);
        Long userIdFromToken = jwtTokenUtil.getUserIdFromClaims(claims);
        AccessGroupType accessGroupTypeFromToken = jwtTokenUtil.getAccessGroupTypeFromClaims(claims);

        System.out.printf("User-ID: %d, AccessGroupType: %s%n", userIdFromToken, accessGroupTypeFromToken);

        Assertions.assertEquals(userId, userIdFromToken);
        Assertions.assertEquals(userAccessGroup, accessGroupTypeFromToken);
    }

    public static Stream<Arguments> dataForGenerateToken() {
        return Stream.of(
                Arguments.arguments(121L, AccessGroupType.ADMINISTRATOR),
                Arguments.arguments(485L, AccessGroupType.EMPLOYEE),
                Arguments.arguments(951L, AccessGroupType.USER)
        );
    }
}
