package com.neu.jbuddy.basic.convert;

import java.util.List;

import com.neu.jbuddy.basic.container.Element;


public abstract class BasicTypeTranslate implements TypeTranslate {
	public void postTranslate(List<Element> list,Element element,String head) {

	}

	public boolean hasPostEvent() {
		return false;
	}
}
