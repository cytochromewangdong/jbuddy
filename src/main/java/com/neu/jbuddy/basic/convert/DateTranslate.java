package com.neu.jbuddy.basic.convert;

import java.util.Calendar;

public class DateTranslate extends BasicTypeTranslate {

	public String getTypeName() {
		return "Date";
	}

	public boolean isType(String orignHead) {
		return orignHead.endsWith(".Date") || orignHead.endsWith(".date");
	}

	public Object convert(String value) {
		if ("null".equalsIgnoreCase(value)) {
			return null;
		}
		int intValue = Integer.parseInt(value);
		// YYYY�̏ꍇ
		if (intValue > 1300 && intValue < 10000) {
			// YYYY0101�̗l�ɂ���
			intValue = intValue * 10000 + 100 + 1;
		}
		// MMdd�̏ꍇ
		if (intValue < 1300) {
			// 2000MMdd�̗l�ɂ���
			intValue = 20000000 + intValue;
		}
		// yyyyMM�̏ꍇ
		if (intValue > 10000 && intValue < 1000000) {
			// yyyyMM01�̗l�ɂ���
			intValue *= 100;
			intValue++;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.set(intValue / 10000, intValue % 10000 / 100 - 1,
				intValue % 100);
		return calendar.getTime();
	}

	public String getPureHead(String orignHead) {
		if (orignHead == null) {
			return null;
		}
		if (orignHead.endsWith(".Date") || orignHead.endsWith(".date")) {
			orignHead = orignHead.substring(0, orignHead.length()
					- ".date".length());
		}
		return orignHead;
	}

	public int getLevel() {
		return 1;
	}

}