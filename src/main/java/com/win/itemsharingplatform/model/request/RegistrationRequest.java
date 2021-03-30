package com.win.itemsharingplatform.model.request;

import com.win.itemsharingplatform.validation.Password;
import com.win.itemsharingplatform.validation.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class RegistrationRequest {

    @NotBlank(message = "Username is mandatory")
    @Size(min= 3,max = 20, message = "Username is too long/short")
    private final String username;

    @NotBlank(message = "Email is mandatory")
    @Email
    @UniqueEmail
    private final String email;

    @NotBlank(message = "Password is mandatory")
    @Password
    private final String password;
}
