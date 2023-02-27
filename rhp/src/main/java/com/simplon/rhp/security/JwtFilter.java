package com.simplon.rhp.security;

import com.simplon.rhp.services.MyUserDetailsService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import static io.jsonwebtoken.lang.Strings.hasText;
@Component
@RequiredArgsConstructor
public class JwtFilter  extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";

    private String token;
    private final JwtProvider jwtProvider;

    private final MyUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")){
            token = bearer.substring(7);
        }else {
            filterChain.doFilter(request, response);
        }

        if (token != null && SecurityContextHolder.getContext().getAuthentication() != null){

        }


    }



}
