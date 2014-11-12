package com.neu.jbuddy.basic.convert;

import java.util.List;
import java.util.Map;

import com.neu.jbuddy.basic.container.Element;


public class MapTranslate extends BasicTypeTranslate {

	public String getTypeName() {
		return "Map";
	}

	public boolean isType(String orignHead) {
		return orignHead.endsWith(".Map") || orignHead.endsWith(".map");
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
		if (orignHead.endsWith(".Map") || orignHead.endsWith(".map")) {
			orignHead = orignHead.substring(0, orignHead.length()
					- ".map".length());
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
					List<Map> listData = each.getList();
					if (listData != null && listData.size() != 0) {
						if (listData.size() == 1) {
							map.put(head, listData.get(0));
						} else {
							map.put(head, listData);
						}
					}
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