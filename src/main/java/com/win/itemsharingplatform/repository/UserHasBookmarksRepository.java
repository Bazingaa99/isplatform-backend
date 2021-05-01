package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.UserHasBookmarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHasBookmarksRepository extends JpaRepository<UserHasBookmarks, Long> {
    List<UserHasBookmarks> findUserHasBookmarksByUserId(Long userId);

    UserHasBookmarks findUserHasBookmarksByUserIdAndItemId(Long userId, Long itemId);

    Boolean existsByUserIdAndItemId(Long userId, Long itemId);
}
