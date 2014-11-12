package com.neu.jbuddy.basic.read;

public interface KeywordReader extends Reader {
	String KEY_PREFIX = "&";

	String EQUAL_MARK = "=";
	
	short MAX_COLUMN = 255;
	String KEY_GROUP ="group";
	String getKeyword();

	boolean iskeyReader(String key);
}
