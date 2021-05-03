package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.UserHasBookmarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserHasBookmarksRepository extends JpaRepository<UserHasBookmarks, Long> {
    List<UserHasBookmarks> findUserHasBookmarksByUserId(Long userId);

    UserHasBookmarks findUserHasBookmarksByUserIdAndItemId(Long userId, Long itemId);

    Boolean existsByUserIdAndItemId(Long userId, Long itemId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_has_bookmarks " +
            "WHERE item_id = :itemId", nativeQuery = true)
    void deleteByItemId(Long itemId);
}
