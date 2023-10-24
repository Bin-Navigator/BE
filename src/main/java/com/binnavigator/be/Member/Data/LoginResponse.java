package com.binnavigator.be.Member.Data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private long userId;
    private String username;
}
