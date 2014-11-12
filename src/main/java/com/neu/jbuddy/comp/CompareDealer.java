package com.neu.jbuddy.comp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.neu.jbuddy.database.Column;
import com.neu.jbuddy.log.Logger;
import com.neu.jbuddy.sort.SortUtils;


public class CompareDealer
{
    public static boolean compareList(List list1, List list2)
    {
        if (list1 == null && list2 == null)
        {
            return true;
        }
        if (list1 == null)
        {
            if (list2.size() == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        if (list2 == null)
        {
            if (list1.size() == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        if (list1.get(0) instanceof Map)
        {
            return compareListMap(list1, list2);
        }
        else
        {
            Comparator com = ComparatorFactory.getComparator(list1,null);
            int result = com.compare(list1, list2);
            if (result != BasicComparator.EQUALE)
            {
                return false;
            }
            return true;

        }
    }

    public static boolean compareListMap(List<Map> list1, List<Map> list2)
    {
        String[] keys = null;
        if (list1 != null && list1.size() != 0)
        {
            keys = (String[]) list1.get(0).keySet().toArray(new String[0]);
        }
        return compareListMap(list1, list2, keys);
    }

    public static boolean compareListMap(List<Map> list1, List<Map> list2,
            String[] keys)
    {
        if (list1 == null && list2 == null)
        {
            return true;
        }
        if (list1 == null)
        {
            if (list2.size() == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        if (list2 == null)
        {
            if (list1.size() == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        int result = ComparatorFactory.getComparatorByName(
                ComparatorFactory.LIST_KEY).compare(list1, list2);
        if (result == BasicComparator.EQUALE)
        {
            return true;
        }
        return false;
    }

    public static boolean sortCompareListMap(List<Map> list1, List<Map> list2)
    {
        String[] keys = null;
        if (list1 != null && list1.size() != 0)
        {
            keys = (String[]) list1.get(0).keySet().toArray(new String[0]);
        }
        return sortCompareListMap(list1, list2, keys);
    }

    public static boolean sortCompareListMap(List<Map> list1, List<Map> list2,
            String[] keys)
    {
        return sortCompareListMap(list1, list2, keys, null);
    }

    public static boolean sortCompareListMap(List<Map> list1, List<Map> list2,
            String[] keys, Map<String, Column> typeStore)
    {
        if (list1 == null && list2 == null)
        {
            return true;
        }
        if (list1 == null)
        {
            if (list2.size() == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        if (list2 == null)
        {
            if (list1.size() == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        if(list1.size() != list2.size())
        {
            return false;
        }
        if (keys == null || keys.length == 0)
        {
            keys = (String[]) list1.get(0).keySet().toArray(new String[0]);
        }
        List cpyList1 = new ArrayList();
        List cpyList2 = new ArrayList();
        cpyList1.addAll(list1);
        cpyList2.addAll(list2);
        SortUtils.SortMapList(cpyList1, typeStore, keys);
        SortUtils.SortMapList(cpyList2, typeStore,  keys);
        int result = ComparatorFactory.getComparatorByName(
                ComparatorFactory.LIST_KEY, typeStore).compare(cpyList1, cpyList2);
        if (result == BasicComparator.EQUALE)
        {
            return true;
        }
        return false;
    }

    public static boolean compareMap(Map srcMap, Map desMap)
    {
        if (srcMap == null)
        {
            if (desMap != null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        String[] keys = (String[]) srcMap.keySet().toArray(new String[0]);
        return compareMap(srcMap, desMap, keys);
    }

    public static boolean compareMap(Map srcMap, Map desMap, String[] keys)
    {
        return compareMap(srcMap, desMap, keys, false);
    }

    public static boolean compareMap(Map srcMap, Map desMap, String[] keys,
            boolean isLogRequired)
    {
        MapComparator com = (MapComparator) ComparatorFactory
                .getComparatorByName(ComparatorFactory.MAP_KEY);
        com.setKeys(keys);
        int result = com.compare(srcMap, desMap);
        if (result == BasicComparator.EQUALE)
        {
            return true;
        }
        if (isLogRequired)
        {
            // Logger.printMap(srcMap, desMap, keys);
        }
        return false;
    }

}
