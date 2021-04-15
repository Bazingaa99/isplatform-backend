package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.UserHasGroups;
import com.win.itemsharingplatform.model.UsersGroup;
import com.win.itemsharingplatform.repository.UserHasGroupsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserHasGroupsService {
    private final UserHasGroupsRepository userHasGroupsRepository;

    public Boolean findByGroupAndUser(Long usersGroupId, Long userId){
        return userHasGroupsRepository.findByGroupIdAndUserId(usersGroupId,userId).isPresent();
    }
    public void saveUserHasGroups(UserHasGroups userHasGroups){
        userHasGroupsRepository.save(userHasGroups);
    }
}
