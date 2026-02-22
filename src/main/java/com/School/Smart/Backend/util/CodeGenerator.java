package com.School.Smart.Backend.util;

import java.security.SecureRandom;

public class CodeGenerator {
    
    private static final String Code= "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int length=10;
    private static final SecureRandom random=new SecureRandom();

    public static String generateCode(){
        StringBuilder GenerateRandomCode=new StringBuilder();

        for(int i=0;i<length;i++){
            GenerateRandomCode.append(Code.charAt(random.nextInt(Code.length())));

        }
        return GenerateRandomCode.toString();
    }
}
