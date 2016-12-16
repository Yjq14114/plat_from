package tt.wifi.app.utils;

/**
 * Created by yangjq on 2016/10/1.
 */
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class AESUtil {

    private final static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    //private final static String keyPre="klcvioer";

    public static void main(String[] args) {
        String key = "1234567";
        System.out.println(getKeySeed(key));
    }

    /*String tmpKey = key;
        if (key.length()<8) {
            for (int i=0;i<8-key.length();i++){
                tmpKey=String.format("0%s",tmpKey);
            }
        } else if (key.length()>8) {
            tmpKey = key.substring(key.length()-8);
        }*/
    private static String getKeySeed(String key) {
        return StringUtil.MD5(key);
    }

    public static String encode(String str, String key) {

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        try {
            byte[] textBytes = str.getBytes("UTF-8");
            SecretKeySpec newKey = new SecretKeySpec(getKeySeed(key).getBytes("UTF-8"), "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
            return Base64.encodeBase64String(cipher.doFinal(textBytes));
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("加密发生错误,error={},str={},key={}",e.getMessage(),str,key);
        }
        return str;
    }

    public static String decode(String str, String key) {

        byte[] textBytes = Base64.decodeBase64(str);
        //byte[] textBytes = str.getBytes("UTF-8");
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        try {
            SecretKeySpec newKey = new SecretKeySpec(getKeySeed(key).getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            return new String(cipher.doFinal(textBytes), "UTF-8");
        } catch(Exception e) {
            e.printStackTrace();
            logger.error("解密发生错误,error={},str={},key={}",e.getMessage(),str,key);
        }
        return str;
    }

}
