package cn.sensorsdata.demo.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 王灼洲 on 2017/5/20
 */

public class AESCipher {
    /**
     * Generates a SecretKeySpec for given password
     *
     * @param password String
     * @return SecretKeySpec
     * @throws UnsupportedEncodingException Exception
     */
    private static SecretKeySpec getKey(String password) throws UnsupportedEncodingException {

        // You can change it to 128 if you wish
        int keyLength = 256;
        byte[] keyBytes = new byte[keyLength / 8];
        // explicitly fill with zeros
        Arrays.fill(keyBytes, (byte) 0x0);

        // if password is shorter then key length, it will be zero-padded
        // to key length
        byte[] passwordBytes = password.getBytes("UTF-8");
        int length = passwordBytes.length < keyBytes.length ? passwordBytes.length : keyBytes.length;
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static String encode(String keyString, String stringToEncode) throws NullPointerException {
        if (keyString == null || keyString.length() == 0) {
            throw new NullPointerException("Please give Password");
        }

        if (stringToEncode == null || stringToEncode.length() == 0) {
            throw new NullPointerException("Please give text");
        }

        try {
            SecretKeySpec skeySpec = getKey(keyString);
            byte[] clearText = stringToEncode.getBytes("UTF8");

            // IMPORTANT TO GET SAME RESULTS ON iOS and ANDROID
            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // Cipher is not thread safe
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);

            return Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Decodes a String using AES-256 and Base64
     *
     * @param password String
     * @param text     String
     * @return decoded String
     */
    public String decode(String password, String text) throws NullPointerException {

        if (password == null || password.length() == 0) {
            throw new NullPointerException("Please give Password");
        }

        if (text == null || text.length() == 0) {
            throw new NullPointerException("Please give text");
        }

        try {
            SecretKey key = getKey(password);

            // IMPORTANT TO GET SAME RESULTS ON iOS and ANDROID
            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            byte[] encryptedPwdBytes = Base64.decode(text, Base64.DEFAULT);
            // cipher is not thread safe
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] decryptedValueBytes = (cipher.doFinal(encryptedPwdBytes));

            return new String(decryptedValueBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
