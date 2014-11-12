package com.neu.jbuddy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.neu.jbuddy.log.Logger;


public class CompareMap {

	static SimpleDateFormat D_FORMAT = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	
	public static boolean compareArrayList(ArrayList list1, ArrayList list2) {
		int list1_size = list1.size();
		int list2_size = list2.size();
		if (list1_size != list2_size) {
			return false;
		}

		if (list1_size == 0) {
			return true;
		}
		if (list1.get(0) instanceof Map) {
			return compareArrayListMap(list1, list2);
		} else {
			return compareArrayListObject(list1, list2);
		}
	}

	public static boolean compareArrayListSort(ArrayList list1, ArrayList list2) {

		int list1_size = list1.size();
		int list2_size = list2.size();

		if (list1_size != list2_size) {
			return false;
		}

		if (list1_size == 0) {
			return true;
		}
		if (list1.get(0) instanceof Map) {
			return compareArrayListMapSort(list1, list2);
		} else {
			return compareArrayListObject(list1, list2);
		}
	}

	public static boolean compareArrayListObject(ArrayList list1,
			ArrayList list2) {

		int list1_size = list1.size();

		for (int i = 0; i < list1_size; i++) {
			Object val1, val2;
			if (list1.get(i) == null) {
				if (list2.get(i) == null) {
					continue;
				} else {
					if ("".equals(list2.get(i).toString().trim())) {
						continue;
					} else {
						return false;
					}
				}
			}
			if (list2.get(i) == null) {
				if ("".equals(list1.get(i).toString().trim())) {
					continue;
				} else {
					return false;
				}
			}
			val1 = list1.get(i);
			val2 = list2.get(i);
			if (val1 instanceof Date) {
				Date dt = (Date) val1;
				val1 = D_FORMAT.format(dt);
			}
			if (val2 instanceof Date) {
				Date dt = (Date) val2;
				val2 = D_FORMAT.format(dt);
			}
			if (!val2.toString().trim().equals(val2.toString().trim())) {
				return false;
			}
		}
		return true;
	}

	public static boolean compareArrayListMap(ArrayList list1,
			ArrayList list2) {


		for (int i = 0; i < list1.size(); i++) {
			if (!(list1.get(i) instanceof Map)) {
				throw new RuntimeException("Map");
			}
		}

		for (int i = 0; i < list2.size(); i++) {
			if (!(list2.get(i) instanceof Map)) {
				throw new RuntimeException("Map");
			}
		}
		for (int i = 0; i < list1.size(); i++) {
			boolean flag = false;
			for (int j = 0; j < list2.size(); j++) {
				if (compareMap((Map) list1.get(i), (Map) list2
						.get(j))) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				Logger.println("Error data row:" + list1.get(i));
				return false;
			}
		}
		for (int i = 0; i < list2.size(); i++) {
			boolean flag = false;
			for (int j = 0; j < list1.size(); j++) {
				if (compareMap((Map) list1.get(j), (Map) list2
						.get(i))) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				Logger.println("not exsits result row:" + list1.get(i));
				return false;
			}
		}
		return true;
	}

	public static boolean compareArrayListMapSort(ArrayList list1,
			ArrayList list2, String[] headStr) {


		for (int i = 0; i < list1.size(); i++) {
			if (!(list1.get(i) instanceof Map)) {
				throw new RuntimeException("Map");
			}
		}

		for (int i = 0; i < list2.size(); i++) {
			if (!(list2.get(i) instanceof Map)) {
				throw new RuntimeException("Map");
			}
		}
		for (int i = 0; i < list1.size(); i++) {

			if (!compareMap((Map) list1.get(i), (Map) list2.get(i),
					true)) {
				Logger.println("CompareDealer Error, INDEX:" + i);
				WLog((Map) list1.get(i), (Map) list2.get(i), headStr);
				return false;
			}

		}

		return true;
	}

	public static boolean compareArrayListMapSort(ArrayList list1,
			ArrayList list2) {
		return compareArrayListMapSort(list1, list2, null);
	}

	public static boolean compareArrayListSpecial(ArrayList list1,
			ArrayList list2) {

		int list1_size = list1.size();
		int list2_size = list2.size();

		if (list1_size != list2_size) {
			return false;
		}

		if (list1_size == 0) {
			return true;
		}
		if (list1.get(0) instanceof Map) {
			Map first = (Map) list1.get(0);
			String[] keyNameArray = (String[]) first.keySet().toArray(
					new String[0]);
			list1 = SortObject.Sort(list1, keyNameArray);
			list2 = SortObject.Sort(list2, keyNameArray);
			return compareArrayListSpecial(list1, list2, keyNameArray);
		} else {
			return compareArrayListObject(list1, list2);
		}
	}

