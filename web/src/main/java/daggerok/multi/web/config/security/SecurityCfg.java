package daggerok.multi.web.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityCfg extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .headers()
                .frameOptions()
                .sameOrigin()
                .and()
            .csrf() // csrf token
                .ignoringAntMatchers("/login") // allow curl -XPOST localhost:8080/login -d 'username&password'
                .ignoringAntMatchers("/logout") // allow curl -XPOST localhost:8080/logout
                .ignoringAntMatchers("/browser/**") // HAL browser
                .ignoringAntMatchers("/console/**") // h2 console
                .ignoringAntMatchers("/*.css")
                .and()
            .authorizeRequests()
                .antMatchers("/browser/**").permitAll()
                .antMatchers("/console/**").permitAll()
                .antMatchers("/*.css").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl);
    }
}