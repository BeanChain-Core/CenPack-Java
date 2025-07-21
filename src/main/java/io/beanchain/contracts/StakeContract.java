// package com.beanchain.contracts;

// import org.iq80.leveldb.DB;

// import com.beanchain.dbs.ConfirmedFundedTxDB;
// import com.beanpack.CENdev.CallManager;
// import com.beanpack.TXs.CENCALL;


// import com.beanpack.TXs.MintTX;
// import com.beanpack.TXs.TX;
// import com.beanpack.Utils.beantoshinomics;
// import com.beanpack.Utils.hex;
// import com.beanpack.crypto.TransactionVerifier;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.JsonMappingException;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import io.beanchain.ContractExport.ContractStorage;
// import io.beanchain.ContractExport.DevContractRegistry;
// import io.beanchain.models.ContractMeta;
// import io.beanchain.models.StakeStorage;
// import io.beanchain.pools.OutPool;

// class StakeContract implements BaseContract{
//     private static final String devKey = "teamStake42"; //devs should hard code this an use it when submitting the contract to CEN (just a way to internally access your contract within itself via the loadIdentity method)
//     private static final String contractName = "com.beanchain.contracts.StakeContract"; //this is the name needed for loading data and the format used by the initializer and dynamic loader

//     private String contractHash;
//     private String contractAddress;
//     private String contractPubKey;
//     private ContractStorage storage;
    
    
//     @Override
//     public void loadIdentity(DB db, String contractName, String devKey){ 
//         DevContractRegistry registry = new DevContractRegistry();
//         try {
//             ContractMeta meta = DevContractRegistry.get(contractName, devKey);
//             this.contractHash = meta.getHash();
//             this.contractAddress = meta.getAddress();
//             this.contractPubKey = meta.getPubKey();
//             this.storage = ContractStorage.getStorage(contractHash);
            
//         } catch (Exception e) {
//             System.out.println("FAILED TO LOAD CONTRACT IDENTITY");
//             e.printStackTrace();
//         }
        
//     }
    
//     @Override
//     public void init() {
//         double supply = 1; //initializes the LockedBean token to establish the token hash identity (needed for core validation protocols) 
//         String token = "LockedBean";
//         String symbol = "LOCB";
//         // TODO 
//         MintTX stakeMint = InternalTxFactory.contractMintTokenInit(contractAddress, contractPubKey, contractAddress, supply, 0, token, supply, symbol, contractHash);
//         //ContractHead head = new ContractHead(contractName, contractAddress, contractPubKey, symbol, 0, 0);
        
//         OutPool.submitTX(stakeMint);

//     }

//     @Override
//     public void execute(CENCALL call) {
//         try {
//             CallManager managedCall = new CallManager(call.toJSON());
//             String method = managedCall.getMethod();
//             String callHash = managedCall.getCallHash();

//             switch (method) {
//                 case "stake":
//                     System.out.println("STAKECALL");
//                     //processStake(managedCall);
//                     break;
//                 case "unstake":
//                     break;
//                 default :
//                     System.out.println("ERROR UNKNOWN CALL METHOD - INVALID STAKE CALL REJECTED**: " + callHash);
//             }
//         } catch (Exception callManagerFail){
//             System.out.println("Failed to manage and process CENCALL, Call rejected");
//             callManagerFail.printStackTrace();
//         }
//     }

//     //TODO: Need to pick up here
    
//     public void processStake(CallManager managedCall) throws Exception {
//         if (!managedCall.isSignatureValid()) {
//             System.out.println("INVALID CALL SIGNATURE");
//             return;
//         }

//         TX fundingTX = managedCall.getEmbeddedTX();
//         if (fundingTX == null) {
//             System.out.println("MISSING FUNDING TX - REJECTED 'UNFUNDED'");
//             return;
//         }

//         // Validate embedded TX signature
//         String txHash = fundingTX.getTxHash();

//         if(!ConfirmedFundedTxDB.exists(txHash)){
//             //TODO:RETURN TO QUE OR REQUEST TX FROM PARENT NODE
//             return;
//         }

//         String signature = fundingTX.getSignature();
//         byte[] bytesHash = hex.hexToBytes(txHash);
//         String pubKey = fundingTX.getPublicKeyHex();

//         if (!TransactionVerifier.verifySHA256Transaction(pubKey, bytesHash, signature)) {
//             System.out.println("INVALID EMBEDDED FUNDING TX SIGNATURE");
//             return;
//         }

//         String stakerAddress = fundingTX.getFrom();
//         double stakeAmount = fundingTX.getAmount();

//         // Log stake to contract DB — you can use a prefix like "stake:<address>"
//         String stakeKey = "stake:" + stakerAddress;
//         if(storage.get(stakeKey) == null){
//             storage.put(stakeKey, String.valueOf(beantoshinomics.toBeantoshi(stakeAmount)));
//         }

//         //Issue matching LOCB token amount (1:1 ratio for now)
//         MintTX mintLOCB = InternalTxFactory.contractMintMoreStake(contractAddress, contractPubKey, stakerAddress, stakeAmount, Integer.parseInt(storage.get("nonce")), "LockedBean", stakeAmount, "LOCB", contractHash);
//         OutPool.submitTX(mintLOCB);

//         System.out.println("Stake successful for: " + stakerAddress + ", amount: " + stakeAmount);
//     }

    

//     public void storageHelper(String stakerAddress, StakeStorage stakeStorage) throws JsonMappingException, JsonProcessingException{
//         String stakeKey = "stake:" + stakerAddress;
//         if(storage.get(stakeKey) == null){
//             String storageJson = stakeStorage.toJson();
//             storage.put(stakeKey, storageJson);
//             return;
//         } else { 
//             ObjectMapper mapper = new ObjectMapper();
//             String json = String.valueOf(storage.get(stakeKey));
//             StakeStorage storedStorage = mapper.readValue(json, StakeStorage.class);
//         }
//     }

    
// }

