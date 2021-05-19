package com.win.itemsharingplatform.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetUserNotificationSeenRequest implements Serializable {
    @NotNull
    private Long notificationId;
}
