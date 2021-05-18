package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.model.UserNotification;
import com.win.itemsharingplatform.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<UserNotification> getUserNotificationsByReceiverId(Long receiverId){
        return notificationRepository.getNotificationsByReceiverId(receiverId);
    }

    public UserNotification createNotification(UserNotification notification){
        return notificationRepository.save(notification);
    }

    public Long getNotificationsCountByReceiverId(Long receiverId) {
        return notificationRepository.getNotificationsCountByReceiverId(receiverId);
    }

    public UserNotification getNotificationById(Long id) {
        return notificationRepository.getNotificationById(id);
    }

    public void updateNotification(UserNotification userNotification) {
        notificationRepository.save(userNotification);
    }
}
