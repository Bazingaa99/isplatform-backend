package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.Item;
import com.win.itemsharingplatform.model.Request;
import com.win.itemsharingplatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Transactional
    void deleteRequestById(Long id);
    Optional<Request> findRequestById(Long requestId);
    Optional<Request> findItemById(Long id);

    List<Request> findRequestsByRequesterIdAndAccepted(Long requesterId, boolean accepted);

    @Query(value = " SELECT request.*" +
                   " FROM request join item on item.id = request.item_id " +
                   " WHERE item.owner_id = :ownerId AND request.accepted = :accepted AND request.responded = :responded", nativeQuery = true)
    List<Request> findRequestsByUserIdAndAcceptedAndResponded(Long ownerId, boolean accepted, boolean responded);

    Boolean existsRequestByItemIdAndRequesterId(Long itemId, Long requesterId);

    List<Request> findRequestsByRequesterIdAndResponded(Long userId, Boolean responded);

    @Query(value = " SELECT request.*" +
            " FROM request join item on item.id = request.item_id " +
            " WHERE item.owner_id = :ownerId AND request.responded = :responded", nativeQuery = true)
    List<Request> findRequestsByUserIdAndResponded(Long ownerId, Boolean responded);

    Request findRequestByItemIdAndRequesterId(Long itemId, Long requesterId);

    @Transactional
    @Modifying
    @Query("UPDATE Request c " +
            "SET c.responded = ?2 " +
            "WHERE c.id=?1 ")
    void updateResponseStatus(Long requestId,
                                Boolean responded);

    @Transactional
    @Modifying
    @Query("UPDATE Request c " +
            "SET c.accepted = ?2 " +
            "WHERE c.id=?1 ")
    void updateAcceptanceStatus(Long requestId,
                                Boolean isAccepted);
}
