package com.win.itemsharingplatform.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToGroupRequest implements Serializable {
    private static final long serialVersionUID = -6986746375915710855L;

    @Email(message = "Email is wrong")
    @NotEmpty(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Token is mandatory")
    private String token;
}