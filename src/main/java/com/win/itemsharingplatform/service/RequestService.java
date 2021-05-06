package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.exception.RequestExistsException;
import com.win.itemsharingplatform.model.Request;
import com.win.itemsharingplatform.repository.RequestRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request addRequest(Request request){
        if(requestRepository.existsRequestByItemIdAndRequesterId(request.getItem().getId(), request.getRequester().getId())) {
            Request r = requestRepository.findRequestByItemIdAndRequesterId(request.getItem().getId(), request.getRequester().getId());
            requestRepository.deleteRequestById(r.getId());
        }
        return requestRepository.save(request);
    }
    public void save (Request request){
        requestRepository.save(request);
    }

    public List<Request> findRequestsByOwnerIdAndResponded(Long userId, boolean responded, boolean returned){
        return requestRepository.findRequestsByUserIdAndRespondedAndReturned(userId, responded,returned);
    }
    public List<Request> findRequestsByRequesterIdAndResponded(Long userId, boolean responded, boolean returned){
        return requestRepository.findRequestsByRequesterIdAndRespondedAndReturned(userId, responded, returned);
    }
    public List<Request> findRequestsByOwnerIdAndAcceptedAndResponded(Long userId, boolean isAccepted, boolean isResponded, boolean returned){
        return requestRepository.findRequestsByUserIdAndAcceptedAndRespondedAndReturned(userId, isAccepted, isResponded, returned);
    }

    public List<Request> findRequestsByRequesterIdAndAccepted(Long userId, boolean isAccepted, boolean returned){
        return requestRepository.findRequestsByRequesterIdAndAcceptedAndReturned(userId, isAccepted, returned);
    }

    public Request findRequestByItemIdAndRequesterId(Long itemId, Long requesterId){
        return requestRepository.findRequestByItemIdAndRequesterId(itemId, requesterId);
    }

    public void deleteRequest(Long requestId){
        requestRepository.deleteRequestById(requestId);
    }

    public void deleteRequestsByItemIdAndAccepted(Long itemId, boolean accepted) { requestRepository.deleteRequestsByItemIdAndAccepted(itemId, accepted); }

    public void updateAcceptanceStatus(Long requestId, Boolean isAccepted){
        requestRepository.updateResponseStatus(requestId,true);
        requestRepository.updateAcceptanceStatus(requestId, isAccepted,LocalDateTime.now());
    }

    public void declineRequests(Long itemId, Long requesterId){
        requestRepository.declineRequestsByItemIdAndNotRequesterId(itemId, requesterId);
    }

    public Request findRequestById(Long requestId){return requestRepository.findRequestById(requestId);}

    public void deleteRequestsByItemId(Long itemId){
        requestRepository.deleteByItemId(itemId);
    }
}
