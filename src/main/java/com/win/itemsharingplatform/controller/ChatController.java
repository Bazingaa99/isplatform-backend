package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.Chat;
import com.win.itemsharingplatform.model.Message;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.request.MessageRequest;
import com.win.itemsharingplatform.service.ChatService;
import com.win.itemsharingplatform.service.MessageService;
import com.win.itemsharingplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/isp/chat")
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService, MessageService messageService) {
        this.chatService = chatService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping("/add")
    public ResponseEntity<Chat> createChat(@Valid @RequestBody Chat chat) {
        System.out.println(chat);
        Chat c = chatService.findChatByRequestId(chat.getRequest().getId());
        if(c == null) {
            Chat newChat = chatService.createChat(chat);
            return new ResponseEntity<>(newChat, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<Chat> getChatByRequestId(@PathVariable("id") Long requestId){
        Chat chat = chatService.findChatByRequestId(requestId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/send_message")
    public ResponseEntity<Message> addMessage(@Valid @RequestBody MessageRequest messageRequest){
        Long senderId = userService.getUserByEmail(messageRequest.getEmail()).getId();
        messageRequest.getMessage().setSender(new User(senderId));
        Message newMessage = messageService.addMessage(messageRequest.getMessage());
        return new ResponseEntity<>(newMessage, HttpStatus.OK);
    }
}
