package gb.library.backend.admin.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
