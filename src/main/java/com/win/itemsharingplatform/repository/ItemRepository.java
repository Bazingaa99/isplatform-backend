package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    void deleteItemById(Long id);

    Optional<Item> findItemById(Long id);

    @Query(value = "SELECT item.* " +
                    "FROM item left join request on item.id = request.item_id " +
                    "WHERE (request.responded = false OR (request.responded = true AND request.accepted = false)) OR request.item_id is null", nativeQuery=true)
    List<Item> findItemsByGroupIdAndNotRespondedOrDeclined(Long groupId);
}
