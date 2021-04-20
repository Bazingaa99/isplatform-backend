package com.win.itemsharingplatform.model.request;

import com.sun.istack.NotNull;
import com.win.itemsharingplatform.model.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestRequest implements Serializable {
    @Valid
    private Request request;

    @NotNull
    @Email
    private String email;
}
