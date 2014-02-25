package ldh.util.encrypt;

import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AESCoder {

	public static byte[] iv = new byte[] { 82, 22, 50, 44, -16, 124, -40, -114, -87, -40, 37, 23, -56, 23, -33, 75 };

    private static AESCoder aes = null;

    public static byte[] key1 = new byte[] { -42, 35, 67, -86, 19, 29, -11, 84, 94, 111, 75, -104, 71, 46, 86, -21, -119, 110, -11, -32, -28, 91, -33, -46, 99, 49, 2, 66, -101, -11, -8, 56 };

    private AESCoder() {

    }

    public static synchronized AESCoder getInstance() {
        if (aes == null) {
            aes = new AESCoder();
        }
        return aes;
    }
    
	public String encrypt(byte[] msg) {
        String str = "";
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key1));
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            SecretKey key = kgen.generateKey();
            Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            str = BASE64.encrypt(ecipher.doFinal(msg));
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return str;
    }
	
	public byte[] decrypt(String value) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key1));
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            SecretKey key = kgen.generateKey();
            Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            return dcipher.doFinal(BASE64.decrypt(value));
        } catch (Exception e) {
            throw new java.lang.IllegalArgumentException("decrypt error!!!");
        } 
    }
}
