package com.dhm.encrypt;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * 加解密工具类
 * 之前base64加密用的是sun公司的sun.misc.BASE64Encoder/BASE64Decoder，
 * 由于后面版本更新，sun公司被oracle公司收购，加密类BASE64Encoder被org.apache.commons.codec.binary.Base64替代了。
 * 
 * @author ZGL
 * 
 */
public class CipherUtil {

	/**
	 * 加密，非对称加密一般来说是公钥加密，私钥解密
	 * @param cipherAlgorithm 加密算法
	 * @param strHexKey 16进制字符串钥匙
	 * @param byteData 待加密数据
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] encrypt(EnumCipherAlgorithm cipherAlgorithm, String strHexKey, byte[] byteData)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return encrypt(cipherAlgorithm, NumberUtil.strHexToBytes(strHexKey), byteData);
		
	}
	
	/**
	 * 加密，非对称加密一般来说是公钥加密，私钥解密
	 * @param cipherAlgorithm 加密算法
	 * @param byteKey 二进制钥匙
	 * @param byteData 待加密数据
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] encrypt(EnumCipherAlgorithm cipherAlgorithm,  byte[] byteKey, byte[] byteData)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Security.addProvider(new BouncyCastleProvider());
		// 生成秘密密钥或公钥
		Key key = null;
		switch (cipherAlgorithm.getKeyAlgorithm()) {
			case DES:
			case DESede:
			case AES:
			case IDEA:
			{
				key = new SecretKeySpec(byteKey, cipherAlgorithm.getKeyAlgorithm().name());
				break;
			}
			case RSA:
			case DSA:
			case ElGamal:
			{
				try {
					// 尝试获取公钥
					X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
							byteKey);
					key = KeyFactory.getInstance(cipherAlgorithm.getKeyAlgorithm().name())
							.generatePublic(x509EncodedKeySpec);
					break;
				} catch (Exception e) {
					try {
						// 尝试获取私钥
						PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
								byteKey);
						key = KeyFactory.getInstance(cipherAlgorithm.getKeyAlgorithm().name())
								.generatePrivate(pkcs8EncodedKeySpec);
						break;
					} catch (Exception e2) {
						break;
					}
				}
				
			}
			default:
				break;
		}

		return encrypt(cipherAlgorithm, key, byteData);
	}
	
	/**
	 * 解密，非对称加密一般来说是公钥加密，私钥解密
	 * @param cipherAlgorithm 加密算法
	 * @param byteData 待解密数据
	 * @param key 钥匙
	 * @return
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception
	 */
	public static byte[] encrypt(EnumCipherAlgorithm cipherAlgorithm, Key key, byte[] byteData)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String algorithm = key.getAlgorithm();
		if(cipherAlgorithm != null){
			algorithm = cipherAlgorithm.getValue();
		}
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(byteData);
	}
	
	
	
	
	
	
	
	/**
	 * 解密，非对称加密一般来说是公钥加密，私钥解密
	 * @param cipherAlgorithm 加密算法
	 * @param strHexKey 16进制字符串钥匙
	 * @param byteData 待解密数据
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decrypt(EnumCipherAlgorithm cipherAlgorithm,  String strHexKey, byte[] byteData) 
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return decrypt(cipherAlgorithm, NumberUtil.strHexToBytes(strHexKey), byteData);
	}

	/**
	 * 解密，非对称加密一般来说是公钥加密，私钥解密
	 * @param cipherAlgorithm 加密算法
	 * @param byteKey 二进制钥匙
	 * @param byteData 待解密数据
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decrypt(EnumCipherAlgorithm cipherAlgorithm,  byte[] byteKey, byte[] byteData) 
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Security.addProvider(new BouncyCastleProvider());
		// 生成秘密密钥或公钥
		Key key = null;
		switch (cipherAlgorithm.getKeyAlgorithm()) {
			case DES:
			case DESede:
			case AES:
			case IDEA:
			{
				key = new SecretKeySpec(byteKey, cipherAlgorithm.getKeyAlgorithm().name());
				break;
			}
			case RSA:
			case DSA:
			case ElGamal:
			{
				try {
					// 尝试获取私钥
					PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
							byteKey);
					key = KeyFactory.getInstance(cipherAlgorithm.getKeyAlgorithm().name())
							.generatePrivate(pkcs8EncodedKeySpec);
					break;
				} catch (Exception e) {
					try {
						// 尝试获取公钥
						X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
								byteKey);
						key = KeyFactory.getInstance(cipherAlgorithm.getKeyAlgorithm().name())
								.generatePublic(x509EncodedKeySpec);
						break;
					} catch (Exception e2) {
						break;
					}
				}
			}
			default:
				break;
		}
		return decrypt(cipherAlgorithm, key, byteData);
	}
	/**
	 * 解密，非对称加密一般来说是公钥加密，私钥解密
	 * @param cipherAlgorithm 加密算法
	 * @param key 钥匙
	 * @param byteData 待解密数据
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decrypt(EnumCipherAlgorithm cipherAlgorithm, Key key, byte[] byteData) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String algorithm = key.getAlgorithm();
		if(cipherAlgorithm != null){
			algorithm = cipherAlgorithm.getValue();
		}
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(byteData);
	}

	/**
	 * JS、JAVA通用：DES加密
	 * @param key
	 * @param plaintext
	 * @return
	 */
	public static String encodeDES(String key,String plaintext){
		try{
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), EnumKeyAlgorithm.DES.name());
			//现在，获取数据并加密
            byte[] e = encrypt(EnumCipherAlgorithm.DES_ECB_PKCS5Padding,keyspec.getEncoded(),plaintext.getBytes());
            //现在，获取数据并编码
