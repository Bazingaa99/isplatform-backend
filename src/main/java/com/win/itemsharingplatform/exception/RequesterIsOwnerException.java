package com.win.itemsharingplatform.exception;

public class RequesterIsOwnerException extends RuntimeException {
    public RequesterIsOwnerException(String message) {
        super(message);
    }
}
