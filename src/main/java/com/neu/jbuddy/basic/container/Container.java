package com.neu.jbuddy.basic.container;

import java.util.List;
import java.util.Map;

public interface Container {
	void add(String key, Object obj);

	List getList(String key);

	Map getMap(String key);

	Object get(String key);
	
	int getObjectCount();
	
	List toList();
}
