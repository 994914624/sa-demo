package cn.sensorsdata.demo.util;

import java.security.Provider;

/**
 * Created by 王灼洲 on 2017/5/20
 *
 * Implementation of Provider for SecureRandom. The implementation supports the
 * "SHA1PRNG" algorithm described in JavaTM Cryptography Architecture, API
 * Specification & Reference
 */

public class CryptoProvider extends Provider {
    /**
     * Creates a Provider and puts parameters
     */
    public CryptoProvider() {
        super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
        put("SecureRandom.SHA1PRNG",
                "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
    }
}
