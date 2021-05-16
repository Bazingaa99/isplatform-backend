package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemById(Long id);

    List<Item> findItemsByAvailableIsTrueAndGroupId(Long groupId);

    Item findItemByIdAndOwnerId(Long itemId, Long userId);

    @Transactional
    @Modifying
    @Query(" UPDATE Item" +
           " SET bookmarkCount = bookmarkCount + ?2" +
           " WHERE id = ?1")
    void updateItemBookmarkCount(Long itemId, int value);

    @Query(value = " SELECT item.* FROM item LEFT JOIN user_has_bookmarks uhb on item.id = uhb.item_id" +
                   " WHERE uhb.user_id = :userId AND item.is_available = true", nativeQuery = true)
//    @Query(value = " SELECT item.* FROM item, user_has_bookmarks" +
//                    " WHERE item.id = user_has_bookmarks.item_id AND user_has_bookmarks.user_id = :userId", nativeQuery = true)
    List<Item> findItemsBookmarkedByUser(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Item i " +
            "SET i.available = ?2 " +
            "WHERE i.id=?1 ")
    void updateAvailableStatus(Long itemId, boolean available);


}
