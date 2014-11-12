package com.neu.jbuddy.basic.convert;

import java.util.List;
import java.util.Map;

import com.neu.jbuddy.basic.container.Element;


public class ListTranslate extends BasicTypeTranslate {

	public String getTypeName() {
		return "List";
	}

	public boolean isType(String orignHead) {
		return orignHead.endsWith(".List") || orignHead.endsWith(".list");
	}

	public Object convert(String value) {
		if ("null".equalsIgnoreCase(value)) {
			return null;
		}
		return value;
	}

	public String getPureHead(String orignHead) {
		if (orignHead == null) {
			return null;
		}
		if (orignHead.endsWith(".List") || orignHead.endsWith(".list")) {
			orignHead = orignHead.substring(0, orignHead.length()
					- ".list".length());
		}
		return orignHead;
	}

	public int getLevel() {
		return 0;
	}

	@Override
	public void postTranslate(List<Element> list, Element element, String head) {
		for (Map map : element.getList()) {
			String key = (String) map.get(head);
			map.put(head, null);
			for (Element each : list) {
				if (each.getName().equals(key)) {
					map.put(head, each.getList());
					break;
				}
			}
		}
	}

	@Override
	public boolean hasPostEvent() {
		return true;
	}

}
