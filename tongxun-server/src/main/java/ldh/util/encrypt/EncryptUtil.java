package ldh.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单加密，以后可以就进行重构
 * @author lxf
 *
 */
public class EncryptUtil {


	final static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	
	public static String sha1(String str) {
		try {
			return sha1(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("密码加密错误");
		}
	}
	
	public static String md5(byte[] bytes) {
		return encode("MD5", bytes);
	}
	
	public static String md5(String str) {
		try {
			return md5(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("密码加密错误");
		}
	}
	
	public static String sha1(byte[] bytes) {
		return encode("SHA-1", bytes);
	}
	
	private static String encode(String encode, byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance(encode);
			md.update(bytes);
			byte[] result = md.digest();

			StringBuffer sb = new StringBuffer();

			for (byte b : result) {
				int i = b & 0xff;
				if (i < 0xf) {
					sb.append(0);
				}
				sb.append(Integer.toHexString(i));
			}
			return sb.toString();
		} catch(NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
