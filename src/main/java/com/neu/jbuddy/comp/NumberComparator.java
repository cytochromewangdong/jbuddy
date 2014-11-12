package com.neu.jbuddy.comp;

import java.math.BigDecimal;

import com.neu.jbuddy.basic.BuddyException;


public class NumberComparator extends BasicComparator {

	public int compare(Object arg0, Object arg1) {
		int result = super.compare(arg0, arg1);
		if (result != EQUALE) {
			return result;
		}
		if (arg0 == null || arg1 == null) {
			return EQUALE;
		}
//		if (arg0 != null && !(arg0 instanceof Number)) {
//			throw new BuddyException("Type is not Number");
//		}
		if (!(arg1 instanceof Number) && !(arg1 instanceof String)) {
			throw new BuddyException("Compared Type is not Number OR String ");
		}
        if (!(arg0 instanceof Number) && !(arg0 instanceof String)) {
            throw new BuddyException("Compared Type is not Number OR String ");
        }
		BigDecimal numSrc = new BigDecimal(arg0.toString());
		BigDecimal numDest = new BigDecimal(arg1.toString());

		return numSrc.compareTo(numDest);
	}
}