package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.model.Chat;
import com.win.itemsharingplatform.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    ChatService(ChatRepository chatRepository) {this.chatRepository = chatRepository;}

    public Chat findChatByRequestId(Long requestId){return chatRepository.findChatByRequestId(requestId);}

    public Chat createChat(Chat chat){ return chatRepository.save(chat);}
}
