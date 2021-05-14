package com.win.itemsharingplatform.model.request;


import com.win.itemsharingplatform.model.Feedback;
import com.win.itemsharingplatform.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequest implements Serializable {
    @Valid
    private Feedback feedback;

    @Email(message = "email address is not valid")
    private String email;

    @NotNull
    private Long receiverId;
}
