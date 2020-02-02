package com.crui.house.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * VM Args:
 *
 * @author crui
 */
public class HashUtils {

    private static final HashFunction FUNCTION = Hashing.md5();
    private static final String SALT = "crui.com";

    public static String encryPasswoed(String password){
        HashCode hashCode = FUNCTION.hashString(password+SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }
}
