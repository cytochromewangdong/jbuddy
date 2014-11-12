package com.neu.jbuddy.comp;

import java.util.Comparator;
import java.util.List;

import com.neu.jbuddy.basic.BuddyException;


public class ListComparator extends BasicComparator {
	public int compare(Object arg0, Object arg1) {
		int result = super.compare(arg0, arg1);
		if (result != EQUALE) {
			return result;
		}
		if(arg0==null || arg1==null) {
			return EQUALE;
		}
		if (arg0 != null && !(arg0 instanceof List)) {
			throw new BuddyException("Type is not List");

		}
		if (arg1 != null && !(arg1 instanceof List)) {
			throw new BuddyException("Type is not List");

		}

		List srcList = (List) arg0;
		List desList = (List) arg1;
        if (srcList.size() < desList.size())
        {
            return SMALLER;
        }
        if (srcList.size() > desList.size())
        {
            return BIGGER;
        }
		for (int i=0;i<srcList.size();i++) {
			Object o1 = srcList.get(i);
			if(i>=desList.size() ) {
				return BIGGER;
			}
			Comparator comp = ComparatorFactory.getComparator(o1, this.getTypeStore());
			Object o2= desList.get(i);
			result = comp.compare(o1, o2);
			if (result != EQUALE) {
				return result;
			} else {
				continue;
			}
		}
		return EQUALE;
	}
}
