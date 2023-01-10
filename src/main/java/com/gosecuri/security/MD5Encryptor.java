package com.gosecuri.security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encryptor {

    private static final String algorithm = "MD5";
    private final MessageDigest md;

    public MD5Encryptor() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance(algorithm);
    }

    public String encrypt(final String str) {
        md.update(StandardCharsets.UTF_8.encode(str));
        return String.format("%032x", new BigInteger(1, md.digest()));
    }
}
