package com.neu.jbuddy.basic.convert;

import java.util.ArrayList;
import java.util.List;

import com.neu.jbuddy.basic.container.Element;
import com.neu.jbuddy.extern.TimeTranslate;


public class TypeTranslateFactory {
	private static List<TypeTranslate> translates = new ArrayList<TypeTranslate>();

	private static TypeTranslate defaultTranslate = new DefaultTranslate();

	private static List<Event> postEvents = new ArrayList<Event>();

	private static class Event {
		Element element;

		String head;

		TypeTranslate translate;

		Event(Element element, String head, TypeTranslate translate) {
			this.head = head;
			this.element = element;
			this.translate = translate;
		}
	}

	public static TypeTranslate create(Element element, String orignHead) {
		TypeTranslate ret = defaultTranslate;
		for (TypeTranslate typeTranslate : translates) {
			if (typeTranslate.isType(orignHead)) {
				ret = typeTranslate;
				break;
			}
		}
		if (ret.hasPostEvent()) {
			String head = ret.getPureHead(orignHead);
			postEvents.add(new Event(element, head, ret));
		}
		return ret;
	}

	public static void postAllEvent(List<Element> list) {
		for (Event event : postEvents) {
			event.translate.postTranslate(list, event.element, event.head);
		}
		postEvents.clear();
	}

	/**
	 * @param translates
	 *            The translates to set.
	 */
	public static void setTranslates(List<TypeTranslate> translates) {
		TypeTranslateFactory.translates = translates;
	}

	static {
		translates.add(new DateTranslate());
		translates.add(new TimeTranslate());
		translates.add(new ListTranslate());
		translates.add(new MapTranslate());
		translates.add(new NumberTranslate());
        translates.add(new LongTranslate());
        translates.add(new BooleanTranslate());
	}
}