//			byte[] temp = Base64.encodeBase64(e);
			byte[] temp = new BASE64Encoder().encode(e).getBytes();
			return IOUtils.toString(temp,"UTF-8");
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * JS、JAVA通用：DESede加密
	 * @param key
	 * @param plaintext
	 * @return
	 */
	public static String encodeDESede(String key,String plaintext){
		try{
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), EnumKeyAlgorithm.DESede.name());
			//现在，获取数据并加密
			byte[] e = encrypt(EnumCipherAlgorithm.DESede_ECB_PKCS5Padding,keyspec.getEncoded(),plaintext.getBytes());
			//现在，获取数据并编码
//			byte[] temp = Base64.encodeBase64(e);
			byte[] temp = new BASE64Encoder().encode(e).getBytes();
			return IOUtils.toString(temp,"UTF-8");
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * JS、JAVA通用：AES加密
	 * @param key
	 * @param plaintext
	 * @return
	 */
	public static String encodeAES(String key,String plaintext){
		try{
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), EnumKeyAlgorithm.AES.name());
			//现在，获取数据并加密
			byte[] e = encrypt(EnumCipherAlgorithm.AES_ECB_PKCS5Padding,keyspec.getEncoded(),plaintext.getBytes());
			//现在，获取数据并编码
//			byte[] temp = Base64.encodeBase64(e);
			byte[] temp = new BASE64Encoder().encode(e).getBytes();
			return IOUtils.toString(temp,"UTF-8");
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * JS、JAVA通用：DES解密
	 * @param key
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public static String decodeDES(String key,String ciphertext) {
        try {
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), EnumKeyAlgorithm.DES.name());
            // 真正开始解码操作
//            byte[] temp = Base64.decodeBase64(ciphertext);
			byte[] temp = new BASE64Decoder().decodeBuffer(ciphertext);
            // 真正开始解密操作
            byte[] e = decrypt(EnumCipherAlgorithm.DES_ECB_PKCS5Padding,keyspec.getEncoded(),temp);
            return IOUtils.toString(e,"UTF-8");
        }catch(Throwable e){
            e.printStackTrace();
            return null;
        }
	}

	/**
	 * JS、JAVA通用：DESede解密
	 * @param key
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public static String decodeDESede(String key,String ciphertext) {
		try {
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), EnumKeyAlgorithm.DESede.name());
			// 真正开始解码操作
//			byte[] temp = Base64.decodeBase64(ciphertext);
			byte[] temp = new BASE64Decoder().decodeBuffer(ciphertext);
			// 真正开始解密操作
			byte[] e = decrypt(EnumCipherAlgorithm.DESede_ECB_PKCS5Padding,keyspec.getEncoded(),temp);
			return IOUtils.toString(e,"UTF-8");
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * JS、JAVA通用：AES解密
	 * @param key
	 * @param ciphertext
	 * @return
	 * @throws Exception
	 */
	public static String decodeAES(String key,String ciphertext) {
		try {
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), EnumKeyAlgorithm.AES.name());
			// 真正开始解码操作
