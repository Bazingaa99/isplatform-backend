package com.win.itemsharingplatform.model.request;


import com.win.itemsharingplatform.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest implements Serializable {
    @Valid
    private Item item;

    @Email(message = "email address is not valid")
    private String email;
}
