package com.neu.jbuddy.log;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Logger
{

    Logger()
    {
        dateForm = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
    }

    static
    {
        file = System.getProperty("user.dir") + "\\JBuddy.log";
    }

    public static void println(Object obj)
    {
        instance.print(obj);
    }
    public static void printError(Object obj)
    {
        instance.error(obj);
    }
//    public static void printMap(Map srcMap, Map destMap)
//    {
//        printMap(srcMap, destMap, null);
//    }
//
//    public static void printMap(Map srcMap, Map destMap, String[] keys)
//    {
//        if (keys == null)
//        {
//            keys = (String[]) srcMap.keySet().toArray(new String[0]);
//        }
//        StringBuffer title = new StringBuffer();
//        StringBuffer souteData = new StringBuffer();
//        StringBuffer resultData = new StringBuffer();
//        StringBuffer allStr = new StringBuffer("\n");
//        for (int i = 0; i < keys.length; i++)
//        {
//            title.append(keys[i]).append("\t");
//            souteData.append(srcMap.get(keys[i])).append("\t");
//            resultData.append(destMap.get(keys[i])).append("\t");
//        }
//        allStr.append(title).append("\n");
//        allStr.append(souteData).append("\n");
//        allStr.append("Map Value").append("\n");
//        allStr.append(resultData).append("\n");
//        allStr.append("Map Value End").append("\n");
//        Logger.println(allStr);
//
//    }

    void print(Object obj)
    {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(dateForm.format(new Date())).append(":");
        strBuf.append(obj);
        writeLog(strBuf, null);
    }

    void error(Object obj)
    {
        StringBuffer strBuf = new StringBuffer();
//        strBuf.append(dateForm.format(new Date())).append(":");
        strBuf.append(obj);
        writeLog(strBuf, null, true);
    }
    static Logger getInstance()
    {
        return instance;
    }

    public static void printMap(Map predictedData, Map resultData, String[] keys)
    {
        printList(Arrays.asList(predictedData), Arrays.asList(resultData), keys);
    }
    public static void printMap(Map predictedData, Map resultData)
    {
        printList(Arrays.asList(predictedData), Arrays.asList(resultData), null);
    }
    
    public static void printList(List<Map> dataList, List<Map> resultList)
    {
        printList(dataList, resultList, null);
    }

    public static void printList(List<Map> dataList, List<Map> resultList,
            String[] keys)
    {
        if (dataList != null && dataList.size() > 0)
        {
            if (keys == null)
            {
                keys = (String[]) (dataList.get(0)).keySet().toArray(
                        new String[0]);
            }
            StringBuffer title = new StringBuffer();
            StringBuffer souteData = new StringBuffer();
            StringBuffer resultData = new StringBuffer();
            StringBuffer allStr = new StringBuffer("\n");
            for (int i = 0; i < keys.length; i++)
            {
                title.append(keys[i]).append("\t");
            }
            allStr.append(title);
            allStr.append("\n");
            for (int j = 0; j < dataList.size(); j++)
            {
                for (int i = 0; i < keys.length; i++)
                {
                    souteData.append(((Map) dataList.get(j)).get(keys[i]))
                            .append("\t");
                }
//                souteData.append("\n");
            }

            allStr.append(souteData).append("\n");
            allStr.append(
                    "=======================================================================================================")
                    .append("\n");
            for (int j = 0; j < resultList.size(); j++)
            {
                for (int i = 0; i < keys.length; i++)
                {
                    resultData.append(((Map) resultList.get(j)).get(keys[i]))
                            .append("\t");
                }
                resultData.append("\n");
            }
            allStr.append(resultData).append("\n");
            Logger.println(allStr);
        }
    }

    private synchronized void writeLog(StringBuffer stringbuffer,
            Throwable throwable)
    {
        writeLog(stringbuffer, throwable, false);
    }
    private synchronized void writeLog(StringBuffer stringbuffer,
            Throwable throwable, boolean isError)
    {
        PrintStream printer = isError? System.err:System.out;
        if (file != null)
        {
            printer.println(stringbuffer);
            PrintWriter printwriter = openLogFile();
            if (throwable == null)
            {
                printwriter.println(stringbuffer);
            }
            else
            {
                printwriter.print(stringbuffer);
                throwable.printStackTrace(printwriter);
            }
            printwriter.close();
            printwriter = null;
        }
        else if (throwable == null)
        {
            printer.println(stringbuffer);
        }
        else
        {
            printer.println(stringbuffer);
            throwable.printStackTrace(printer);
        }
    }

    private PrintWriter openLogFile()
    {
        try
        {
            BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(
                    new FileOutputStream(file, true));
            return new PrintWriter(bufferedoutputstream, true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static Logger instance;

    static String file;

    SimpleDateFormat dateForm;

    static
    {
        instance = new Logger();
    }

    /**
     * @param file The file to set.
     */
    public static void setFile(String file)
    {
        Logger.file = file;
    }
}
