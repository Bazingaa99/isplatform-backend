package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.Chat;
import com.win.itemsharingplatform.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Transactional
    void deleteByRequestId(Long requestId);

    Chat findChatByRequestId(Long requestId);

    Chat findChatById(Long chatId);
}
