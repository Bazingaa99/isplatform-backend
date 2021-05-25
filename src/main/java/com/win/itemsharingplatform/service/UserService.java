package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.exception.EmailNotFoundException;
import com.win.itemsharingplatform.exception.UserEmailExistsException;
import com.win.itemsharingplatform.model.ConfirmationToken;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.request.ChangeUserRequest;
import com.win.itemsharingplatform.repository.UserRepository;
import com.win.itemsharingplatform.repository.UsersGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final static String EMAIL_NOT_FOUND_MSG = "User with email %email not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final UsersGroupRepository usersGroupRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(String.format(EMAIL_NOT_FOUND_MSG, email)));
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public String signUpUser(User user) {

        String encodedPassword = bCryptPasswordEncoder
                .encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return tokenGeneration(user);
    }

    public String tokenGeneration(User user) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException(String.format(EMAIL_NOT_FOUND_MSG, email)));
    }

    public Boolean findUsersByGroupIdAndUserId(long groupId, long userId) {
        return userRepository.findIfUserExistsByIdAndUserId(groupId, userId).isPresent();
    }

    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public void updateUser(ChangeUserRequest user) throws UserEmailExistsException {
        boolean userExists = userRepository.findByEmail(user.getEmail())
                .isPresent();
        if (user.getEmail().equals(user.getOldEmail())) {
            User oldUser = getUserByEmail(user.getOldEmail());
            oldUser.setEmail(user.getOldEmail());
            oldUser.setUsername(user.getUsername());
            userRepository.save(oldUser);
        }
        else if(!userExists){
            User oldUser = getUserByEmail(user.getOldEmail());
            oldUser.setEmail(user.getEmail());
            oldUser.setUsername(user.getUsername());
            userRepository.save(oldUser);

        }else {
            throw new UserEmailExistsException(user.getEmail());
        }
    }


    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
