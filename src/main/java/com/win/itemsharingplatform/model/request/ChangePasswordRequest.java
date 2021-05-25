package com.win.itemsharingplatform.model.request;


import com.win.itemsharingplatform.validation.Password;
import com.win.itemsharingplatform.validation.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Email is mandatory")
    @Email
    @UniqueEmail
    private final String email;

    @NotBlank(message = "Password is mandatory")
    @Password
    private final String newPassword;

    @NotBlank(message = "Old password is mandatory")
    @Password
    private final String oldPassword;
}
