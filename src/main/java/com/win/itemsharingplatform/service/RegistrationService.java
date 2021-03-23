package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.exception.UserEmailExistsException;
import com.win.itemsharingplatform.exception.UserException;
import com.win.itemsharingplatform.model.ConfirmationToken;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.request.RegistrationRequest;
import com.win.itemsharingplatform.model.response.ConfirmResponse;
import com.win.itemsharingplatform.repository.EmailSender;
import com.win.itemsharingplatform.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final UserRepository userRepository;

    public void register(RegistrationRequest request) throws UserException {

        boolean userExists = userRepository.findByEmail(request.getEmail())
                .isPresent();

        if (userExists) {
            throw new UserEmailExistsException(request.getEmail());
        } else {
            User user = new User(request.getUsername(),
                    request.getEmail(),
                    request.getPassword());
            String token = userService.signUpUser(user);
            emailSender.generateLinkAndSend(user,token);
        }
    }

    public ConfirmResponse confirmToken(String token) {

        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            return new ConfirmResponse("Email is already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            return new ConfirmResponse("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableAppUser(
                confirmationToken.getUser().getEmail());
        return new ConfirmResponse("Email is confirmed");
    }


}
