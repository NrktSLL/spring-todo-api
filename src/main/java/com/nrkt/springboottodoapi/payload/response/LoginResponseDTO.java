package com.nrkt.springboottodoapi.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LoginResponseDTO implements Serializable {
    @JsonProperty("token")
    String token;
}
