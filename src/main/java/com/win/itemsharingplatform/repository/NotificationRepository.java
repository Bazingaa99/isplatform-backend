package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<UserNotification, Long> {

    @Query(value = "SELECT COUNT(id) FROM user_notification WHERE receiver_id = :receiverId AND is_seen = false", nativeQuery = true)
    Long getNotificationsCountByReceiverId(Long receiverId);

    List<UserNotification> getNotificationsByReceiverId(Long userId);

    UserNotification getNotificationById(Long id);
}
