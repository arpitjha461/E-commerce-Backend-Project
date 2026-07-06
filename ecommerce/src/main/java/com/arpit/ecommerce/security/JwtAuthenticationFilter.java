package com.arpit.ecommerce.security;

import com.arpit.ecommerce.service.CustomUserDetailsService;
import com.arpit.ecommerce.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("===== JWT FILTER EXECUTED =====");
        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }


        String jwt = authHeader.substring(7);
        System.out.println("JWT: " + jwt);
        try {
            String email = jwtUtil.extractEmail(jwt);
            System.out.println("Extracted Email: " + email);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
//                System.out.println(userDetails.getAuthorities());
//                System.out.println("UserDetails: " + userDetails.getUsername());
//                System.out.println("Token Valid: " + jwtUtil.validateToken(jwt, userDetails.getUsername()));
                if (jwtUtil.validateToken(jwt,userDetails.getUsername())){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    System.out.println(
//                            SecurityContextHolder.getContext()
//                                    .getAuthentication()
//                                    .getAuthorities()
//                    );
                }
            }
        }
        catch (Exception e){
            System.out.println("Invalid jwt : "+ e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
}
