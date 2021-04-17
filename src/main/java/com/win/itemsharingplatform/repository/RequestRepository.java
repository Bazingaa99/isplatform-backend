package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.Item;
import com.win.itemsharingplatform.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    void deleteItemById(Long id);

    Optional<Request> findItemById(Long id);

    List<Request> findRequestsByRequesterIdAndAccepted(Long requesterId, boolean accepted);

    @Query(value = " SELECT request.*" +
                   " FROM request join item on item.id = request.item_id " +
                   " WHERE item.owner_id = :ownerId AND request.accepted = :accepted", nativeQuery = true)
    List<Request> findRequestsByUserIdAndAccepted(Long ownerId, boolean accepted);
}
