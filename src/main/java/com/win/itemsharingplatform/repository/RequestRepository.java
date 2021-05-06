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

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM request " +
            "WHERE item_id = :itemId", nativeQuery = true)
    void deleteByItemId(Long itemId);

    @Transactional
    void deleteRequestsByItemIdAndAcceptedIsFalseAndReturnedIsFalse(Long id);

    Request findRequestById(Long requestId);

    Optional<Request> findItemById(Long id);

    List<Request> findRequestsByRequesterIdAndAcceptedAndReturned(Long requesterId, boolean accepted, boolean returned);

    @Query(value = " SELECT request.*" +
                   " FROM request join item on item.id = request.item_id " +
                   " WHERE item.owner_id = :ownerId AND request.accepted = :accepted AND request.responded = :responded AND request.returned = :returned", nativeQuery = true)
    List<Request> findRequestsByUserIdAndAcceptedAndRespondedAndReturned(Long ownerId, boolean accepted, boolean responded, boolean returned);

    Boolean existsRequestByItemIdAndRequesterId(Long itemId, Long requesterId);

    List<Request> findRequestsByRequesterIdAndRespondedAndReturned(Long userId, Boolean responded, Boolean returned);

    @Query(value = " SELECT request.*" +
            " FROM request join item on item.id = request.item_id " +
            " WHERE item.owner_id = :ownerId AND request.responded = :responded AND request.returned =:returned", nativeQuery = true)
    List<Request> findRequestsByUserIdAndRespondedAndReturned(Long ownerId, Boolean responded, Boolean returned);

    Request findRequestByItemIdAndRequesterId(Long itemId, Long requesterId);

    @Transactional
    @Modifying
    @Query("UPDATE Request r " +
            "SET r.responded = true, r.accepted = false " +
            "WHERE r.item.id = ?1 AND r.requester.id <> ?2")
    void declineRequestsByItemIdAndNotRequesterId(Long itemId, Long requesterId);

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
            "SET c.accepted = ?2, c.shareDate=?3 " +
            "WHERE c.id=?1 ")
    void updateAcceptanceStatus(Long requestId,
                                Boolean isAccepted, LocalDateTime localDateTime);
}
