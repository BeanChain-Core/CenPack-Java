package io.beanchain.ContractExport;

import org.iq80.leveldb.DB;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.beanchain.models.ContractMeta;
import io.beanchain.tools.DBManager;

import java.nio.charset.StandardCharsets;

public class DevContractRegistry {
    private static DB db;

    public DevContractRegistry(){
        db = DBManager.getDB("cen-data/devContractRegistry");
    }

    public static DB getDB(){
        return db;
    }

    public static void register(String contractName, String devKey, ContractMeta meta) throws Exception {
        String key = contractName + "::" + devKey;
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(meta);
        db.put(key.getBytes(StandardCharsets.UTF_8), json.getBytes(StandardCharsets.UTF_8));
    }

    public static ContractMeta get(String contractName, String devKey) throws Exception {
        String key = contractName + "::" + devKey;
        byte[] valueBytes = db.get(key.getBytes(StandardCharsets.UTF_8));
        if (valueBytes == null) return null;
        String json = new String(valueBytes, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, ContractMeta.class);
    }

    public static String getAddress(String contractName, String devKey) throws JsonMappingException, JsonProcessingException{
        String key = contractName + "::" + devKey;
        byte[] valueBytes = db.get(key.getBytes(StandardCharsets.UTF_8));
        if (valueBytes == null) return null;
        String json = new String(valueBytes, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        ContractMeta head = mapper.readValue(json, ContractMeta.class);
        return head.getAddress();
    }

    public static String getContractHash(String contractName, String devKey) throws JsonMappingException, JsonProcessingException{
        String key = contractName + "::" + devKey;
        byte[] valueBytes = db.get(key.getBytes(StandardCharsets.UTF_8));
        if (valueBytes == null) return null;
        String json = new String(valueBytes, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        ContractMeta head = mapper.readValue(json, ContractMeta.class);
        return head.getHash();
    }

    public static String getPubKey(String contractName, String devKey) throws JsonMappingException, JsonProcessingException{
        String key = contractName + "::" + devKey;
        byte[] valueBytes = db.get(key.getBytes(StandardCharsets.UTF_8));
        if (valueBytes == null) return null;
        String json = new String(valueBytes, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        ContractMeta head = mapper.readValue(json, ContractMeta.class);
        return head.getPubKey();
    }
}


