package com.nnspace.thaismoodandroid;

import android.util.Base64;
import java.io.UnsupportedEncodingException;

public class JWTUtils {

    public static String decoded(String JWTEncoded) throws Exception {
        String body = null;
        try {
            String[] split = JWTEncoded.split("\\.");
            body = getJson(split[1]);
        } catch (UnsupportedEncodingException e) {
            //Error
        }
        return body;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}
