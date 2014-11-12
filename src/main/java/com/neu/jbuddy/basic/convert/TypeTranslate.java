package com.neu.jbuddy.basic.convert;

import java.util.List;

import com.neu.jbuddy.basic.container.Element;


public interface TypeTranslate {
	String getTypeName();

	boolean isType(String orignHead);

	Object convert(String value);

	String getPureHead(String orignHead);

	int getLevel();
	boolean hasPostEvent();
	void postTranslate(List<Element> list,Element element,String head);
}
