package com.win.itemsharingplatform.model.request;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Email;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class ItemReturnedRequest {

    @NotNull
    private Long requestId;

    @NotNull
    @Email
    private String email;
}
