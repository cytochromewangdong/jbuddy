/*
 * Copyright (c) 2013 NeuLion, Inc. All Rights Reserved.
 */
package com.neu.jbuddy.basic.convert;

import com.neu.jbuddy.utils.StringUtils;


public class LongTranslate extends  BasicTypeTranslate
{
    public String getTypeName()
    {
        return "Long";
    }

    public boolean isType(String orignHead)
    {
        return orignHead.endsWith(".Long") || orignHead.endsWith(".long");
    }

    public Object convert(String value)
    {
        if ("null".equalsIgnoreCase(value))
        {
            return null;
        }
        if (StringUtils.isEmpty(value))
        {
            return null;
        }

        try
        {
            return Long.parseLong(value);
        }
        catch (Exception e)
        {
        }
        return null;

    }

    public String getPureHead(String orignHead)
    {
        if (orignHead == null)
        {
            return null;
        }
        if (orignHead.endsWith(".Long") || orignHead.endsWith(".long"))
        {
            orignHead = orignHead.substring(0,
                    orignHead.length() - ".long".length());
        }
        return orignHead;
    }

    public int getLevel()
    {
        return 1;
    }
}
