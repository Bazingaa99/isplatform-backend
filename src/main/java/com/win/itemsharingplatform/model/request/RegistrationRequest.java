package com.win.itemsharingplatform.model.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class RegistrationRequest {
    private final String username;
    private final String email;
    private final String password;
}
