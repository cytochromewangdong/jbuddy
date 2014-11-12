package com.neu.jbuddy.comp;

import java.util.Date;

import com.neu.jbuddy.basic.BuddyException;
import com.neu.jbuddy.utils.StringUtils;


public class StringComparator extends BasicComparator {

	public int compare(Object arg0, Object arg1) {
		int result = super.compare(arg0, arg1);
		if (result != EQUALE) {
			return result;
		}
		if(arg0==null || arg1==null) {
			return EQUALE;
		}
		if (arg0 != null && !(arg0 instanceof String)) {
			throw new BuddyException("Type is not String");

		}
		String strSrc = StringUtils.rightTrim((String) arg0);

		String StrDes = StringUtils.rightTrim(arg1.toString());
		if (arg1 instanceof Date) {
			StrDes = DATETIME_FORMAT.format(arg1);
		}
		return strSrc.compareTo(StrDes);
	}

}