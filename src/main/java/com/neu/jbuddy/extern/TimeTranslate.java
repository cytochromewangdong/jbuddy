package com.neu.jbuddy.extern;

import java.util.Calendar;

import com.neu.jbuddy.basic.convert.BasicTypeTranslate;


public class TimeTranslate extends BasicTypeTranslate {

	public String getTypeName() {
		return "Time";
	}

	public boolean isType(String orignHead) {
		return orignHead.endsWith(".Time") || orignHead.endsWith(".time");
	}

	public Object convert(String value) {
		if ("null".equalsIgnoreCase(value)) {
			return null;
		}
		int intValue = Integer.parseInt(value);
		int hour = 0, minute = 0, second = 0;
		Calendar calendar = Calendar.getInstance();
		if (value.length() <= 4) {
			hour = intValue / 100;
			minute = intValue % 100;
		} else {
			hour = intValue / 10000;
			minute = intValue % 10000 / 100;
			second = intValue % 100;
		}
		calendar.set(1970, 0, 1, hour, minute, second);
		return calendar.getTime();
	}

	public String getPureHead(String orignHead) {
		if (orignHead == null) {
			return null;
		}
		if (orignHead.endsWith(".Time") || orignHead.endsWith(".time")) {
			orignHead = orignHead.substring(0, orignHead.length()
					- ".time".length());
		}
		return orignHead;
	}

	public int getLevel() {
		return 1;
	}

}