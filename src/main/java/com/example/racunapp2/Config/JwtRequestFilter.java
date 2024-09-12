package com.example.racunapp2.Config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/clients/register".equals(path)|| "/clients/login".equals(path) || "/clients/check".equals(path)) {
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");

        LoggedInUser user = null;
        String token;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            try {
                user = JwtUtil.extractUser(token);
            } catch (ExpiredJwtException e) {
                // Handle expired token
            }
            // TODO catch
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            if (user != null) {
                var auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                response.setStatus(403);
            }
        }

        chain.doFilter(request, response);
    }
}
