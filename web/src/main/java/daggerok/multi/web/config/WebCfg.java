package daggerok.multi.web.config;

import com.github.mustachejava.DeferringMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "daggerok")
public class WebCfg {
    @Bean public MustacheFactory mustacheFactory() {
        return new DeferringMustacheFactory();
    }
}
