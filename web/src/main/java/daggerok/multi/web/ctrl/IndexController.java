package daggerok.multi.web.ctrl;

import daggerok.multi.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String index(Model model, final HttpServletRequest request) throws InterruptedException {
        model.addAttribute("users", userRepository.findAll().stream()
                .filter(user -> !user.getUsername().equals(request.getUserPrincipal().getName()))
                .collect(Collectors.toList()));

        model.addAttribute("request", request);

        TimeUnit.SECONDS.sleep(1);
        // fetch some data from DB and external web service endpoint
        model.addAttribute("role", request.isUserInRole("USER"));
        return "index";
    }

    @RequestMapping("/callable")
    public Callable<String> indexCallable(Model model, final HttpServletRequest request) throws InterruptedException {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                model.addAttribute("users", userRepository.findAll().stream()
                        .filter(user -> !user.getUsername().equals(request.getUserPrincipal().getName()))
                        .collect(Collectors.toList()));

                model.addAttribute("request", request);

                TimeUnit.SECONDS.sleep(3);
                // fetch some data from DB and external web service endpoint
                model.addAttribute("role", request.isUserInRole("USER"));
                return "index";
            }
        };
    }
}
