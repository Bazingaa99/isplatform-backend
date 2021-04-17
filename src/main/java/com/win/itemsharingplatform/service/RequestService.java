package com.win.itemsharingplatform.service;

import com.win.itemsharingplatform.model.Request;
import com.win.itemsharingplatform.repository.RequestRepository;
import com.win.itemsharingplatform.repository.UserRepository;
import com.win.itemsharingplatform.repository.UsersGroupRepository;
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
        return requestRepository.save(request);
    }

    public List<Request> findRequestsByOwnerIdAndAccepted(Long userId, boolean isAccepted){
        return requestRepository.findRequestsByUserIdAndAccepted(userId, isAccepted);
    }

    public List<Request> findRequestsByRequesterIdAndAccepted(Long userId, boolean isAccepted){
        return requestRepository.findRequestsByRequesterIdAndAccepted(userId, isAccepted);
    }
}
