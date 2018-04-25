package com.cff.baidupcs.util;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RsaUtilOld {
	public static final String CHARSET = "UTF-8";
	public static final String RSA_ALGORITHM = "RSA";
	// 密钥长度1024对应的加解密最大长度
	public static final int MAX_ENCRYPT_BLOCK = 117;
	public static final int MAX_DECRYPT_BLOCK = 128;

	static {

		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {

			System.out.println("security provider BC not found");

			Security.addProvider(new BouncyCastleProvider());

		}

	}

	/**
	 * 得到公钥
	 * 
	 * @param publicKey
	 *            密钥字符串（经过base64编码）
	 * @param publicExponent
	 * @throws NoSuchProviderException 
	 * @throws Exception
	 */
	public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
		byte[] mBytes = CodeUtil.hexString2ByteArr(publicKey);
		BigInteger m = new BigInteger(1, mBytes);
		BigInteger e = BigInteger.valueOf(0x10001);

		// 恢复公钥
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM, "BC");
		RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(new RSAPublicKeySpec(m, e));
		return key;
	}

	public static void testRsa(String data, String pubKey) {
		long exponent = 0x10001;
		BigInteger rsaKey = new BigInteger(CodeUtil.hexString2ByteArr(pubKey));

	}

	/**
	 * 得到私钥
	 * 
	 * @param privateKey
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
		// //通过PKCS#8编码的Key指令获得私钥对象
		// KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		// PKCS8EncodedKeySpec pkcs8KeySpec = new
		// PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
		// RSAPrivateKey key = (RSAPrivateKey)
		// keyFactory.generatePrivate(pkcs8KeySpec);

		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance(RSA_ALGORITHM, "BC");
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}
		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(Base64.getDecoder().decode(privateKey)),
				BigInteger.valueOf(0x10001));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String publicKey) throws Exception {
		RSAPublicKey pubKey = getPublicKey(publicKey);
		return CodeUtil.byteArr2HexString(encrypt(data.getBytes(), pubKey));
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, PublicKey key) throws Exception {
		System.out.println(CodeUtil.byteArr2HexString(data));
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM, "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 */

	public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM, "BC");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] res = cipher.doFinal(CodeUtil.hexString2ByteArr(data));
			System.out.println(CodeUtil.byteArr2HexString(res));
			return new String(res);
		} catch (Exception e) {
			throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
		}
	}

	public static void main(String args[]) throws Exception {
		String prikey = "MIICXAIBAAKBgQCuR7BNOlWl/avGEqQm2ESEvLHCnGO7rDNUShu5TZMHcubiAc8r"
				+ "ObW27e0czLteTc5xO4fG3YjD277jofviIHI/AeKqge2Ul8j/sF/1Sj6YKnbWgrCq"
				+ "vGDb+dGoJD/iki5D3V35wllEIUe79HF+XtjUwb1TRN0ajzW2MdgKtFqbxwIDAQAB"
				+ "AoGAW0CoHFe9/tLq/SRHlRtKDSJsBRUz11Fb8vd2urjWkmDkaVQ/MEfgUK8Vpy2/"
				+ "saoVvQ5JkqPud3b45WGsbINGrb8saugZ1h5huDbuxVXKDj1ZWyJPkmxHLUK2+7iL"
				+ "5c7F7+v2C+n6polIgMV9SbLXD6YIXUJ+GengWQffhTRE7WECQQDj/g5x7Rj5vc7X"
				+ "o3i0SQmyN4RcxxOWfiLe5OUASKM2UPVBQKI3CugkmiTaXTi7auuG3I4GVPRHVHw9"
				+ "y/Ekz7J3AkEAw7B5+uI60MwcDMeGoXAMAEYe/s7LhyBICarY6cNwySb46B7OHEUz"
				+ "ooFV2qx31I6ivpMRwCqrRKXEvjPEAfPlMQJAGrXi/1nluSyRlRXjyEteRXDXov73"
				+ "voPclfx/D79yz6RAd3qZBpXSiKc+dg7B3MM0AMLKKNe/HrQ5Mgw4njVvFQJAPKvX"
				+ "ddh0Qc42mCO4cw8JOYCEFZ5J7fAtRYoJzJhCvKrvmxAJ+SvfcW/GDZFRab57aLiy"
				+ "VTElfpgiopHsIGrc0QJBAMliJywM9BNYn9Q4aqKN/dR22W/gctfa6bxU1m9SfJ5t"
				+ "5G8MR8HsB/9Cafv8f+KnFzp2SncEu0zuFc/S8n5X5v0=";

		String text = "123456";
		String pubkey = "B3C61EBBA4659C4CE3639287EE871F1F48F7930EA977991C7AFE3CC442FEA49643212E7D570C853F368065CC57A2014666DA8AE7D493FD47D171C0D894EEE3ED7F99F6798B7FFD7B5873227038AD23E3197631A8CB642213B9F27D4901AB0D92BFA27542AE890855396ED92775255C977F5C302F1E7ED4B1E369C12CB6B1822F";
		String endata = encrypt(text, pubkey);
		System.out.println(endata);

		String data = "89ee5aa0c0e0901226c7641c0fd726c78a9c693bb908e610dcbcde262aba1d079e90827f70518dde395fdaec2bc3730d88368ca66d4164189a99059115afd72f3d0fa54dcd1b5e655264f8549978a56de8cb6f7b840b89cadb4364fd2783ba653b6efc6dd8aa98c2f470e6c0985b3b3421dce3f266843b6d7bb8918ded16a80d";
		RSAPrivateKey privateKey = getPrivateKey(prikey);
		System.out.println(privateDecrypt(data, privateKey));
	}
}
