package com.binnavigator.be.Bin.Data;

import lombok.Data;

@Data
public class BinUpdateRequest {
    private long binId;
    private String information;
}
