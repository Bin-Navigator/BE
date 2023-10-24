package com.binnavigator.be.Member.Data;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
