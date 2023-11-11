package com.binnavigator.be.Bin.Data;

import com.binnavigator.be.Bin.BinType;
import lombok.Data;

@Data
public class BinAddRequest {
    private float latitude;
    private float longitude;
    private String information;
    private String image;
    private long userId;
    private BinType type;
}
