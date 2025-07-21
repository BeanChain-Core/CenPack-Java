package io.beanchain.pools;

import java.util.ArrayList;
import java.util.List;

import com.beanpack.TXs.TX;
import com.beanpack.logger.PackLoggerManager;

public class OutPool {
    public List<TX> outPool = new ArrayList<>();

    public OutPool() {
        
    }

    public boolean addTX(TX tx){
        try {
            this.outPool.add(tx);
            return true;
        } catch (Exception poolException){
            PackLoggerManager.PackLoggerFPrint("[POOL] COULD NOT ADD TX TO POOL");
            return false;
        }
    }
}
