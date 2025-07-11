package io.beanchain.models;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ContractHead {
    private String contractName;
    private String contractAddy;
    private String contractPubKey;
    private String creatorAddress;
    private int contractNonce;
    private int contractLayer2Nonce;

    private static final ObjectMapper mapper = new ObjectMapper();

    public ContractHead() {}

    public ContractHead(String name, String address, String pubKey, String creatorAddy, int nonce, int layer2Nonce) {
        this.contractName = name;
        this.contractAddy = address;
        this.contractPubKey = pubKey;
        this.creatorAddress = creatorAddy;
        this.contractNonce = nonce;
        this.contractLayer2Nonce = layer2Nonce;
    }

    public String toJson() {
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize ContractHead", e);
        }
    }

    public static ContractHead fromJson(String json) {
        try {
            return mapper.readValue(json, ContractHead.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse ContractHead JSON", e);
        }
    } 

    // --- Getters & Setters ---

    public String getContractName() { return contractName; }
    public void setContractName(String contractName) { this.contractName = contractName; }

    public String getContractAddy() { return contractAddy; }
    public void setContractAddy(String contractAddy) { this.contractAddy = contractAddy; }

    public String getContractPubKey() { return contractPubKey; }
    public void setContractPubKey(String contractPubKey) { this.contractPubKey = contractPubKey; }

    public String getCreatorAddress() { return creatorAddress; }
    public void setCreatorAddress(String creatorAddress) { this.creatorAddress = creatorAddress; }

    public int getContractNonce() { return contractNonce; }
    public void setContractNonce(int contractNonce) { this.contractNonce = contractNonce; }

    public int getContractLayer2Nonce() { return contractLayer2Nonce; }
    public void setContractLayer2Nonce(int contractLayer2Nonce) { this.contractLayer2Nonce = contractLayer2Nonce; }
}
