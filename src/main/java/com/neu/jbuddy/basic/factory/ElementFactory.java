package com.neu.jbuddy.basic.factory;

import com.neu.jbuddy.basic.container.Element;

public interface ElementFactory {
	Element Create();
	Element Create(int type);
}
