package com.simplon.rhp.controllers;

import com.simplon.rhp.Exeptions.UsernameExistException;
import com.simplon.rhp.entities.User;
import com.simplon.rhp.pojo.Response;
import com.simplon.rhp.repositories.UserRepository;
import com.simplon.rhp.security.JwtService;
import com.simplon.rhp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    private final JwtService jwtService;

    @PostMapping("register")
    public ResponseEntity<Response> register(@RequestBody User user) throws UsernameExistException {
        User user1 = userService.save(user);
        if (user1 == null) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message("User not registered")
                    .data(null)
                    .reason("User not registered")
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }else {
            return ResponseEntity.ok().body(Response.builder()
                    .message("User registered")
                    .data(
                            Map.of("user", user,"token", jwtService.generateToken(user))
                    )
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .build());
        }
    }

    @GetMapping("login")
    public String test() {
        return "Route is public";
    }
}
