package io.beanchain.pools;


import com.beanpack.CENdev.CallManager;
import com.beanpack.TXs.CENCALL;

public class ExecutePool extends CallPool{


    public void addFullCenCall(CENCALL call) throws Exception {
        if (call != null && call.getParams() != null) {
            CallManager manager = new CallManager(call.toJSON());
            if (manager.getCallHash() != null) {
                String callHash = manager.getCallHash();
                addCenCall(callHash, call);
            } else {
                System.out.println("CALLHASH NOT FOUND. ERROR FAULTY CALL");
            }
        }
    }

    
}
