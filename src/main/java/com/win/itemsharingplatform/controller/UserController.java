package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.exception.InvalidOldPasswordException;
import com.win.itemsharingplatform.exception.UserEmailExistsException;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.UsersGroup;
import com.win.itemsharingplatform.model.request.ChangePasswordRequest;
import com.win.itemsharingplatform.model.request.ChangeUserRequest;
import com.win.itemsharingplatform.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("isp/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<UserDetails> getUserByEmail (@PathVariable("email") String email) {
        UserDetails user = userService.loadUserByUsername(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PutMapping("/update")
    public void updateUserInfo(@RequestBody ChangeUserRequest user) throws UserEmailExistsException {

        userService.updateUser(user);
    }

    @PutMapping("/updatePassword")
    public void updateUserPassword(@RequestBody ChangePasswordRequest user){

        User oldUser = userService.getUserByEmail(user.getEmail());
        if(!userService.checkIfValidOldPassword(oldUser,user.getOldPassword())){
            throw new InvalidOldPasswordException("Bad old password");
        };
        userService.changeUserPassword(oldUser, user.getNewPassword());
    }
}
