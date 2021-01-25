package com.test.ge.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Description 加密工具类
 *
 * @author lxq
 */

public class EncryptionUtil {
	/**
	 * 加密数组
	 */
    private static char[] base64EncodeChars = new char[] { 
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
            'w', 'x', 'y', 'z', '0', '1', '2', '3', 
            '4', '5', '6', '7', '8', '9', '+', '/' }; 
	
	/**
	   * 基于shiro的MD5加密
	 * @param saltSource 盐值源
	 * @param key 明文
	 * @return 密文
	 */
	public static String encryptionByMD5(String saltSource, String key) {
		// 盐为用户名得到，可以自己添加其他值
		ByteSource salt = ByteSource.Util.bytes(saltSource);
		// 参数依次为（加密方式，明文密码，盐，散列次数）
		SimpleHash simpleHash1 = new SimpleHash("MD5", key, salt, 2);
		return simpleHash1.toString();
	}
	
	/**
     * MD5加密,非shiro
     * @param key 内容       
     * @param charset 编码方式
	 * @throws Exception 
     */
	public static String MD5(String key, String charset) throws Exception {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(key.getBytes(charset));
	    byte[] result = md.digest();
	    StringBuffer sb = new StringBuffer(32);
	    for (int i = 0; i < result.length; i++) {
	        int val = result[i] & 0xff;
	        if (val <= 0xf) {
	            sb.append("0");
	        }
	        sb.append(Integer.toHexString(val));
	    }
	    return sb.toString().toLowerCase();
	}
	
	/**
	  * 基于commons-codec的BASE64加密
	 * @param key 明文字符串
	 * @return encryptionKey 加密后的字符串
	 */
	public static String encryptionByBASE64(String key) {
		if(key == null) {
			return null;
		}
		//将明文转为字符数组
		byte[] encode = Base64.encodeBase64(key.getBytes());
		// 加密后的字符串
		String encryptionKey = new String(encode);
		return encryptionKey;
	}
	
	/**
     * base64编码
     * @param str 内容       
     * @param charset 编码方式
	 * @throws UnsupportedEncodingException 
     */
	public static String base64(String str, String charset) throws UnsupportedEncodingException{
		byte[] data = str.getBytes(charset);
		
		StringBuffer sb = new StringBuffer(); 
        int len = data.length; 
        int i = 0; 
        int b1, b2, b3; 
        while (i < len) { 
            b1 = data[i++] & 0xff; 
            if (i == len) 
            { 
                sb.append(base64EncodeChars[b1 >>> 2]); 
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]); 
                sb.append("=="); 
                break; 
            } 
            b2 = data[i++] & 0xff; 
            if (i == len) 
            { 
                sb.append(base64EncodeChars[b1 >>> 2]); 
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]); 
                sb.append("="); 
                break; 
            } 
            b3 = data[i++] & 0xff; 
            sb.append(base64EncodeChars[b1 >>> 2]); 
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]); 
            sb.append(base64EncodeChars[b3 & 0x3f]); 
        } 
       
		String encoded = sb.toString();
		
		return encoded;    
	}		
	
	/**
	 * Sha256Hash加密
	 */
	public static String encryptionBySha256Hash(String salt, String key){
		return new Sha256Hash(key, salt).toHex();
		
	}
	
	public static String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
		String result = URLEncoder.encode(str, charset);
		return result;
	}
}
