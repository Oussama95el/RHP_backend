package com.simplon.rhp.auth;

import com.simplon.rhp.pojo.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("register")
  public Response register(
      @RequestBody RegisterRequest request
  ) {
    Map<String, AuthenticationResponse> data = new HashMap<>();
    data.put("Data", service.register(request));

    return Response.builder()
        .timestamp(java.time.LocalDateTime.now())
        .statusCode(200)
        .status(org.springframework.http.HttpStatus.OK)
        .message("User registered successfully")
        .reason("User registered successfully")
        .developerMessage("User registered successfully")
        .data(data)
        .build();
  }
  @PostMapping("authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }


}
