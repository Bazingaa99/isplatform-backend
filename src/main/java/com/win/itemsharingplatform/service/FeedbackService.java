package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.exception.UsersGroupNotFoundException;
import com.win.itemsharingplatform.model.Feedback;
import com.win.itemsharingplatform.model.UsersGroup;
import com.win.itemsharingplatform.repository.FeedbackRepository;
import com.win.itemsharingplatform.repository.UserHasGroupsRepository;
import com.win.itemsharingplatform.repository.UserRepository;
import com.win.itemsharingplatform.repository.UsersGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Long getFeedbacksCountByUserId(Long userId) {
        return feedbackRepository.findFeedbacksCountByUserId(userId);
    }

    public List<Feedback> getFeedbacksByUserId(Long userId) {
        return feedbackRepository.findFeedbackByUserId(userId);
    }
}
