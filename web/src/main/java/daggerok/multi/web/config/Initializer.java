package daggerok.multi.web.config;

import daggerok.multi.data.user.User;
import daggerok.multi.data.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Initializer {
    @Bean
    public CommandLineRunner testData(UserRepository userRepository) {
        return args -> Arrays.asList("max,dag,bax".split(","))
                .forEach(name -> userRepository.save(User.of(name, name)));
    }
}
