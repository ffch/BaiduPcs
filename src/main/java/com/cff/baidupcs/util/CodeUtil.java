package com.cff.baidupcs.util;

public class CodeUtil {
	/**
	 * 字节数组转换为十六进制字符串
	 * 
	 * @param bytearr
	 *            字节数组
	 * @return 十六进制字符�?
	 */
	public static String byteArr2HexString(byte[] bytearr) {
		if (bytearr == null) {
			return "null";
		}
		StringBuffer sb = new StringBuffer();

		for (int k = 0; k < bytearr.length; k++) {
			if ((bytearr[k] & 0xFF) < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(bytearr[k] & 0xFF, 16));
		}
		return sb.toString();
	}

	/**
	 * 十六进制字符串转换为字节数组
	 * 
	 * @param hexstring
	 *            十六进制字符�?
	 * @return 字节数组
	 */
	public static byte[] hexString2ByteArr(String hexstring) {
		if ((hexstring == null) || (hexstring.length() % 2 != 0)) {
			return new byte[0];
		}

		byte[] dest = new byte[hexstring.length() / 2];

		for (int i = 0; i < dest.length; i++) {
			String val = hexstring.substring(2 * i, 2 * i + 2);
			dest[i] = (byte) Integer.parseInt(val, 16);
		}
		return dest;
	}
	
	public static void main(){
		
	}
}
