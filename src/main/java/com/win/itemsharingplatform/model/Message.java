package com.win.itemsharingplatform.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference
    @JoinColumn(name="chat_id", referencedColumnName = "id")
    @NotNull(message = "Provide a chat id.")
    private Chat chat;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @Column(name = "text")
    @NotBlank(message = "Message is empty.")
    @Size(min=1, max=250, message = "Allowed character amount is from 1 to 250 characters.")
    private String text;

    @Column(name = "date_sent")
    @DateTimeFormat
    private final LocalDateTime dateSent = LocalDateTime.now();
}
