package com.simplon.rhp.security;


import com.simplon.rhp.entities.User;
import com.simplon.rhp.services.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

    private final  JwtFilter jwtFilter;
    private final PasswordEncoder passwordEncoder;
    private final MyUserDetailsService myUserDetailsService;


    @Bean
    UserDetailsService userDetailsService() {
        // Using myUserDetailsService to get the user from the database and check the role of the user then grant the right authorities
        return username -> {
            String userEmail = username.split(":")[0];
            String role = username.split(":")[1];
            User user = myUserDetailsService.loadUserByEmail(userEmail);
            GrantedAuthority authority = new SimpleGrantedAuthority(
                    user.getRoles().stream().findFirst().get().getName()
            );
            return (UserDetails) new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singletonList(authority));

        };
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
         http
                 .csrf()
                 .disable()
                 .authorizeHttpRequests()
                 .requestMatchers("/auth/**")
                 .permitAll()
                 .anyRequest()
                 .authenticated()
                 .and()
                 .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .authenticationProvider(authenticationProvider())
                 .addFilterBefore( jwtFilter , UsernamePasswordAuthenticationFilter.class);
//                 .requestMatchers("/admin/**").hasRole("ADMIN")
//                 // HR Manager role
//                 .requestMatchers("/hrmanager/**").hasRole("HR_MANAGER")
//                 // HR Agent role
//                 .requestMatchers("/hragent/**").hasRole("HR_AGENT")
//                 // Employee role
//                 .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                 // All other requests require authentication
//                 .anyRequest().authenticated()
//                 .and()
//                 .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                 .and()
//                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                 .and()
//                 .authenticationProvider(authenticationProvider)
//                 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }




//    @Bean
//    CorsConfigurationSource corsConfigurationSource(){
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
