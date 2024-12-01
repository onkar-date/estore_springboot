package com.example.estore.filter;

import com.example.estore.security.JwtUtil;
import com.example.estore.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        String jwtToken;
        String username = null;

        // Check for JWT in Authorization header
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        jwtToken = header.substring(7); // Extract token after "Bearer "
        try {
            // Extract username from token
            username = jwtUtil.getUsernameFromToken(jwtToken);
        } catch (ExpiredJwtException e) {
            // Respond with appropriate error if token is expired
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT token has expired");
            return;
        } catch (Exception e) {
            // Handle other parsing exceptions
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid JWT token");
            return;
        }

        // Validate token and set security context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = this.userService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, user.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                // If token is invalid
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("JWT token is invalid");
                return;
            }
        }

        chain.doFilter(request, response); // Continue the filter chain
    }

}
