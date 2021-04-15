package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.model.GroupToken;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.UsersGroup;
import com.win.itemsharingplatform.repository.GroupTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GroupTokenService {
    private GroupTokenRepository groupTokenRepository;
    @Autowired
    public GroupTokenService(GroupTokenRepository groupTokenRepository) {
        this.groupTokenRepository = groupTokenRepository;
    }

    public String generateToken(UsersGroup group){
        String token = UUID.randomUUID().toString().substring(0,6);
        GroupToken groupToken = new GroupToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusWeeks(1),
                group);
        groupTokenRepository.save(groupToken);
        return token;
    }


    public GroupToken getGroupTokenByToken(String token){
        return groupTokenRepository.findByToken(token).get();
    }

    public GroupToken findByToken(String token){
        return groupTokenRepository.findByToken(token).get();
    }

}
