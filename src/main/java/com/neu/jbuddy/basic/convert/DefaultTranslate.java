package com.neu.jbuddy.basic.convert;

public class DefaultTranslate extends BasicTypeTranslate  {

	public String getTypeName() {
		return "String";
	}

	public boolean isType(String orignHead) {
		return true;
	}

	public Object convert(String value) {
		if("null".equalsIgnoreCase(value)) {
			return null;
		}
		return value;
	}

	public String getPureHead(String orignHead) {
		if(orignHead==null) {
			return null;
		}
		if(orignHead.endsWith(".String")||orignHead.endsWith(".string")) {
			orignHead = orignHead.substring(0,orignHead.length() - ".string".length());
		}
		return orignHead;
	}

	public int getLevel() {
		return 0;
	}

}
