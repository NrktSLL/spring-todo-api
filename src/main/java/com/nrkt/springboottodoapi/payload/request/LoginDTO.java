package com.nrkt.springboottodoapi.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LoginDTO implements Serializable {
    @JsonProperty("email")
    @Email
    String email;
    @JsonProperty("password")
    @NotBlank
    String password;
}
