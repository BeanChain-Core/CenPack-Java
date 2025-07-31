package io.beanchain.models;

import com.beanpack.Utils.beantoshinomics;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StakeStorage {
    private String stakerAddress;
    private String stakerPubKey;
    private long lockedBean;
    private long lastStake;

    public StakeStorage() {}

   
    public String getStakerAddress() {
        return stakerAddress;
    }

    public String getStakerPubKey() {
        return stakerPubKey;
    }


    public long getLockedBean() {
        return lockedBean;
    }

    public long getLastStake() {
        return lastStake;
    }

    public void setStakerAddress(String stakerAddress) {
        this.stakerAddress = stakerAddress;
    }

    public void setStakerPubKey(String stakerPubKey) {
        this.stakerPubKey = stakerPubKey;
    }

    public void setLockedBean(double lockedBean){
        this.lockedBean = beantoshinomics.toBeantoshi(lockedBean);
    }
    
    public void setLockedBean(long lockedBeanInBeantoshi) {
        this.lockedBean = lockedBeanInBeantoshi;
    }

    public void setLastStake(long lastStake) {
        this.lastStake = lastStake;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert StakeStorage to JSON", e);
        }
    }

    public static StakeStorage fromJson(String json) {
        try {
            return new ObjectMapper().readValue(json, StakeStorage.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON into StakeStorage", e);
        }
    }
}

