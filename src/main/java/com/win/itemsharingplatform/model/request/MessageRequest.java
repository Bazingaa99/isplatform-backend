package com.win.itemsharingplatform.model.request;

import com.win.itemsharingplatform.model.Chat;
import com.win.itemsharingplatform.model.Message;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MessageRequest {
    @Valid
    private Message message;

    @NotBlank
    @Email
    private String email;
}
