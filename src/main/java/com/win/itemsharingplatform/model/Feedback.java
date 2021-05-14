package com.win.itemsharingplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "writer_id", nullable = false)
    private User writer;

    @Column(name = "feedback_text")
    @Size(max = 100, message = "Please provide a shorter feedback text.")
    private String feedbackMessage;

    @Column(name = "stars_count", nullable = false)
    @NotNull(message = "Please select rating.")
    @Min(value = 1, message="Minimum rating is 1 star.")
    @Max(value = 5, message = "Maximum rating is 1 star.")
    private Long starsCount;

    public Feedback(User user, User writer, String feedbackMessage, Long starsCount) {
        this.user = user;
        this.writer = writer;
        this.feedbackMessage = feedbackMessage;
        this.starsCount = starsCount;
    }

    public Feedback() {

    }
}
