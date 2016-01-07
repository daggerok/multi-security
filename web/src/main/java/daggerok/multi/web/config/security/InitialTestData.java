package daggerok.multi.web.config.security;

import daggerok.multi.data.user.User;
import daggerok.multi.data.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = "daggerok")
public class InitialTestData {
    @Bean
    public CommandLineRunner populate(UserRepository userRepository) {
        return args -> Arrays.asList("max,dag,bax".split(","))
                .forEach(name -> userRepository.save(User.of(name, name)));
    }
}
