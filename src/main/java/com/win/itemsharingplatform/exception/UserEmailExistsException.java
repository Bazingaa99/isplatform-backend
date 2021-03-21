package com.win.itemsharingplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserEmailExistsException extends UserException {
    public UserEmailExistsException(String message) {
        super(String.format("Email is already taken", message));
    }
}