//			byte[] temp = Base64.decodeBase64(ciphertext);
			byte[] temp = new BASE64Decoder().decodeBuffer(ciphertext);
			// 真正开始解密操作
			byte[] e = decrypt(EnumCipherAlgorithm.AES_ECB_PKCS5Padding,keyspec.getEncoded(),temp);
			return IOUtils.toString(e,"UTF-8");
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从字符串中加载公钥
	 * @return
	 * @throws Exception 加载公钥时产生的异常
	 */
	private static RSAPublicKey loadPublicKeyByStr() throws Exception {
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7rS78PJLTDLcHmF7bSUteQZB7joIyuCx3Z8T5A+KPbFQFPZmXMigDhM4vbzcfrUwcKq+c7X+wQPJ3Poi6VpCGnRbK8ts6+PIH8klS/UT/+LS9V+eEA0fBlD1MwQemJIYUhFbAvlGJ4IBubo9qjOaHJCPlBI93p3WMyp3N4GoONwIDAQAB";
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(EnumCipherAlgorithm.RSA_ECB_PKCS1Padding.getKeyAlgorithm().name());
//			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
			byte[] temp = new BASE64Decoder().decodeBuffer(publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(temp);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从字符串中加载私钥
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey loadPrivateKeyByStr() throws Exception {
		String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALutLvw8ktMMtweYXttJS15BkHuOgjK4LHdnxPkD4o9sVAU9mZcyKAOEzi9vNx+tTBwqr5ztf7BA8nc+iLpWkIadFsry2zr48gfySVL9RP/4tL1X54QDR8GUPUzBB6YkhhSEVsC+UYnggG5uj2qM5ockI+UEj3endYzKnc3gag43AgMBAAECgYA427QDaRqWZCDDZU8/okn6KWTrefZKBXA7UK3lP18RUqF14P66RtDGmCKbTldl+mu3kNsZcP6hWFvc8o4b3gP0r5nlW+12Xp6qwg7Ayx7QnvujytnZKu3ijRQhYTXKa9EDbUnPQSMdvBmdnij+QwXGxCiQr90wXslkawNB/GfjIQJBAOKOC4Mi4NWhZptNTZE+4YQcdwM7d8z3PYqv9yGvgym9aPz6EfMX9R4RMBILIjSlNAuRtb+O+zJmfcLWr4Kr7YcCQQDUEZLq4YRjiXJpPCsL+PiQamPF64w/TPNzSF/NzBr3Brkj7/iLk/QANy+3oIbaJMFf7qIEFbf8nGuPyEq8qoXRAkEAjKorRbG7LYk3/wchOSR0uyU9U7lxqcZ85IZbCAREiP78l83gpTHj1FZRpXJaO5uzU9eVpClvmByAyx+m+5gqMwJBAIUpg9d5RGg8JltuLJmX/HyyUXQ2NBqLd1MsXvwa7dOvpRGr3aXHga+g95WWdxcDfWl/rrxh5uX4UpI2creFXAECQQDZVivHFIE1oU7ayXvICgNp72gmwZPWoKyY8coJ7lw200YYMRfP/ZaYDh1dSCsRAQW7RCyibyxNtikgHkwlvp6b";
		try {
//			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
			byte[] temp = new BASE64Decoder().decodeBuffer(privateKey);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(temp);
			KeyFactory keyFactory = KeyFactory.getInstance(EnumCipherAlgorithm.RSA_ECB_PKCS1Padding.getKeyAlgorithm().name());
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 公钥加密过程
	 * @param plainTextData 明文数据
	 * @return
	 * @throws Exception 加密过程中的异常信息
	 */
	public static String encryptRSA(String plainTextData) throws Exception {
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance(EnumCipherAlgorithm.RSA_ECB_PKCS1Padding.getKeyAlgorithm().name());
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, loadPublicKeyByStr());
			byte[] byteContent = plainTextData.getBytes("utf-8");
			byte[] output = cipher.doFinal(byteContent);

//			return new String(Base64.encodeBase64(output));
			byte[] temp = new BASE64Encoder().encode(output).getBytes();
			return new String(temp);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 私钥解密过程
	 * @param cipherData 密文数据
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static String decryptRSA(String cipherData) throws Exception {
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance(EnumCipherAlgorithm.RSA_ECB_PKCS1Padding.getKeyAlgorithm().name());
			cipher.init(Cipher.DECRYPT_MODE, loadPrivateKeyByStr());
//			byte[] byteContent = Base64.decodeBase64(cipherData.getBytes());
			byte[] byteContent = new BASE64Decoder().decodeBuffer(cipherData);

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteContent.length; i += 128) {
				byte[] subarray = ArrayUtils.subarray(byteContent, i, i + 128);
				byte[] doFinal = cipher.doFinal(subarray);
				sb.append(new String(doFinal, "utf-8"));
			}
			// byte[] output = cipher.doFinal(byteContent);
			String strDate = sb.toString();
			return strDate;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}
}
