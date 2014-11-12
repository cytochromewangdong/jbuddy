
package com.neu.jbuddy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


//import java.util.Map;

public class SortObject
{
    static SimpleDateFormat D_FORMAT = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    public static final int BIGGER = 1;

    public static final int SMALLER = -1;

    public static final int EQUALE = 0;

    public static int compareMap(Map dataMap, Map resultMap,
            String[] keyNameArray)
    {
        for (int i = 0; i < keyNameArray.length; i++)
        {
            String keyName = (String) keyNameArray[i];

            if (resultMap.get(keyName) == null)
            {
                Object dataObj = dataMap.get(keyName);
                if (dataObj != null)
                {
                    if (dataObj instanceof String)
                    {
                        if ("".equals(((String) dataObj).trim()))
                        {
                            continue;
                        }
                    }
                    else
                    {
                        return BIGGER;
                    }
                }
                else
                {
                    continue;
                }
            }

            if (dataMap.get(keyName) == null)
            {
                if (resultMap.get(keyName) == null)
                {
                    continue;
                }
                else
                {
                    Object resultObj = resultMap.get(keyName);
                    if (resultObj != null)
                    {
                        if (resultObj instanceof String)
                        {
                            if ("".equals(((String) resultObj).trim()))
                            {
                                continue;
                            }
                        }
                        else
                        {
                            return SMALLER;
                        }
                    }
                    else
                    {
                        continue;
                    }
                }
            }


            if (dataMap.get(keyName) instanceof Map)
            {
                Map valObj = (Map) dataMap.get(keyName);
                Object resultObj = resultMap.get(keyName);
                if (!(resultObj instanceof Map))
                {
                    throw new RuntimeException(keyName
                            + " Object Type is not right,not Map");
                }
                int ret = compareMap(valObj, (Map) resultObj);
                if (ret == EQUALE)
                {
                    continue;
                }
                else
                {
                    return ret;
                }
            }

            /*
             * if (dataMap.get(keyName) instanceof ArrayList) { ArrayList valObj
             * = (ArrayList) dataMap.get(keyName); Object resultObj =
             * resultMap.get(keyName); if (!(resultObj instanceof ArrayList)){
             * throw new RuntimeException(keyName+" Object Type is not right,not
             * ArrayList"); } int ret = compareArrayList(valObj, (ArrayList)
             * resultObj); if(ret==EQUALE) { continue; }else { return ret; } }
             */

            Object valObj = dataMap.get(keyName);
            Object resultObj = resultMap.get(keyName);
            if (valObj == null && resultObj == null)
            {
                continue;
            }
            else
            {
                if (resultObj == null)
                {
                    return BIGGER;
                }
                if (valObj == null)
                {
                    return SMALLER;
                }
            }
            if (valObj instanceof Date)
            {
                Date dt = (Date) valObj;
                valObj = D_FORMAT.format(dt);
            }
            if (resultObj instanceof Date)
            {
                Date dt = (Date) resultObj;
                resultObj = D_FORMAT.format(dt);
            }
            if (valObj.toString().trim().equals(resultObj.toString().trim()))
            {
                continue;
            }
            else
            {
                return valObj.toString().trim().compareTo(resultObj.toString()) > 0 ? BIGGER
                        : SMALLER;
            }

        }

        return EQUALE;
    }

    public static ArrayList Sort(ArrayList sortObj, String[] keyNameArray)
    {
        ArrayList retList = new ArrayList();
        for (int i = 0; i < sortObj.size(); i++)
        {
            Map curMap = (Map) sortObj.get(i);
            for (int j = 0; j < retList.size(); j++)
            {
                int compRet = compareMap(curMap, (Map) retList.get(j),
                        keyNameArray);
                if (compRet == SMALLER)
                {
                    retList.add(j, curMap);
                    break;
                }
            }
            if (i == retList.size())
            {
                retList.add(curMap);
            }

        }
        return retList;
    }

    /**
     * Method compareMap.
     * @param valObj
     * @param Map
     * @return int
     */
    private static int compareMap(Map dataMap, Map resultMap)
    {
        String[] keyNameArray = (String[]) dataMap.keySet().toArray(
                new String[0]);
        return compareMap(dataMap, resultMap, keyNameArray);
    }
}
