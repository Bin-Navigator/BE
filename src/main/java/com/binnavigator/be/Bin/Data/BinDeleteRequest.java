package com.binnavigator.be.Bin.Data;

import lombok.Data;

@Data
public class BinDeleteRequest {
    private long binId;
    private long userId;
}
