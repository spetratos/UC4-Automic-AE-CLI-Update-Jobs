package com.automic.std;

import java.nio.charset.StandardCharsets;

import com.uc4.util.Base64;


public class CryptoUtils {

	static final String key = "Un1ver$e2015!@)#"; // The key for 'encrypting' and 'decrypting'.
	
	public static String encode(String s) {
        return Base64.encode(s.getBytes(),true);
    }

    public static String decode(String s) {
        return new String(Base64.decode(s), StandardCharsets.UTF_8);
    }

}