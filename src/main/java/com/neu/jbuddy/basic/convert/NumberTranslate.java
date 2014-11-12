package com.neu.jbuddy.basic.convert;

import com.neu.jbuddy.utils.StringUtils;

public class NumberTranslate extends BasicTypeTranslate {

	public String getTypeName() {
		return "Number";
	}

	public boolean isType(String orignHead) {
		return orignHead.endsWith(".Number") || orignHead.endsWith(".number");
	}

	public Object convert(String value) {
		if ("null".equalsIgnoreCase(value)) {
			return null;
		}
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if (value.indexOf('.') >= 0) {
			return Double.parseDouble(value);
		} else {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				return Long.parseLong(value);
			}
		}

	}

	public String getPureHead(String orignHead) {
		if (orignHead == null) {
			return null;
		}
		if (orignHead.endsWith(".Number") || orignHead.endsWith(".number")) {
			orignHead = orignHead.substring(0,
					orignHead.length() - ".number".length());
		}
		return orignHead;
	}

	public int getLevel() {
		return 1;
	}

}