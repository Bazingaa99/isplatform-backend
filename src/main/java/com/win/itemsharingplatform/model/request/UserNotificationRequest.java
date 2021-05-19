package com.win.itemsharingplatform.model.request;


import com.win.itemsharingplatform.model.UserNotification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationRequest implements Serializable {
    @NotBlank
    private String userNotificationMessage;

    @NotNull
    private Long writerId;

    @NotNull
    private Long receiverId;
}
