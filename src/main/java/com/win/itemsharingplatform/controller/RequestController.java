package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.exception.RequesterIsOwnerException;
import com.win.itemsharingplatform.model.Item;
import com.win.itemsharingplatform.model.Request;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.request.ItemRequestRequest;
import com.win.itemsharingplatform.model.request.ItemReturnedRequest;
import com.win.itemsharingplatform.model.request.ResponseToRequest;
import com.win.itemsharingplatform.service.ChatService;
import com.win.itemsharingplatform.service.ItemService;
import com.win.itemsharingplatform.service.RequestService;
import com.win.itemsharingplatform.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/isp/request/")
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;
    private final ItemService itemService;
    private final ChatService chatService;

    @GetMapping("/for/{email}&{isAccepted}&{isResponded}&{returned}")
    public ResponseEntity<List<Request>> getRequestsByOwnerEmail(@PathVariable("email") String email,
                                                                 @PathVariable("isAccepted") boolean isAccepted,
                                                                 @PathVariable("isResponded") boolean isResponded,
                                                                 @PathVariable("returned") boolean isReturned) {
        Long ownerId = userService.getUserByEmail(email).getId();
        List<Request> requests = requestService.findRequestsByOwnerIdAndAcceptedAndResponded(ownerId, isAccepted, isResponded, isReturned);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/by/{email}&{isAccepted}&{returned}")
    public ResponseEntity<List<Request>> getRequestsByRequesterEmail(@PathVariable("email") String email, @PathVariable("isAccepted") boolean isAccepted, @PathVariable("returned") boolean isReturned) {
        Long requesterId = userService.getUserByEmail(email).getId();
        List<Request> requests = requestService.findRequestsByRequesterIdAndAccepted(requesterId, isAccepted, isReturned);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/notRespondedByOwner/{email}&{isReturned}")
    public ResponseEntity<List<Request>> getNotRespondedRequestsByOwnerEmail(@PathVariable("email") String email, @PathVariable("isReturned") boolean isReturned) {
        Long ownerId = userService.getUserByEmail(email).getId();
        List<Request> requests = requestService.findRequestsByOwnerIdAndResponded(ownerId, false, isReturned);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/notRespondedByRequester/{email}&{isReturned}")
    public ResponseEntity<List<Request>> getNotRespondedRequestsByRequesterEmail(@PathVariable("email") String email, @PathVariable("isReturned") boolean isReturned) {
        Long requesterId = userService.getUserByEmail(email).getId();
        List<Request> requests = requestService.findRequestsByRequesterIdAndResponded(requesterId, false, isReturned);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Request> addRequest(@RequestBody ItemRequestRequest itemRequestRequest) {
        Long userId = userService.getUserByEmail(itemRequestRequest.getEmail()).getId();
        if (itemService.findItemByIdAndOwnerId(itemRequestRequest.getRequest().getItem().getId(), userId) == null) {
            itemRequestRequest.getRequest().setRequester(new User(userId));
            Request newRequest = requestService.addRequest(itemRequestRequest.getRequest());
            return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
        } else {
            throw new RequesterIsOwnerException("Requester is owner.");
        }
    }

    @GetMapping("exists/{itemId}&{email}")
    public Request findRequestByItemIdAndRequesterIdAndReturnedIsFalse(@PathVariable("itemId") Long itemId, @PathVariable("email") String email) {
        Long requesterId = userService.getUserByEmail(email).getId();
        return requestService.findRequestByItemIdAndRequesterIdAndReturnedIsFalse(itemId, requesterId);
    }

    @DeleteMapping("delete/{requestId}")
    public void deleteRequest(@PathVariable("requestId") Long requestId) {
        requestService.deleteRequest(requestId);
    }

    @PutMapping("update-acceptance")
    public void updateAcceptance(@RequestBody ResponseToRequest responseToRequest) {
        Request request = requestService.findRequestById(responseToRequest.getRequestId());
        requestService.updateAcceptanceStatus(responseToRequest.getRequestId(),responseToRequest.getIsAccepted());
        if(responseToRequest.getIsAccepted()){
            requestService.deleteRequestsByItemIdAndAcceptedIsFalseAndReturnedIsFalse(request.getItem().getId());
            itemService.updateAvailableStatus(request.getItem().getId(), false);
        }
    }

    @PutMapping("item-returned")
    public void itemReturned(@RequestBody ItemReturnedRequest itemReturnedRequest) {
        Request request = requestService.findRequestById(itemReturnedRequest.getRequestId());
        if (itemService.findItemById(request.getItem().getId()).getOwner() == userService.getUserByEmail(itemReturnedRequest.getEmail())) {
            request.setReturned(true);
            request.setReturnDate(LocalDateTime.now());
            requestService.save(request);
        }
    }

    @PutMapping("item-relist")
    public void relistItem(@RequestBody ItemReturnedRequest itemReturnedRequest) {
        Request request = requestService.findRequestById(itemReturnedRequest.getRequestId());
        if (itemService.findItemById(request.getItem().getId()).getOwner() == userService.getUserByEmail(itemReturnedRequest.getEmail())) {
            Item item = itemService.findItemById(request.getItem().getId());
            item.setAvailable(true);
            item.setRelistCount(item.getRelistCount()+1);
            itemService.updateItem(item);
        }
    }
    @GetMapping("checkIfItemIsReturned/{itemId}")
    public ResponseEntity<Boolean> checkIfItemIsReturned(@PathVariable("itemId") Long id){
        Boolean itemIsReturned = requestService.checkIfItemIsReturned(itemService.findItemById(id));
        return new ResponseEntity<>(itemIsReturned,HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable("id") Long id) {
        Request request = requestService.findRequestById(id);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
