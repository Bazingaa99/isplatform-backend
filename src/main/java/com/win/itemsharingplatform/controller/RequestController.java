package com.win.itemsharingplatform.controller;

import com.win.itemsharingplatform.model.Request;
import com.win.itemsharingplatform.model.User;
import com.win.itemsharingplatform.model.request.ItemRequestRequest;
import com.win.itemsharingplatform.model.request.ResponseToRequest;
import com.win.itemsharingplatform.service.RequestService;
import com.win.itemsharingplatform.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/isp/request/")
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;

    @GetMapping("/for/{email}&{isAccepted}")
    public ResponseEntity<List<Request>> getRequestsByOwnerEmail (@PathVariable("email") String email, @PathVariable("isAccepted") boolean isAccepted) {
        Long ownerId = userService.getUserByEmail(email).getId();
        List<Request> requests = requestService.findRequestsByOwnerIdAndAccepted(ownerId, isAccepted);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/by/{email}&{isAccepted}")
    public ResponseEntity<List<Request>> getRequestsByRequesterEmail (@PathVariable("email") String email, @PathVariable("isAccepted") boolean isAccepted) {
        Long requesterId = userService.getUserByEmail(email).getId();
        List<Request> requests = requestService.findRequestsByRequesterIdAndAccepted(requesterId, isAccepted);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }
    @GetMapping("/notRespondedByOwner/{email}")
    public ResponseEntity<List<Request>> getNotRespondedRequestsByOwnerEmail (@PathVariable("email") String email) {
        Long ownerId = userService.getUserByEmail(email).getId();
        List<Request> requests = requestService.findRequestsByOwnerIdAndResponded(ownerId, false);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }
    @GetMapping("/notRespondedByRequester/{email}")
    public ResponseEntity<List<Request>> getNotRespondedRequestsByRequesterEmail (@PathVariable("email") String email) {
        Long requesterId = userService.getUserByEmail(email).getId();
        List<Request> requests = requestService.findRequestsByRequesterIdAndResponded(requesterId, false );
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Request> addRequest(@RequestBody ItemRequestRequest itemRequestRequest){
        Long userId = userService.getUserByEmail(itemRequestRequest.getEmail()).getId();
        itemRequestRequest.getRequest().setRequester(new User(userId));
        Request newRequest = requestService.addRequest(itemRequestRequest.getRequest());
        return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
    }

    @GetMapping("exists/{itemId}&{email}")
    public Boolean checkIfRequestExists(@PathVariable("itemId") Long itemId, @PathVariable("email") String email){
        Long requesterId = userService.getUserByEmail(email).getId();
        return requestService.findRequestByItemIdAndRequesterId(itemId, requesterId);
    }

    @DeleteMapping("delete/{requestId}")
    public void deleteRequest(@PathVariable("requestId") Long requestId){
        requestService.deleteRequest(requestId);
    }

    @PutMapping("update-acceptance")
    public void updateAcceptance(@RequestBody ResponseToRequest responseToRequest){

        requestService.updateAcceptanceStatus(responseToRequest.getRequestId(),true);
    }
}
