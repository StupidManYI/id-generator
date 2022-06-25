package com.echao.echao.idGenerator.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class EXRSAPXUtil {
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥

    public static void main(String[] args) throws Exception {
//        //生成公钥和私钥
//        genKeyPair();
//        //加密字符串
//        String message = "http://101.35.182.169:80/serverstatus";
//        System.out.println("随机生成的公钥为:" + keyMap.get(0));
//        System.out.println("随机生成的私钥为:" + keyMap.get(1));
//        String messageEn = encrypt(message, keyMap.get(0));
//        System.out.println(message + "\t加密后的字符串为:" + messageEn);
//        String messageDe = decrypt(messageEn, keyMap.get(1));
//        System.out.println("还原后的字符串为:" + messageDe);
        String messageDe = decrypt(after, EXRSAPXUtil.privateKey );
        System.out.println(messageDe);
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(3072, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    protected static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }


    protected static final String privateKey = "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";

    protected static final String after = "ZSUKrsKzAgaPLFAyhwaADURH5ZzEMkeqNOtHCM/Cr5ccRBVZIKq9J/zHSW2GXYNzNrfXPI8QgeiOTIEmnY5KBDeXbtWQcMEvIPTIGYKzGF1j4itnY0juB0oWUM3diNyv2H9uI7tOW3F2jLIAKK7DeY/B/boOwBonRXa/wzxqCpddEjYBrEpv3p5qalhpFenrT9+GPdZd+8O11/4pzbha36NCXOqnDZbDJeJT9QpTFsylgpUnuuxmkLWPY4SGcYmy6E8iUDjhVuX8tBjtBli0TKA/lAE87AjZ+567SW6JbxLJrvfJ+2LppDA9sZbMqBUDCvVkQVXlV5p0/qxWYQU+naEXU97mz/7gwiqWVN8R3pTcHsnExcYzyVFsbEBkXDdZuPHTYnrHV1zZk6/var5sOP5p/1nmHzp6CLeYufnH3MgALyaTFTFR00r1t3wJgZ6+9QEH+28lx903l6kOrw4L6q/nvafTXJG5bugzWbi9pm2p58/GoIpPp0HzwvtHW9J7";
}

