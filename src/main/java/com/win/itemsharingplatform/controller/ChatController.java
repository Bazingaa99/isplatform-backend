package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.exception.ChatAuthorizationException;
import com.win.itemsharingplatform.exception.DoesNotExistException;
import com.win.itemsharingplatform.model.Chat;
import com.win.itemsharingplatform.model.Message;
import com.win.itemsharingplatform.model.Request;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.request.MessageRequest;
import com.win.itemsharingplatform.service.ChatService;
import com.win.itemsharingplatform.service.MessageService;
import com.win.itemsharingplatform.service.RequestService;
import com.win.itemsharingplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/isp/chat")
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;
    private final MessageService messageService;
    private final RequestService requestService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService, MessageService messageService, RequestService requestService) {
        this.chatService = chatService;
        this.userService = userService;
        this.messageService = messageService;
        this.requestService = requestService;
    }

    @PostMapping("/add")
    public ResponseEntity<Chat> createChat(@Valid @RequestBody Chat chat) {
        Chat c = chatService.findChatByRequestId(chat.getRequest().getId());
        Request r = requestService.findRequestById(chat.getRequest().getId());

        if(r != null){
            if(c == null) {
                Chat newChat = chatService.createChat(chat);
                return new ResponseEntity<>(newChat, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(c, HttpStatus.OK);
        }else{
            throw new DoesNotExistException("Can't open chat, because request does not exist.");
        }

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

        Chat chat = chatService.findChatById(messageRequest.getMessage().getChat().getId());
        Request request = requestService.findRequestById(chat.getRequest().getId());
        if(messageRequest.getMessage().getSender().getId().equals(request.getRequester().getId()) ||
           messageRequest.getMessage().getSender().getId().equals(request.getItem().getOwner().getId()))
        {
            Message newMessage = messageService.addMessage(messageRequest.getMessage());
            return new ResponseEntity<>(newMessage, HttpStatus.OK);
        }else{
            throw new ChatAuthorizationException("You have not requested this item and you are not the owner of the item.");
        }
    }

    @MessageMapping("message/chat/{id}")
    @SendTo("/chat/{id}")
    public Message sendMessage(@Valid @RequestBody @Payload Message message){
        Chat chat = chatService.findChatById(message.getChat().getId());
        Request request = requestService.findRequestById(chat.getRequest().getId());
        if(message.getSender().getId().equals(request.getRequester().getId()) ||
           message.getSender().getId().equals(request.getItem().getOwner().getId()))
        {
            return message;
        }else{
            throw new ChatAuthorizationException("You have not requested this item and you are not the owner of the item.");
        }
    }
}
