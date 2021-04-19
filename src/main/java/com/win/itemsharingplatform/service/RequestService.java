package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.exception.RequestExistsException;
import com.win.itemsharingplatform.model.Request;
import com.win.itemsharingplatform.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request addRequest(Request request){
        if(!requestRepository.existsRequestByItemIdAndRequesterId(request.getItem().getId(), request.getRequester().getId())) {
            return requestRepository.save(request);
        }else{
            throw new RequestExistsException("You have already requested this item.");
        }
    }

    public List<Request> findRequestsByOwnerIdAndResponded(Long userId, boolean responded){
        return requestRepository.findRequestsByUserIdAndResponded(userId, responded);
    }
    public List<Request> findRequestsByRequesterIdAndResponded(Long userId, boolean responded){
        return requestRepository.findRequestsByRequesterIdAndResponded(userId, responded);
    }
    public List<Request> findRequestsByOwnerIdAndAccepted(Long userId, boolean isAccepted){
        return requestRepository.findRequestsByUserIdAndAccepted(userId, isAccepted);
    }

    public List<Request> findRequestsByRequesterIdAndAccepted(Long userId, boolean isAccepted){
        return requestRepository.findRequestsByRequesterIdAndAccepted(userId, isAccepted);
    }

    public Boolean findRequestByItemIdAndRequesterId(Long itemId, Long requesterId){
        return requestRepository.existsRequestByItemIdAndRequesterId(itemId, requesterId);
    }

    public void deleteRequest(Long requestId){
        requestRepository.deleteRequestById(requestId);
    }

    public void updateAcceptanceStatus(Long requestId, Boolean isAccepted){
        requestRepository.updateResponseStatus(requestId,true);
        requestRepository.updateAcceptanceStatus(requestId, isAccepted);
    }

    public Request findRequestById(Long requestId){return requestRepository.findRequestById(requestId).get();}

}
