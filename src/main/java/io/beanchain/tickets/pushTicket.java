package io.beanchain.tickets;

import io.beanchain.pools.OutPool;

public class pushTicket {
    private OutPool contractOutPool;
    private String contractHash;
    private long batchTimeStamp;

    public pushTicket(OutPool outpool, String contractHash) {
        this.contractOutPool = outpool;
        this.contractHash = contractHash;
        batchTimeStamp = System.currentTimeMillis();
    }
    
}
