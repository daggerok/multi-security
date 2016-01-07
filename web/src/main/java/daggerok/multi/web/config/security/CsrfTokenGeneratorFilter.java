package daggerok.multi.web.config.security;

import lombok.NoArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * setting _csrf token into http response.
 * should be configured in our security config.
 *
 * it occurs after spring CsrfFilter, that is why it appears in request, and we can do:
 *
 * <code>request.getAttribute("_csrf")</code>
 */
@Component
@NoArgsConstructor
public class CsrfTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        // include csrf token in header name
        response.setHeader("X-CSRF-HEADER", csrfToken.getHeaderName());
        // include csrf token in parameter name
        response.setHeader("X-CSRF-PARAM", csrfToken.getParameterName());
        // this value of the token will be included as either a header or an HTTP parameter
        response.setHeader("X-CSRF-TOKEN", csrfToken.getToken());

        filterChain.doFilter(request, response);
    }
}