package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = "SELECT COUNT(id) FROM feedback WHERE user_id = :userId", nativeQuery = true)
    Long findFeedbacksCountByUserId(Long userId);

    List<Feedback> findFeedbackByUserId(Long userId);

    Feedback findFeedbackById(Long id);
}
