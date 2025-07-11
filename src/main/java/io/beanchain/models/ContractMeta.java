package io.beanchain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ContractMeta {
    private String name;
    private String hash;
    private String address;
    private String pubKey;
    private String creator;
    private long timestamp;

    @JsonIgnore
    private String privateKey; // Securely stored elsewhere

    public ContractMeta(){
        
    }

    public ContractMeta(String name, String hash, String address, String pubKey, String creator, long timestamp, String privateKey) {
        this.name = name;
        this.hash = hash;
        this.address = address;
        this.pubKey = pubKey;
        this.creator = creator;
        this.timestamp = timestamp;
        this.privateKey = privateKey;
    }

    public ContractHead toHead() { return new ContractHead(name, address, pubKey, creator, 0, 0); }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPubKey() { return pubKey; }
    public void setPubKey(String pubKey) { this.pubKey = pubKey; }

    public String getCreator() { return creator; }
    public void setCreator(String creator) { this.creator = creator; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getPrivateKey() { return privateKey; }
    public void setPrivateKey(String privateKey) { this.privateKey = privateKey; }
}
