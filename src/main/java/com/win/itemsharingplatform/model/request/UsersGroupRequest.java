package com.win.itemsharingplatform.model.request;

import com.win.itemsharingplatform.model.UsersGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersGroupRequest {
    @Valid
    private UsersGroup usersGroup;

    @Email(message = "email address is not valid")
    private String email;
}