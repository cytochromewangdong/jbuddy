package com.neu.jbuddy.utils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static String rightTrim(String value) {
		if (value == null) {
			return null;
		}
		char[] chars = value.toCharArray();
		for (int i = chars.length; i > 0; i--) {
			if (chars[i - 1] != ' ') {
				return new String(chars, 0, i);
			}
		}
		return "";
	}
}
