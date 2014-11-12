package com.neu.jbuddy.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.neu.jbuddy.comp.ComparatorFactory;
import com.neu.jbuddy.database.Column;


public class SortUtils {

	public static void SortMapList(List<Map> list,  Map<String, Column> typeStore, String[] keys) {
		if (list == null || list.size() == 0) {
			return;
		}
		Comparator com = ComparatorFactory
				.getComparatorByName(ComparatorFactory.MAP_KEY,typeStore, true);
		Collections.sort(list, com);
	}

	public static void SortList(List list,  Map<String, Column> typeStore) {
		if (list == null || list.size() == 0) {
			return;
		}
		Comparator com = ComparatorFactory.getComparator(list.get(0), typeStore, true);
		Collections.sort(list, com);
	}

}
