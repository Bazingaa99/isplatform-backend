package com.win.itemsharingplatform.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemBookmarkRequest {
    @NotNull
    private Long itemId;

    @Email
    @NotBlank
    private String email;
}
