package com.neu.jbuddy.comp;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.neu.jbuddy.basic.BuddyException;


public class DateComparator extends BasicComparator
{
    SimpleDateFormat dateFormat;
    public DateComparator(SimpleDateFormat simpleDateFormat)
    {
        this.dateFormat = simpleDateFormat;
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
        // if (arg0 != null && !(arg0 instanceof Date)) {
        // throw new BuddyException("Type is not Date");
        //
        // }
        if (!(arg0 instanceof Date) && !(arg0 instanceof String))
        {
            throw new BuddyException("Compared Type is not Date, String");
        }
        if (!(arg1 instanceof Date) && !(arg1 instanceof String))
        {
            throw new BuddyException("Compared Type is not Date, String");
        }

        String strSrcDate = "";
        if (arg0 instanceof Date)
        {
            Date dt1 = (Date) arg0;
            strSrcDate = dateFormat.format(dt1);
        }
        else
        {
            strSrcDate = (String) arg0;
        }
        String StrDesDate = arg1.toString();
        if (arg1 instanceof Date)
        {
            StrDesDate = dateFormat.format(arg1);
        }

        return strSrcDate.compareTo(StrDesDate);
    }

}