	public static boolean compareArrayListSpecial(ArrayList list1,
			ArrayList list2, String[] keyNameArray) {

		int list1_size = list1.size();
		int list2_size = list2.size();

		if (list1_size != list2_size) {
			return false;
		}

		if (list1_size == 0) {
			return true;
		}
		if (list1.get(0) instanceof Map) {
			Map first = (Map) list1.get(0);
			list1 = SortObject.Sort(list1, keyNameArray);
			list2 = SortObject.Sort(list2, keyNameArray);
			return compareArrayListMapSort(list1, list2, keyNameArray);
		} else {
			return compareArrayListObject(list1, list2);
		}
	}

	public static boolean compareMap(Map dataMap, Map resultMap) {
		String[] keyNameArray = (String[]) dataMap.keySet().toArray(
				new String[0]);
		return compareMap(dataMap, resultMap, keyNameArray);

	}

	public static boolean compareMap(Map dataMap, Map resultMap,
			boolean flag) {
		String[] keyNameArray = (String[]) dataMap.keySet().toArray(
				new String[0]);
		return compareMap(dataMap, resultMap, keyNameArray, flag);

	}

	public static void WLog(Map dataMap, Map resultMap,
			String[] keyNameArray) {
		if (keyNameArray == null) {
			keyNameArray = (String[]) dataMap.keySet().toArray(new String[0]);
		}
		StringBuffer title = new StringBuffer();
		StringBuffer souteData = new StringBuffer();
		StringBuffer resultData = new StringBuffer();
		StringBuffer allStr = new StringBuffer("\n");
		for (int i = 0; i < keyNameArray.length; i++) {
			title.append(keyNameArray[i]).append("\t");
			souteData.append(dataMap.get(keyNameArray[i])).append("\t");
			resultData.append(resultMap.get(keyNameArray[i])).append("\t");
		}
		allStr.append(title).append("\n");
		allStr.append(souteData).append("\n");
		allStr.append("").append("\n");
		allStr.append(resultData).append("\n");
		allStr.append("").append("\n");
		Logger.println(allStr);

	}

	public static void WLog(ArrayList dataList, ArrayList resultList,
			String[] keyNameArray) {
		if (dataList.size() > 0) {
			if (keyNameArray == null) {
				keyNameArray = (String[]) ((Map) dataList.get(0)).keySet()
						.toArray(new String[0]);
			}
			StringBuffer title = new StringBuffer();
			StringBuffer souteData = new StringBuffer();
			StringBuffer resultData = new StringBuffer();
			StringBuffer allStr = new StringBuffer("\n");
			for (int i = 0; i < keyNameArray.length; i++) {
				title.append(keyNameArray[i]).append("\t");
			}
			allStr.append(title);
			allStr.append("\n");
			for (int j = 0; j < dataList.size(); j++) {
				for (int i = 0; i < keyNameArray.length; i++) {
					souteData.append(
							((Map) dataList.get(j)).get(keyNameArray[i]))
							.append("\t");
				}
				souteData.append("\n");
			}

			allStr.append(souteData).append("\n");
			allStr.append("").append("\n");
			for (int j = 0; j < resultList.size(); j++) {
				for (int i = 0; i < keyNameArray.length; i++) {
					resultData.append(
							((Map) resultList.get(j)).get(keyNameArray[i]))
							.append("\t");
				}
				resultData.append("\n");
			}
			allStr.append(resultData).append("\n");
			allStr.append("").append("\n");
			Logger.println(allStr);
		}
	}

	public static boolean compareMap(Map dataMap, Map resultMap,
			String[] keyNameArray) {
		return compareMap(dataMap, resultMap, keyNameArray, false);
	}

