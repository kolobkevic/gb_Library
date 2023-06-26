package gb.library.auth.controllers;

import gb.library.auth.dtos.JwtRequest;
import gb.library.auth.dtos.JwtResponse;
import gb.library.auth.jwt.JwtService;
import gb.library.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest request) throws BadCredentialsException {
        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());

        userDetails = userService.checkPasswordIsEncoded(userDetails, request.getPassword());
        if(passwordEncoder.matches(request.getPassword(), userDetails.getPassword())){
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        throw new BadCredentialsException("Неверные данные пользователя");
    }
}