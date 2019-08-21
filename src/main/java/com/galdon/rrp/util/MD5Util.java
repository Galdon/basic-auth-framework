package com.galdon.rrp.util;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: rrp
 * @description: MD5 工具类
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
public class MD5Util {
	
	private static final Logger logger = LoggerFactory.getLogger(MD5Util.class);
	
	private static final int STREAM_BUFFER_LENGTH = 1024 * 1024;
	
	private static MessageDigest messageDigest = null;
	
	static {
        try {
        	messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        	logger.error(e.getMessage(), e);
        }
    }
	
	/**
	 * <h1>字符串MD5</h1>
	 * @author galdon
	 * @date 2017年7月6日下午3:30:04
	 * @version 1.0
	 * @param input 字符串
	 * @return
	 */
	public static String MD5(final String input) {
		return MD5(input, false);
	}
	
	/**
	 * <h1>字符串MD5</h1>
	 * @author galdon
	 * @date 2017年7月6日下午3:30:04
	 * @version 1.0
	 * @param input 字符串
	 * @param toLowerCase 是否小写的16进制字符串
	 * @return
	 */
	public static String MD5(final String input, final boolean toLowerCase) {
		byte[] inputByteArray = input.getBytes();
		messageDigest.update(inputByteArray);
		byte[] byteArray = messageDigest.digest();
		return new String(Hex.encodeHex(byteArray, toLowerCase));
	}
	
	/**
	 * <h1>文件MD5，支持超大文件</h1>
	 * @author galdon
	 * @date 2017年7月6日下午3:30:04
	 * @version 1.0
	 * @param file 文件
	 * @return
	 */
	public static String MD5(final File file) throws IOException {
		return MD5(file, false);
	}
	
	/**
	 * <h1>文件MD5，支持超大文件</h1>
	 * @author galdon
	 * @date 2017年7月6日下午3:30:04
	 * @version 1.0
	 * @param file 文件
	 * @param toLowerCase 是否小写的16进制字符串
	 * @return
	 */
	public static String MD5(final File file, final boolean toLowerCase) throws IOException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			return MD5(in, toLowerCase);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * <h1>文件MD5，支持超大文件</h1>
	 * @author galdon
	 * @date 2017年7月6日下午3:30:04
	 * @version 1.0
	 * @param in 流
	 * @param toLowerCase 是否小写的16进制字符串
	 * @return
	 */
	public static String MD5(final InputStream in, final boolean toLowerCase) throws IOException {
		DigestInputStream digestInputStream = null;
		try {
			digestInputStream = new DigestInputStream(in, messageDigest);
			byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
			while (digestInputStream.read(buffer) > 0);
			messageDigest = digestInputStream.getMessageDigest();
			byte[] byteArray = messageDigest.digest();
			return new String(Hex.encodeHex(byteArray, toLowerCase));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
		}
	}
	
}
