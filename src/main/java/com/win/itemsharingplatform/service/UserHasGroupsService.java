package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.UserHasGroups;
import com.win.itemsharingplatform.model.UsersGroup;
import com.win.itemsharingplatform.repository.UserHasGroupsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserHasGroupsService {
    private final UserHasGroupsRepository userHasGroupsRepository;

    public Boolean findByGroupAndUser(Long usersGroupId, Long userId){
        return userHasGroupsRepository.findByGroupIdAndUserId(usersGroupId,userId).isPresent();
    }

    public List<UserHasGroups> findGroupsByUser(User user) {
        return userHasGroupsRepository.findGroupsByUser(user);
    }

    public void saveUserHasGroups(UserHasGroups userHasGroups){
        userHasGroupsRepository.save(userHasGroups);
    }
}
