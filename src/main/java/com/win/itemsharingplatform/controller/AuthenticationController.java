package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.request.AuthenticationRequest;
import com.win.itemsharingplatform.model.response.AuthenticationResponse;
import com.win.itemsharingplatform.repository.UserRepository;
import com.win.itemsharingplatform.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth/")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository users;
    private final MessageSource messageSource;


    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest data) {
        try {
            String email = data.getEmail();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.createToken(email);
            AuthenticationResponse response = new AuthenticationResponse(email, token);
            return ok(response);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(messageSource.getMessage("authController.invalidCredentials", null, null));
        }
    }
}
