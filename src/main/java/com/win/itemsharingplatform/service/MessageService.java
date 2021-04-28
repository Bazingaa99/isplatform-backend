package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.model.Message;
import com.win.itemsharingplatform.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    MessageService(MessageRepository messageRepository){this.messageRepository = messageRepository;}

    public Message addMessage(Message message){
        return messageRepository.save(message);
    }
}
