package com.amazon.demo.service;

import org.apache.commons.codec.binary.Base64;

public class Base64Utility {

    public static String encode(String input) {
        return new String(Base64.encodeBase64(input.getBytes()));
    }

    public static String decode(String input) {
        return new String(Base64.decodeBase64(input.getBytes()));
    }
}
