package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.UsersGroup;
import com.win.itemsharingplatform.model.request.UsersGroupRequest;
import com.win.itemsharingplatform.service.UserService;
import com.win.itemsharingplatform.service.UsersGroupService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usersgroup")
public class UsersGroupController {
    private final UsersGroupService usersGroupService;
    private final UserService userService;

    public UsersGroupController(UsersGroupService usersGroupService, UserService userService) {
        this.usersGroupService = usersGroupService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsersGroup>> getAllUsersGroups () {
        List<UsersGroup> usersGroups = usersGroupService.findAllUsersGroups();
        return new ResponseEntity<>(usersGroups, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UsersGroup> getUserGroupById (@PathVariable("id") Long id) {
        UsersGroup usersGroup = usersGroupService.findUsersGroupById(id);
        return new ResponseEntity<>(usersGroup, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UsersGroup> createUsersGroup(@Valid @RequestBody UsersGroupRequest usersGroupRequest) {
        Long userId = userService.getUserByEmail(usersGroupRequest.getEmail()).getId();
        usersGroupRequest.getUsersGroup().setAdmin_id(userId);
        UsersGroup newUsersGroup = usersGroupService.createUsersGroup(usersGroupRequest.getUsersGroup());
        return new ResponseEntity<>(newUsersGroup, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<UsersGroup> usersGroup(@RequestBody UsersGroup usersGroup) {
        UsersGroup updateUsersGroup = usersGroupService.updateUsersGroup(usersGroup);
        return new ResponseEntity<>(updateUsersGroup, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsersGroup(@PathVariable("id") Long id) {
        usersGroupService.deleteUsersGroup(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/user/groups/{email}")
    public ResponseEntity<List<UsersGroup>> getUserGroups(@PathVariable("email") String email){
        Long userId = userService.getUserByEmail(email).getId();
        List<UsersGroup> groups = usersGroupService.findUsersGroupsByAdminId(userId);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }


}
