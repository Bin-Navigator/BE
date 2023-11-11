package com.binnavigator.be.Member.Data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGetResponse {
    private int numOfBins;
    private int distance;
}
