package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.UserNotification;
import com.win.itemsharingplatform.model.request.SetUserNotificationSeenRequest;
import com.win.itemsharingplatform.model.request.UserNotificationRequest;
import com.win.itemsharingplatform.service.NotificationService;
import com.win.itemsharingplatform.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notifiationService;
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserNotification>> getUserNotifications (@PathVariable("userId") Long userId) {
        List<UserNotification> userNotification = notifiationService.getUserNotificationsByReceiverId(userId);
        return new ResponseEntity<>(userNotification, HttpStatus.OK);
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> getUserNotificationsCount (@PathVariable("userId") Long userId) {
        Long numberOfNotifications = notifiationService.getNotificationsCountByReceiverId(userId);
        return new ResponseEntity<>(numberOfNotifications, HttpStatus.OK);
    }

    @PostMapping("/create/")
    public void createNotification(@Valid @RequestBody UserNotificationRequest notificationRequest) {
        UserNotification newUserNotification = new UserNotification();
        newUserNotification.setNotificationMessage(notificationRequest.getUserNotificationMessage());
        newUserNotification.setReceiver(userService.getUserById(notificationRequest.getReceiverId()));
        newUserNotification.setWriter(userService.getUserById(notificationRequest.getWriterId()));
        newUserNotification.setSeen(false);
        notifiationService.createNotification(newUserNotification);
    }

    @PutMapping("/set/seen/")
    public void updateUsersGroup(@Valid @RequestBody SetUserNotificationSeenRequest setUserNotificationSeenRequest) {
        System.out.println("works");
        System.out.println("works");
        System.out.println("works");
        UserNotification notification = notifiationService.getNotificationById(setUserNotificationSeenRequest.getNotificationId());
        System.out.println(notification);
        System.out.println(notification);
        System.out.println(notification);
        notification.setSeen(true);
        notifiationService.updateNotification(notification);
    }
}
