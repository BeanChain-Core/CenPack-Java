package io.beanchain.models;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StakeStorage {
    private String stakerAddress;
    private String stakerPubKey;
    private long beantoshiStaked;
    private long lockedBean;
    private long lastStake;

    public StakeStorage() {}

   
    public String getStakerAddress() {
        return stakerAddress;
    }

    public String getStakerPubKey() {
        return stakerPubKey;
    }

    public long getBeantoshiStaked() {
        return beantoshiStaked;
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

    public void setBeantoshiStaked(long beantoshiStaked) {
        this.beantoshiStaked = beantoshiStaked;
    }

    public void setLockedBean(long lockedBean) {
        this.lockedBean = lockedBean;
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

