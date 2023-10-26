package com.binnavigator.be.Bin.Data;

import lombok.Data;

@Data
public class BinAddRequest {
    private float latitude;
    private float longitude;
    private String information;
    private long userId;
}
