package com.neu.jbuddy.comp;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Map;

import com.neu.jbuddy.database.Column;

public abstract class BasicComparator implements Comparator  {
	protected static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
    protected static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	
	public static final int BIGGER = 1;

	public static final int SMALLER = -1;

	public static final int EQUALE = 0;
	
	protected boolean sortFlag;
	
	public boolean isSortFlag()
    {
        return sortFlag;
    }

    public void setSortFlag(boolean sortFlag)
    {
        this.sortFlag = sortFlag;
    }
    protected Map<String, Column> typeStore;
    public Map<String, Column> getTypeStore()
    {
        return typeStore;
    }

    public void setTypeStore(Map<String, Column> typeStore)
    {
        this.typeStore = typeStore;
    }

    public int  compare(Object arg0, Object arg1) {
		if (arg0 == null && arg1 == null) {
			return EQUALE;
		}
		if (arg0 == null && "".equals(arg1)) {
			return EQUALE;

		}
		if (arg1 == null && "".equals(arg0)) {
			return EQUALE;

		}
		if (arg0 == null) {
			return SMALLER;
		}
		if (arg1 == null) {
			return BIGGER;
		}
		return EQUALE;
	}
}
