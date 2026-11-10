
package com.example.BookMovies.JWT;

import com.example.BookMovies.Repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract JWT token from the header
            String jwtToken = authHeader.substring(7);
            String username = jwtService.extractUsername(jwtToken);

            // Check that a username exists and authentication hasn't already been set
            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                var userDetailsOptional = userRepository.findByUsername(username);

                // User associated with the token no longer exists
                if (userDetailsOptional.isEmpty()) {
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write(
                            "{\"message\":\"Invalid authentication token\"}"
                    );
                    return;
                }

                var userDetails = userDetailsOptional.get();

                // Validate the token
                if (!jwtService.isTokenValid(jwtToken, userDetails)) {
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write(
                            "{\"message\":\"Invalid authentication token\"}"
                    );
                    return;
                }

                // Create authentication with user roles
                List<SimpleGrantedAuthority> authorities =
                        userDetails.getRoles().stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList();

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, authorities
                        );

                // Set authentication details
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Update the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (JwtException | IllegalArgumentException e) {
            // Handles expired, malformed, and incorrectly signed JWTs
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"message\":\"Invalid or expired authentication token\"}"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}
