package com.matheus.CoreControl.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BcryptUtil {

    public String encode(String password) {
        BCrypt.Hasher hash = BCrypt.withDefaults();
        return hash.hashToString(12, password.toCharArray());
    }

    public boolean matches(String password, String hash) {
        BCrypt.Verifyer verifier = BCrypt.verifyer();
        BCrypt.Result result = verifier.verify(password.toCharArray(), hash.toCharArray());
        return result.verified;
    }
}
