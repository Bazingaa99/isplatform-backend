package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    void deleteItemById(Long id);

    Optional<Item> findItemById(Long id);

    List<Item> findItemsByGroupId(Long groupId);
}
