package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.request.AuthenticationRequest;
import com.win.itemsharingplatform.model.response.AuthenticationResponse;
import com.win.itemsharingplatform.repository.EmailSender;
import com.win.itemsharingplatform.repository.UserRepository;
import com.win.itemsharingplatform.security.JwtTokenProvider;
import com.win.itemsharingplatform.service.RegistrationService;
import com.win.itemsharingplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth/")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository users;
    private final MessageSource messageSource;
    private final  UserRepository userRepository;
    private final UserService userService;
    private final EmailSender emailSender;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository users, MessageSource messageSource, UserRepository userRepository, UserService userService, EmailSender emailSender) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.users = users;
        this.messageSource = messageSource;
        this.userRepository = userRepository;
        this.userService = userService;
        this.emailSender = emailSender;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest data) {
        try {

            String email = data.getEmail();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.createToken(email,this.users.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("authController.invalidCredentials", null, null)))
                    .getRoles());
            System.out.println(token);
            AuthenticationResponse response = new AuthenticationResponse(email, token);
            return ok(response);
        }catch (DisabledException ex){
            User user = userRepository.findByEmail(data.getEmail()).get();
            String token = userService.tokenGeneration(userRepository.findByEmail(data.getEmail()).get());
            emailSender.generateLinkAndSend(user,token);
            throw new BadCredentialsException(messageSource.getMessage("authController.notConfirmedEmail", null, null));
        }
        catch (AuthenticationException ex) {
            throw new BadCredentialsException(messageSource.getMessage("authController.invalidCredentials", null, null));}
    }
}
