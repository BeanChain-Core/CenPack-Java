package io.beanchain.pools;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import com.beanpack.TXs.CENCALL;

public abstract class CallPool {

    protected ConcurrentHashMap<String, CENCALL> callMap = new ConcurrentHashMap<>();

    public void addCenCall(String hash, CENCALL call) {
        callMap.put(hash, call);
    }

    public boolean removeCenCall(String hash) {
        return callMap.remove(hash) != null;
    }

    public boolean hasPendingCalls() {
        return !callMap.isEmpty();
    }

    public List<CENCALL> getAllCenCalls() {
        Collection<CENCALL> values = callMap.values();
        return new ArrayList<>(values);
    }

    public CENCALL getCenCall(String hash) {
        return callMap.get(hash);
    }

    public int size() {
        return callMap.size();
    }
}
