package com.simplon.rhp.security;

import com.simplon.rhp.services.MyUserDetailsService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import static io.jsonwebtoken.lang.Strings.hasText;
@Component
@RequiredArgsConstructor
public class JwtFilter  extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";

    private String token;
    private final  JwtService jwtProvider;

    private final MyUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String bearer = request.getHeader(AUTHORIZATION);
        if (bearer!=null && bearer.startsWith("Bearer ")){
            token = bearer.substring(7);
            final String userEmail = jwtProvider.extractUserName(token);
            if (token != null && SecurityContextHolder.getContext().getAuthentication() != null){
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
                if (jwtProvider.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }else {
            filterChain.doFilter(request, response);
        }

    }



}
