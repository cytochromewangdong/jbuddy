package com.neu.jbuddy.comp;

import java.util.Comparator;
import java.util.Map;

import com.neu.jbuddy.basic.BuddyException;
import com.neu.jbuddy.database.Column;
import com.neu.jbuddy.database.TableDesc;
import com.neu.jbuddy.log.Logger;


public class MapComparator extends BasicComparator
{

    private String[] keys = null;

    public MapComparator()
    {
    }

    public MapComparator(String[] compareKeys)
    {
        keys = compareKeys;
    }

    public MapComparator(Map map)
    {
        keys = (String[]) map.keySet().toArray(new String[0]);
    }

    public int compare(Object arg0, Object arg1)
    {
        int result = super.compare(arg0, arg1);
        if (result != EQUALE)
        {
            return result;
        }
        if (arg0 == null || arg1 == null)
        {
            return EQUALE;
        }
        if (arg0 != null && !(arg0 instanceof Map))
        {
            throw new BuddyException("Type is not Map");

        }
        if (arg1 != null && !(arg1 instanceof Map))
        {
            throw new BuddyException("Type is not Map");

        }
        if (keys == null)
        {
            keys = (String[]) ((Map) arg0).keySet().toArray(new String[0]);
        }
        Map srcMap = (Map) arg0;
        Map aimMap = (Map) arg1;
        Map<String, Column> typeStore = this.getTypeStore();
        for (String key : keys)
        {
            Object o1 = srcMap.get(key);
            Object o2 = aimMap.get(key);
            Comparator comp = null;
            if (typeStore != null)
            {
                Column column = typeStore.get(key);

                if (TableDesc.isNumber(column.getDataType()))
                {
                    comp = ComparatorFactory.getNumberComparator();
                }
            }
            if (comp == null)
            {
                if ((o1 instanceof String || o1 == null) && o2 != null)
                {
                    comp = ComparatorFactory.getComparator(o2);
                }
                else
                {
                    comp = ComparatorFactory.getComparator(o1);
                }

            }
            result = comp.compare(o1, o2);
            if (result != EQUALE)
            {
                if (!this.sortFlag)
                {
                    // comp = ComparatorFactory.getComparator(o1);
                    Logger.printMap(srcMap, aimMap);
                    comp.compare(o1, o2);
                    StringBuilder outputInfor = new StringBuilder(
                            "-------------------------------");
                    outputInfor.append(key)
                            .append("-------------------------------")
                            .append("\n");
                    outputInfor.append(o1 == null || o1.toString().isEmpty()? "null/Empty" : o1).append("\n");
                    outputInfor.append(o2 == null || o2.toString().isEmpty()? "null/Empty" : o2).append("\n");
                    // System.err.println(outputInfor);
                    Logger.printError(outputInfor);
                }

                return result;
            }
            else
            {
                continue;
            }
        }
        return EQUALE;
    }

    /**
     * @param keys The keys to set.
     */
    public void setKeys(String[] keys)
    {
        this.keys = keys;
    }

}
