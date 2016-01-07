package daggerok.multi.web.ctrl;

import daggerok.multi.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("users", userRepository.findAll().stream()
                .filter(user -> !user.getUsername().equals(request.getUserPrincipal().getName()))
                .collect(Collectors.toList()));
        model.addAttribute("request", request);
        return "index";
    }
}