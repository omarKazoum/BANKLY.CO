package com.example.gateway.dto;

import com.example.gateway.entity.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthDTO {
    Long id;
    String email;
    String firstName;
    String lastName;
    String password;
    RoleEnum role;
    //ignored in post requests
    String jwtToken;
}
