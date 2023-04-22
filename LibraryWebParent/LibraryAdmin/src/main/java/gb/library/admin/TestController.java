package gb.library.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }
}
