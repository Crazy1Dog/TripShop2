package com.chiyu.shopapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class Encryption {
	private static final String Algorithm = "DESede"; // 定义 加密算法,可用
	private static final String PASSWORD_CRYPT_KEY = "satisfy_app";

	// src为被加密的数据缓冲区（源）
	public static byte[] encryptMode(byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(
					build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
			
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param
	 * @return 加密后的base64编码
	 */
	public static String encryptToBase64(String src) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(encryptMode(src.getBytes()));
	}

	/*
	 * 根据字符串生成密钥字节数组
	 * 
	 * @param keyStr 密钥字符串
	 * 
	 * @return
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] build3DesKey(String keyStr)
			throws UnsupportedEncodingException {
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes(); // 将字符串转成字节数组
		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	/**
	 * 将文件转换成base64 字符串
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String encodeBase64File(String path) {
		FileInputStream in = null;
		try {
			File file = new File(path);
			in = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			in.read(buffer);
			return new BASE64Encoder().encode(buffer).replaceAll("\r\n", "")
					.replaceAll("\n", "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	/**
	 * 将图片转换成十六进制字符串
	 * 
	 * @param photo
	 * @return
	 */
	public static String sendPhoto(ImageView photo) {
		Drawable d = photo.getDrawable();
		Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0 -
		byte[] bt = stream.toByteArray();
		String photoStr = byte2hex(bt);
		return photoStr;
	}

	/**
	 * 二进制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}

		}
		return sb.toString();
	}
}