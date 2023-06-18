package gb.library.gateway.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    private final JwtService jwtService;
    private final RouteValidator validator;

    @Autowired
    public JwtAuthFilter(JwtService jwtService, RouteValidator validator) {
        super(Config.class);
        this.jwtService = jwtService;
        this.validator = validator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (validator.isSecured.test(request)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtService.validateToken(authHeader);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Unauthorized access");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }

}
