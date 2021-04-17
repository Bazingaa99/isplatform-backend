package com.win.itemsharingplatform.model.request;

import com.win.itemsharingplatform.model.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestRequest implements Serializable {
    private Request request;

    private String email;
}
