package com.win.itemsharingplatform.model.request;


import com.win.itemsharingplatform.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest implements Serializable {
    private Item item;
    private String email;
}
