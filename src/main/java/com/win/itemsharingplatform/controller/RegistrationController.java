package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.exception.UserEmailExistsException;
import com.win.itemsharingplatform.exception.UserException;
import com.win.itemsharingplatform.exception.UserNotFoundException;
import com.win.itemsharingplatform.model.request.RegistrationRequest;
import com.win.itemsharingplatform.model.response.ConfirmResponse;
import com.win.itemsharingplatform.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/registration")
@AllArgsConstructor
public class RegistrationController {

    final private RegistrationService registrationService;
    @PostMapping
    public ResponseEntity register(@Valid @RequestBody RegistrationRequest request) throws UserException {
        registrationService.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED) ;
    }

    @GetMapping(path = "confirm")
    public ConfirmResponse confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
