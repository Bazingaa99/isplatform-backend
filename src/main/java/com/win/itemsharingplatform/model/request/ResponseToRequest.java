package com.win.itemsharingplatform.model.request;

import lombok.Getter;

@Getter
public class ResponseToRequest {
    private Long requestId;
    private String email;
    private Boolean isAccepted;
}
