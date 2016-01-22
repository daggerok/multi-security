package daggerok.multi.web.ctrl;

import daggerok.multi.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/login")
    public String index(Model model, final HttpServletRequest request) throws InterruptedException {
        return "login";
    }
}
