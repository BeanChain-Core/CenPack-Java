package io.beanchain.tools;

public class Compiler {
    public static String contractPath;
    public static String outputPath = "compiled-contracts/";

    public static void init(String contractPathNew, String outputPathNew){
        contractPath = contractPathNew;
        outputPath = outputPathNew;
    }

    public static boolean compileContract() {
        if (contractPath == null || outputPath == null) {
            System.err.println("ERROR: Paths not set. Call Compiler.init(...) first.");
            return false;
        }
        
        boolean compiled = ContractCompilerUtils.compileContract(contractPath, outputPath);
        if(compiled){
            System.out.println("Compiled: " + compiled);
        } else {
            System.out.println("FAILED TO COMPILE");
        }
        return compiled;
    }
}
