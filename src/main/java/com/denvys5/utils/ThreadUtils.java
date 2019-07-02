package com.denvys5.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ThreadUtils {
    static String encrypt(String input, String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            BaseUtils.sendErr("Ключ должен быть 16 символов");
        }
        return new String(new sun.misc.BASE64Encoder().encode(crypted));
    }

    static String decrypt(String input, String key) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(input));
        } catch (Exception e) {
            BaseUtils.sendErr("Ключ шифрование не совпадает или больше 16 символов, или полученна ошибка от launcher.php");
            BaseUtils.sendErr("Проверьте настройку  в Settings.java или connect.php");
        }
        return new String(output);
    }
}