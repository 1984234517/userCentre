package com.example.usercenter.constant;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AESUtils {

    /*
     * 加密用的Key 可以用26个字母和数字组成 使用AES-128-CBC加密模式，key需要为16位。
     */

    private static final String key = "6f00cd9cade84e52";
    private static final String iv = "25d82196341548ef";

    /**
     * @author miracle.qu
     * @Description AES算法加密明文
     * @param data 明文
     * 
     * @return 密文
     */

    public static String encryptAES(String data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/");
            int blockSize = cipher.getBlockSize();
            int plaintextLength = data.length();

            byte[] dataBytes = data.getBytes("UTF-8");

            int pad = blockSize - (plaintextLength % blockSize);
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            // System.out.println(pad);

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            for (int i = dataBytes.length; i < plaintextLength; i++) {
                plaintext[i] = (byte)(pad & 0xFF);
            }
            for (int i = 0; i < plaintextLength; i++) {
                System.out.print(plaintext[i]);
            }
            System.out.println();

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes("UTF-8")); // CBC模式，需要一个向量iv，可增加加密算法的强度

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return encode(encrypted); // BASE64做转码。

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 编码
     * 
     * @param byteArray
     * @return
     */
    public static String encode(byte[] byteArray) {
        return new String(new Base64().encode(byteArray));
    }

    public static void main(String[] args) throws Exception {
        String res = encryptAES("1683426681701");
        System.out.println(res);
        System.out.println("正确的：" + "11lW0P8pUtzvJmIdla46rQ==");
    }

}