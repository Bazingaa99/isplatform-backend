package com.win.itemsharingplatform.controller;


import com.win.itemsharingplatform.exception.FeedbackForYourselfException;
import com.win.itemsharingplatform.model.Feedback;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.win.itemsharingplatform.model.request.FeedbackRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final UserService userService;

    public FeedbackController(FeedbackService feedbackService, UserService userService) {
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest) {
        Long writerId = userService.getUserByEmail(feedbackRequest.getEmail()).getId();
        if(!writerId.equals(feedbackRequest.getReceiverId())) {
            feedbackRequest.getFeedback().setWriter(new User(writerId));
            feedbackRequest.getFeedback().setUser(new User(feedbackRequest.getReceiverId()));

            Feedback newFeedback = feedbackService.createFeedback(feedbackRequest.getFeedback());
            return new ResponseEntity<>(newFeedback, HttpStatus.CREATED);
        } else throw new FeedbackForYourselfException("You can not leave feedback for yourself.");
    }

    @GetMapping("/get/feedbacks/count/{userId}")
    public ResponseEntity<Long> getFeedbacksCountByUserId (@PathVariable("userId") Long userId) {
        Long feedbacksCount = feedbackService.getFeedbacksCountByUserId(userId);
        return new ResponseEntity<>(feedbacksCount, HttpStatus.OK);
    }

    @GetMapping("/get/user/feedbacks/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbacks(@PathVariable("userId") Long userId){
        List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }
}
