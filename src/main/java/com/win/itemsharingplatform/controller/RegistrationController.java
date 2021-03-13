package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.request.RegistrationRequest;
import com.win.itemsharingplatform.model.response.ConfirmResponse;
import com.win.itemsharingplatform.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/registration")
@AllArgsConstructor
public class RegistrationController {

    final private RegistrationService registrationService;
    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public ConfirmResponse confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
