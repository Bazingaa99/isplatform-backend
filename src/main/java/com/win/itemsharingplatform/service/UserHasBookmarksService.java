package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.model.UserHasBookmarks;
import com.win.itemsharingplatform.repository.UserHasBookmarksRepository;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class UserHasBookmarksService {
    private final UserHasBookmarksRepository userHasBookmarksRepository;

    public List<UserHasBookmarks> findUserHasBookmarksByUserId(Long userId){
        return userHasBookmarksRepository.findUserHasBookmarksByUserId(userId);
    }

    public UserHasBookmarks addUserHasBookmarks(UserHasBookmarks userHasBookmarks){
        return userHasBookmarksRepository.save(userHasBookmarks);
    }

    public void deleteUserHasBookmarks(UserHasBookmarks userHasBookmarks){
        userHasBookmarksRepository.delete(userHasBookmarks);
    }

    public UserHasBookmarks findUserHasBookmarksByUserIdAndItemId(Long userId, Long itemId){
        return userHasBookmarksRepository.findUserHasBookmarksByUserIdAndItemId(userId, itemId);
    }

    public Boolean existsUserHasBookmarksByUserIdAndItemId(Long userId, Long itemId){
        return userHasBookmarksRepository.existsByUserIdAndItemId(userId, itemId);
    }

    public void deleteUserHasBookmarksByItemId(Long itemId){
        userHasBookmarksRepository.deleteByItemId(itemId);
    }
}
