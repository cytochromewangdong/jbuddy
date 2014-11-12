/*
 * Copyright (c) 2013 NeuLion, Inc. All Rights Reserved.
 */
package com.neu.jbuddy.basic.convert;

import com.neu.jbuddy.utils.StringUtils;


public class BooleanTranslate extends BasicTypeTranslate
{
    public String getTypeName()
    {
        return "Boolean";
    }

    public boolean isType(String orignHead)
    {
        return orignHead.endsWith(".Boolean") || orignHead.endsWith(".boolean");
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
            return Boolean.parseBoolean(value);
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
        if (orignHead.endsWith(".Boolean") || orignHead.endsWith(".boolean"))
        {
            orignHead = orignHead.substring(0,
                    orignHead.length() - ".boolean".length());
        }
        return orignHead;
    }

    public int getLevel()
    {
        return 1;
    }
}
