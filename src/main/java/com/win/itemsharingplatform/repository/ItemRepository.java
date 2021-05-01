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

    @Query(value = "SELECT a.* FROM item as a WHERE a.id IS NOT NULL AND EXISTS (SELECT 1 " +
                    "FROM item b left join request r on b.id = r.item_id " +
                    "WHERE a.id = b.id AND b.group_id = :groupId AND ((r.responded = false OR (r.responded = true AND r.accepted = false)) OR r.item_id is null))", nativeQuery=true)
    List<Item> findItemsByGroupIdAndNotRespondedOrDeclined(Long groupId);

    Item findItemByIdAndOwnerId(Long itemId, Long userId);

    @Transactional
    @Modifying
    @Query(" UPDATE Item" +
           " SET bookmarkCount = bookmarkCount + ?2" +
           " WHERE id = ?1")
    void updateItemBookmarkCount(Long itemId, int value);

    @Query(value = " SELECT i.* FROM item i LEFT JOIN user_has_bookmarks uhb ON i.id = uhb.item_id LEFT JOIN request r ON uhb.item_id = r.item_id" +
                   " WHERE i.is_hidden = false AND uhb.user_id = :userId AND (r.accepted = false OR r.item_id is null)" +
                   " GROUP BY i.id", nativeQuery = true)
    List<Item> findItemsBookmarkedByUser(Long userId);
}
