package com.fnb.oms_usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private Long id;
    private String firstName;
    private String surname;
    private String email;
    private String role;

}