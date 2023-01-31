package pl.kamil.praca.authentication.security.JWT;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.kamil.praca.authentication.model.Token;
import pl.kamil.praca.authentication.repository.TokenRepository;
import pl.kamil.praca.authentication.service.UserService;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter implements Filter {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final TokenRepository tokenRepository;

    public JWTFilter(UserService userService, JWTUtil jwtUtil, TokenRepository tokenRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = jwtUtil.validToken(token);
                String username = decodedJWT.getSubject();

                final Optional<Token> userToken = tokenRepository.findTokenByTokenAndUsername(token, username);
                if (userToken.isPresent()) {
                    UserDetails userDetails = userService.getUserDetails(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } else {
                    log.info("Error, invalid token");
                    response.setHeader("error", "Invalid token");
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("Error_message", "Invalid token");
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), errorMap);
                    this.tokenRepository.deleteTokenByToken(token);
                }
            } catch(Exception e) {
                 log.info("Error", e.getMessage());
                 response.setHeader("error", e.getMessage());
                 response.setStatus(FORBIDDEN.value());
                 Map<String, String> errorMap = new HashMap<>();
                 errorMap.put("error_message", e.getMessage());
                 response.setContentType(APPLICATION_JSON_VALUE);
                 new ObjectMapper().writeValue(response.getOutputStream(), errorMap );
            }
        } else {
                filterChain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) throws javax.servlet.ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, javax.servlet.FilterChain filterChain) throws IOException, javax.servlet.ServletException {

    }
}