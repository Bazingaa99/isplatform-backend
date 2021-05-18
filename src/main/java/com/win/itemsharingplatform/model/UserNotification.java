package com.win.itemsharingplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer;

    @Column(name = "notification_text")
    @NotBlank
    private String notificationMessage;

    @Column(name = "is_seen", nullable = false)
    private Boolean seen;

    public UserNotification(User receiver, User writer, String notificationMessage, Boolean seen) {
        this.receiver = receiver;
        this.writer = writer;
        this.notificationMessage = notificationMessage;
        this.seen = seen;
    }

    public UserNotification() {

    }
}
