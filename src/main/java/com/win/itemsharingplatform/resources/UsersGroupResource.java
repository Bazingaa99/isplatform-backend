package com.win.itemsharingplatform.resources;

import com.win.itemsharingplatform.model.UsersGroup;
import com.win.itemsharingplatform.service.UsersGroupService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usersgroup")
public class UsersGroupResource {
    private final UsersGroupService usersGroupService;

    public UsersGroupResource(UsersGroupService usersGroupService) {
        this.usersGroupService = usersGroupService;
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
    public ResponseEntity<UsersGroup> creaeteUsersGroup(@RequestBody UsersGroup usersGroup) {
        UsersGroup newUsersGroup = usersGroupService.createUsersGroup(usersGroup);
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

    @GetMapping("/find/user/groups/{userId}")
    public ResponseEntity<List<UsersGroup>> usersGroup(@PathVariable("userId") Long userId){
        List<UsersGroup> groups = usersGroupService.findUsersGroupsByUserId(userId);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }


}
