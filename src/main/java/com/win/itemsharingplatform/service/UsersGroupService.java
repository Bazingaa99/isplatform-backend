package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.exception.UsersGroupNotFoundException;
import com.win.itemsharingplatform.model.UsersGroup;
import com.win.itemsharingplatform.repository.UsersGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersGroupService {
    private final UsersGroupRepository usersGroupRepository;

    @Autowired
    public UsersGroupService(UsersGroupRepository usersGroupRepository) {
        this.usersGroupRepository = usersGroupRepository;
    }

    public UsersGroup createUsersGroup(UsersGroup usersGroup) {
        usersGroup.setGroupCode(UUID.randomUUID().toString());
        return usersGroupRepository.save(usersGroup);
    }

    public List<UsersGroup> findAllUsersGroups() {
        return usersGroupRepository.findAll();
    }

    public List<UsersGroup> findUsersGroupsByUserId(Long admin_id){
        return usersGroupRepository.findUsersGroupsByAdminId(admin_id);
    }

    public UsersGroup updateUsersGroup(UsersGroup usersGroup) {
        return usersGroupRepository.save(usersGroup);
    }

    public UsersGroup findUsersGroupById(Long id) {
        return usersGroupRepository.findUsersGroupById(id)
                .orElseThrow(() -> new UsersGroupNotFoundException("Users group by id " + id + " was not found"));
    }

    public void deleteUsersGroup(Long id){
        usersGroupRepository.deleteUsersGroupById(id);
    }
}
