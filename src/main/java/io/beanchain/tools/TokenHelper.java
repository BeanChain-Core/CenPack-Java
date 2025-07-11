package io.beanchain.tools;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class TokenHelper {

    public String generateHash(String minter, String token, String supply, String symbol){
        
        try {
            String data = minter + token + supply + symbol; //uses original supply at mint 
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for(byte b: hash){
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
