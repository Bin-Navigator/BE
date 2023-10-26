package com.binnavigator.be.Bin.Data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetResponse {
    private long binId;
    private float latitude;
    private float longitude;
    private String information;
}
