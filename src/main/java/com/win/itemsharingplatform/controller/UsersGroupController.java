package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.GroupToken;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.UserHasGroups;
import com.win.itemsharingplatform.model.UsersGroup;
import com.win.itemsharingplatform.model.request.AddToGroupRequest;
import com.win.itemsharingplatform.model.request.UsersGroupRequest;
import com.win.itemsharingplatform.model.response.AddToGroupResponse;
import com.win.itemsharingplatform.model.response.GroupTokenResponse;
import com.win.itemsharingplatform.service.GroupTokenService;
import com.win.itemsharingplatform.service.UserHasGroupsService;
import com.win.itemsharingplatform.service.UserService;
import com.win.itemsharingplatform.service.UsersGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usersgroup")
public class UsersGroupController {
    private final UsersGroupService usersGroupService;
    private final UserService userService;
    private final GroupTokenService groupTokenService;
    private final UserHasGroupsService userHasGroupsService;

    public UsersGroupController(UsersGroupService usersGroupService, UserService userService, GroupTokenService groupTokenService, UserHasGroupsService userHasGroupsService) {
        this.usersGroupService = usersGroupService;
        this.userService = userService;
        this.groupTokenService = groupTokenService;
        this.userHasGroupsService = userHasGroupsService;
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
        User user = userService.getUserByEmail(usersGroupRequest.getEmail());
        usersGroupRequest.getUsersGroup().setAdmin_id(user.getId());
        UsersGroup newUsersGroup = usersGroupService.createUsersGroup(usersGroupRequest.getUsersGroup());
        UserHasGroups newUserHasGroups = new UserHasGroups(user, newUsersGroup);
        userHasGroupsService.saveUserHasGroups(newUserHasGroups);
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
        User user = userService.getUserByEmail(email);
        List<UserHasGroups> userHasGroups = userHasGroupsService.findGroupsByUser(user);
        List<UsersGroup> groups = new ArrayList<>();
        for(int i=0; i<userHasGroups.size(); i++){
            groups.add(userHasGroups.get(i).getGroup());
        }
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/generate-token/{groupId}&{email}")
    public GroupTokenResponse getLink(@PathVariable("groupId") Long groupId, @PathVariable("email") String email){
        Long userId = userService.getUserByEmail(email).getId();

        UsersGroup group = usersGroupService.findGroupByGroupId(groupId).get();
        String token = groupTokenService.generateToken(group);
        return new GroupTokenResponse(token);
    }
    @PostMapping("/add-to-group")
    public AddToGroupResponse addToTheGroup(@RequestBody AddToGroupRequest addToGroupRequest){

        GroupToken groupToken = groupTokenService.findByToken(addToGroupRequest.getToken());
        LocalDateTime expiredAt = groupToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())){
            return new AddToGroupResponse("Your invitation has expired");
        }
        User user= userService.getUserByEmail(addToGroupRequest.getEmail());

        if(!userHasGroupsService.findByGroupAndUser(groupToken.getUsersGroup().getId(),user.getId())){
            UserHasGroups userHasGroups = new UserHasGroups(user,groupToken.getUsersGroup());
            System.out.println(userHasGroups.toString());
            userHasGroupsService.saveUserHasGroups(userHasGroups);
            return new AddToGroupResponse("You have successfully added to the group");
        }else{
            return new AddToGroupResponse("You are already in this group");
        }


    }

}
