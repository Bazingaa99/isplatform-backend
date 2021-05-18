package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDetails> getUserById (@PathVariable("id") Long id) {
        UserDetails user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/group/{group_id}")
    public ResponseEntity<List<User>> getUsersByGroupId(@PathVariable("group_id") Long groupId){
        List<User> users = userService.getUsersByGroupId(groupId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
