package io.beanchain.ContractExport;

import org.iq80.leveldb.DB;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.beanchain.models.ContractHead;
import io.beanchain.tools.DBManager;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ContractStorage {
    private final DB db;
    private static final Map<String, ContractStorage> openStorages = new ConcurrentHashMap<>();

    private ContractStorage(String contractHash) {
        // Register DB at: cen-data/contracts/{hash}/state
        this.db = DBManager.getDB("cen-data/contracts/" + contractHash + "/state");
    }

    public static synchronized ContractStorage getStorage(String contractHash) {
        return openStorages.computeIfAbsent(contractHash, h -> new ContractStorage(h));
    }

    public void put(String key, String value) {
        db.put(bytes(key), bytes(value));
    }

    public String get(String key) {
        byte[] value = db.get(bytes(key));
        return value != null ? asString(value) : null;
    }

    public void delete(String key) {
        db.delete(bytes(key));
    }

    private byte[] bytes(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    private String asString(byte[] b) {
        return new String(b, StandardCharsets.UTF_8);
    }

    public ContractHead getContractHead() throws JsonMappingException, JsonProcessingException {
        String head = get("head");
        if (head == null) {
            throw new IllegalStateException("Missing contract head for this contract storage");
        }
        return new ObjectMapper().readValue(head, ContractHead.class);
    }

    public void saveContractHead(ContractHead contractHead) throws JsonMappingException, JsonProcessingException {
        put("head", contractHead.toJson());
    }

    public int getContractNonce() throws JsonMappingException, JsonProcessingException{
        ContractHead head = getContractHead();
        return head.getContractNonce();
    }

    public int getContractLayer2Nonce() throws JsonMappingException, JsonProcessingException{
        ContractHead head = getContractHead();
        return head.getContractLayer2Nonce();
    }
    
    public void incrimentNonce() throws JsonMappingException, JsonProcessingException{
        ContractHead head = getContractHead();
        int nonce = head.getContractNonce();
        head.setContractNonce(nonce + 1);
        saveContractHead(head);
    }

    public void incrimentL2Nonce() throws JsonMappingException, JsonProcessingException{
        ContractHead head = getContractHead();
        int nonce = head.getContractLayer2Nonce();
        head.setContractLayer2Nonce(nonce + 1);
        saveContractHead(head);
    }
}