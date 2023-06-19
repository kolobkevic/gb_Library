package gb.library.gateway.jwt;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/authenticate",
            "reader/api/v1/users/create",
            "reader/api/v1/users/verify",
            "reader/api/v1/users/check_email",
            "reader/api/v1/books_catalog",
            "reader/api/v1/genres"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