	public static boolean compareMap(Map dataMap, Map resultMap,
			String[] keyNameArray, boolean flag) {
		for (int i = 0; i < keyNameArray.length; i++) {
			String keyName = (String) keyNameArray[i];
			// �܂܂Ȃ�
			if (!resultMap.containsKey(keyName)) {
				if (dataMap.get(keyName) != null) {// TODO
					if (flag) {
						Logger.println("CompareDealer Error, key:" + keyName);
						Logger.println("CompareDealer Error, Data:"
								+ dataMap.get(keyName));
						Logger.println("CompareDealer Error, Result:"
								+ resultMap.get(keyName));
						return false;
					} else {
						continue;
					}
				}
			}

			if (dataMap.get(keyName) == null) {
				if (resultMap.get(keyName) == null) {
					continue;
				} else {
					if (flag) {
						Logger.println("CompareDealer Error, key:" + keyName);
						Logger.println("CompareDealer Error, Data:"
								+ dataMap.get(keyName));
						Logger.println("CompareDealer Error, Result:"
								+ resultMap.get(keyName));
					}
					return false;
				}
			}

			if (resultMap.get(keyName) == null) {
				if ("".equals(dataMap.get(keyName).toString().trim())) {
					continue;
				} else {
					if (flag) {
						Logger.println("CompareDealer Error, key:" + keyName);
						Logger.println("CompareDealer Error, Data:"
								+ dataMap.get(keyName));
						Logger.println("CompareDealer Error, Result:"
								+ resultMap.get(keyName));
					}
					return false;
				}
			}

			if (dataMap.get(keyName) instanceof Map) {
				Map valObj = (Map) dataMap.get(keyName);
				Object resultObj = resultMap.get(keyName);
				if (!(resultObj instanceof Map)
						|| !compareMap(valObj, (Map) resultObj)) {
					if (flag) {
						Logger.println("CompareDealer Error, key:" + keyName);
						Logger.println("CompareDealer Error, Data:"
								+ dataMap.get(keyName));
						Logger.println("CompareDealer Error, Result:"
								+ resultMap.get(keyName));
					}
					return false;
				} else {
					continue;
				}
			}

			if (dataMap.get(keyName) instanceof ArrayList) {
				ArrayList valObj = (ArrayList) dataMap.get(keyName);
				Object resultObj = resultMap.get(keyName);
				if (!(resultObj instanceof ArrayList)
						|| !compareArrayList(valObj, (ArrayList) resultObj)) {
					if (flag) {
						Logger.println("CompareDealer Error, key:" + keyName);
						Logger.println("CompareDealer Error, Data:"
								+ dataMap.get(keyName));
						Logger.println("CompareDealer Error, Result:"
								+ resultMap.get(keyName));
					}
					return false;
				} else {
					continue;
				}
			}

			Object valObj = dataMap.get(keyName);
			Object resultObj = resultMap.get(keyName);
			if (valObj instanceof Date) {
				Date dt = (Date) valObj;
				valObj = D_FORMAT.format(dt);
			}
			if (resultObj instanceof Date) {
				Date dt = (Date) resultObj;
				resultObj = D_FORMAT.format(dt);
			}
			if (valObj.toString().trim().equals(resultObj.toString().trim())) {
				continue;
			} else {
				if (flag) {
					Logger.println("CompareDealer Error, key:" + keyName);
					Logger.println("CompareDealer Error, Data:"
							+ dataMap.get(keyName));
					Logger.println("CompareDealer Error, Result:"
							+ resultMap.get(keyName));
				}
				return false;
			}

		}

		return true;
	}

	/*public static void main(String[] args) throws Exception {

		Map dataMap = null;
		Map resultMap = null;
		ArrayList list1 = null;
		ArrayList list2 = null;
		try {
			dataMap = new Map();
			resultMap = new Map();
			list1 = new ArrayList();
			list2 = new ArrayList();
			CompareMap c = new CompareMap();
			list1.add("a");
			list2.add("a");
			list1.add("b");
			list2.add("b");
			list1.add("c");
			list2.add("c");
			// if(c.comparearraylist(list1,list2))
			// Logger.println("success");
			// else
			// Logger.println("failure");
			dataMap.put("a", list1);
			// dataMap.put("c",list2);
			// dataMap.put("d","d");
			resultMap.put("a", list2);
			// resultMap.put("c",list2);
			// resultMap.put("d","d");
			// resultMap.put("f","d");
			// dataMap.put("a","b");
			// dataMap.put("c","d");
			// resultMap.put("a","b");
			// resultMap.put("c","e");
			if (c.compareMap(dataMap, resultMap))
				Logger.println("success");
			else
				Logger.println("failure");
		} catch (Exception e) {
			e.printStackTrace();
			Logger.println("system failure");
		}

	}*/
}