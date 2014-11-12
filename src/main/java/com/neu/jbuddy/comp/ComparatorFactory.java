package com.neu.jbuddy.comp;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.neu.jbuddy.database.Column;


public class ComparatorFactory
{
    public static final String NUMBER_KEY = "java.lang.Number";

    public static final String STRING_KEY = "java.lang.String";

    public static final String DATE_KEY = "java.util.Date";

    public static final String MAP_KEY = "java.util.Map";

    public static final String LIST_KEY = "java.util.list";

    public static final String SQL_TIME_KEY = "java.sql.Time";
    public static final String SQL_DATE_KEY = "java.sql.Date";
    public static final String SQL_TIMESTAMP_KEY = "java.sql.Date";

    private static Map<String, BasicComparator> comparators = new LinkedHashMap<String, BasicComparator>();

    private static Map<String, BasicComparator> bufferMap = new LinkedHashMap<String, BasicComparator>();

    private static BasicComparator basicComparator = new BasicComparator()
    {

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
            if (arg0 == null)
            {
                if ("".equals(arg1))
                {
                    return EQUALE;
                }
                else
                {
                    return SMALLER;
                }
            }
            if (arg1 == null)
            {
                if ("".equals(arg0))
                {
                    return EQUALE;
                }
                else
                {
                    return BIGGER;
                }
            }
            return arg0.toString().compareTo(arg1.toString());
        }

    };

    /**
     * @param comparators The comparators to set.
     */
    public static void setComparatorList(
            Map<String, BasicComparator> comparators)
    {
        ComparatorFactory.comparators.putAll(comparators);
    }

    public static Comparator getComparator(Object o)
    {
        return getComparator(o, null);
    }

    public static Comparator getComparator(Object o,
            Map<String, Column> typeStore)
    {
        return getComparator(o, typeStore, false);
    }

    public static Comparator getComparator(Object o,
            Map<String, Column> typeStore, boolean isForSort)
    {
        if (o == null)
        {
            return basicComparator;
        }
        String className = o.getClass().getName();
        String objectClassName = o.getClass().getName();
        Comparator com = getComparatorByName(className, typeStore, isForSort);
        if (com != null)
        {
            bufferMap.put(objectClassName, comparators.get(className));
            return com;
        }
        for (Class oClass : o.getClass().getInterfaces())
        {
            className = oClass.getName();
            com = getComparatorByName(className, typeStore, isForSort);
            if (com != null)
            {
                bufferMap.put(objectClassName, comparators.get(className));
                return com;
            }
        }
        Class superClass = o.getClass().getSuperclass();
        while (superClass != null && !(superClass.equals(Object.class)))
        {
            className = superClass.getName();
            com = getComparatorByName(className, typeStore, isForSort);
            if (com != null)
            {
                bufferMap.put(objectClassName, comparators.get(className));
                return com;
            }
            superClass = superClass.getSuperclass();
        }

        return basicComparator;
    }

    public static Comparator getComparatorByName(String classString)
    {
        return getComparatorByName(classString, null);
    }

    public static Comparator getComparatorByName(String classString,
            Map<String, Column> typeStore)
    {
        return getComparatorByName(classString, typeStore, false);
    }

    public static Comparator getComparatorByName(String classString,
            Map<String, Column> typeStore, boolean isForSort)
    {
        BasicComparator com = bufferMap.get(classString);
        if (com == null)
        {
            com = comparators.get(classString);
            bufferMap.put(classString, com);
        }
        if (com instanceof MapComparator)
        {
            ((MapComparator) com).setKeys(null);
        }
        if (com != null)
        {
            com.setSortFlag(isForSort);
        }
        if (com != null)
        {
            com.setTypeStore(typeStore);
        }
        return com;
    }

    public static Comparator getNumberComparator()
    {
        return comparators.get(NUMBER_KEY);
    }

    static
    {
        // TIMESTAMP_KEY
        comparators.put(MAP_KEY, new MapComparator());
        comparators.put(SQL_TIME_KEY, new DateComparator(
                BasicComparator.TIME_FORMAT));
        comparators.put(SQL_DATE_KEY, new DateComparator(
                BasicComparator.DATE_FORMAT));
        comparators.put(DATE_KEY, new DateComparator(
                BasicComparator.DATETIME_FORMAT));
        comparators.put(STRING_KEY, new StringComparator());
        comparators.put(LIST_KEY, new ListComparator());
        comparators.put(NUMBER_KEY, new NumberComparator());

    }
}
