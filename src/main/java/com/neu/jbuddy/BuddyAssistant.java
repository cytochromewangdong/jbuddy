package com.neu.jbuddy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.neu.jbuddy.basic.BuddyException;
import com.neu.jbuddy.basic.container.Element;
import com.neu.jbuddy.basic.export.WorkbookDataDealer;
import com.neu.jbuddy.basic.export.WorkbookDataDealerImpl;
import com.neu.jbuddy.comp.CompareDealer;
import com.neu.jbuddy.database.Column;
import com.neu.jbuddy.database.TableDesc;
import com.neu.jbuddy.database.TableElementDealer;
import com.neu.jbuddy.log.Logger;
import com.neu.jbuddy.utils.StringUtils;


public class BuddyAssistant
{
    public static final String COMMON = "common";

    // public static String FILE_SEPARATOR = null;

    public Connection connection = null;

    public Connection getConnection()
    {
        return connection;
    }
    protected boolean autoCommit;
    public boolean isAutoCommit()
    {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit)
    {
        this.autoCommit = autoCommit;
    }
    public String path = null;

    public static WorkbookDataDealer export = new WorkbookDataDealerImpl();

    public static final String KEY_COMPARE = "compare";

    public static final String KEY_PREPARE = "prepare";

    private List<List<Element>> caseList = null;

    private List<Element> commonList = null;

    public int currentCaseNo = -1;

    public String sheetName = "case";

    private static String[] suffixList = new String[] { ".xlsx", ".xls" };

    protected DataSource dataSource;

    public DataSource getDataSource()
    {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
        try
        {
            connection = dataSource.getConnection();
        }
        catch (SQLException e)
        {
            Logger.println(e);
        }

    }

    // public Connection getConnection()
    // {
    // this.getDataSource().getConnection();
    // }

    /**
     * Constructor for TestTest.
     * @param arg0
     */
    public void initialize(String excelName)
    {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory())
        {
            throw new BuddyException("The " + path
                    + " is not a directory or does not exist");
        }

        File dataFile = new File(directory, excelName);

        for (String suffix : suffixList)
        {

            if (!excelName.endsWith(suffix))
            {
                File testFile = new File(directory, excelName + suffix);
                if (testFile.exists())
                {
                    dataFile = testFile;
                    break;
                }

            }
        }
        if (!dataFile.exists())
        {
            throw new BuddyException("The " + dataFile.getAbsolutePath()
                    + " does not exist");
        }
        POIFSFileSystem fs;
        try
        {
            fs = new POIFSFileSystem(new FileInputStream(dataFile));
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            caseList = export.Export(workbook);
            commonList = export.Export(workbook, COMMON);
        }
        catch (FileNotFoundException e)
        {
            throw new BuddyException(e);
        }
        catch (IOException e)
        {
            throw new BuddyException(e);
        }

    }

    public void reset()
    {
        caseList = null;
        commonList = null;
        currentCaseNo = -1;
    }

    public void setCurrentCaseNo(int caseNO)
    {
        currentCaseNo = caseNO;
    }

    public int getCaseCount()
    {
        return caseList.size();
    }

    /**
     * @param connection The connection to set.
     */
    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }

    /**
     * @param filePath The filePath to set.
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    protected List<Element> getTableElementList(int idx, String group)
    {
        List<Element> elementList = caseList.get(idx);
        List<Element> tblList = new ArrayList<Element>();
        for (Element element : elementList)
        {
            int type = element.getType();
            String currGroup = element.getGroup();
            if (type == Element.TABLE
                    && (StringUtils.isEmpty(group) || group.equals(currGroup)))
            {
                tblList.add(element);
            }
        }
        return tblList;
    }

    protected List<Element> getTableElementList(String group)
    {
        return getTableElementList(currentCaseNo, group);
    }

    protected List<Element> getTableElementList()
    {
        return getTableElementList(currentCaseNo);
    }

    protected List<Element> getTableElementList(int idx)
    {

        return getTableElementList(idx, null);
    }

    protected void prepareDB(int idx, String group)
    {
        List<Element> tblList = getTableElementList(idx, group);
        if(this.autoCommit)
        {
            try
            {
                connection.setAutoCommit(false);
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        for (Element element : tblList)
        {
            TableElementDealer.importElement(element, this.connection);
        }
        if(this.autoCommit)
        {
            try
            {
                connection.commit();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    protected Element getCommonElement(String name)
    {
        List<Element> tblList = commonList;
        for (Element element : tblList)
        {
            if (element.getType() == Element.MAP
                    && element.getName().equals(name))
            {
                return element;
            }
        }
        return null;
    }

    public List<Map> getCommonList(String name)
    {
        Element element = getCommonElement(name);
        if (element == null)
        {
            return null;
        }
        return element.getList();
    }

    public Map getCommonMap(String name)
    {
        List<Map> list = getCommonList(name);
        if (list == null)
        {
            return null;
        }
        return list.get(0);
    }

    public void prepareCommon()
    {
        if(this.autoCommit)
        {
            try
            {
                connection.setAutoCommit(false);
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
        List<Element> tblList = commonList;
        for (Element element : tblList)
        {
            if (element.getType() == Element.TABLE)
            {
                TableElementDealer.importElement(element, connection);
            }
        }
        if(this.autoCommit)
        {
            try
            {
                connection.commit();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public void prepareDB(String group)
    {
        prepareDB(currentCaseNo, group);
    }

    protected void prepareDB(int idx)
    {
        prepareDB(idx, null);
    }

    public void prepareDB()
    {
        prepareDB(currentCaseNo, KEY_PREPARE);
    }

    protected Element getElement(int idx, String name, int type, String group)
    {
        List<Element> elementList = caseList.get(idx);
        for (Element element : elementList)
        {
            String currName = element.getName();
            int currType = element.getType();
            String currGroup = element.getGroup();
            if (currName.equals(name)
                    && currType == type
                    && (group == null || (currGroup != null && currGroup
                            .equals(group))))
            {
                return element;
            }
        }
        return null;
    }

    protected Element getElement(int idx, String name, int type)
    {
        return getElement(idx, name, type, null);
    }

    protected List<Map> getMapList(int idx, String name, String group)
    {
        Element element = getElement(idx, name, Element.MAP, group);
        if (element == null)
        {
            return null;
        }
        return element.getList();
    }

    protected List<Map> getMapList(int idx, String name)
    {
        return getMapList(idx, name, null);
    }

    public List<Map> getMapList(String name)
    {
        return getMapList(currentCaseNo, name);
    }

    protected Map getMap(int idx, String name, String group)
    {
        List<Map> list = getMapList(idx, name, group);
        if (list == null)
        {
            return null;
        }
        if (list.size() == 0)
        {
            return null;
        }
        return list.get(0);
    }

    protected Map getMap(int idx, String name)
    {
        return getMap(idx, name, null);
    }

    public Map getMap(String name)
    {
        return getMap(currentCaseNo, name);
    }

    public List<Map> getTableDataList(String name)
    {
        return getTableDataList(currentCaseNo, name);
    }

    protected List<Map> getTableDataList(int idx, String name)
    {
        return getTableDataList(idx, name, null);
    }

    public List<Map> getTableDataList(String name, String group)
    {
        return getTableDataList(currentCaseNo, name, group);
    }

    protected List<Map> getTableDataList(int idx, String name, String group)
    {
        Element elment = getElement(idx, name, Element.TABLE, group);
        if (elment == null)
        {
            return null;
        }

        return elment.getList();
    }

    protected String[] getPrimaryKeyList(int idx, String name, String group)
    {
        Element elment = getElement(idx, name, Element.TABLE, group);
        if (elment == null)
        {
            return new String[0];
        }

        return elment.getPrimaryKeyList().toArray(new String[0]);
    }

    protected boolean assertMap(int caseNo, String hashName, Map resultMap)
    {
        Map dataMap = getMap(caseNo, hashName);

        return assertMap(dataMap, resultMap);
    }

    public boolean assertMap(String mapName, Map resultMap)
    {
        return assertMap(currentCaseNo, mapName, resultMap);
    }

    public boolean assertMap(Map dataMap, Map resultMap)
    {
        Logger.printMap(dataMap, resultMap);
        return CompareDealer.compareMap(dataMap, resultMap);
    }

    protected boolean assertList(int caseNo, String arrName,
            List<Map> resultList)
    {
        List<Map> dataList = getMapList(caseNo, arrName);

        return assertList(dataList, resultList);
    }

    public boolean assertList(String arrName, List<Map> resultList)
    {
        return assertList(currentCaseNo, arrName, resultList);

    }

    public boolean assertList(List<Map> dataList, List<Map> resultList)
    {
        return CompareDealer.compareList(dataList, resultList);
    }

    protected boolean assertTable(int caseNo, String tableName,
            List<Map> resultList, String group)
    {
        List<Map> dataList = getTableDataList(caseNo, tableName, group);
        Map<String, Column> desc = TableDesc.getTableColumnsMap(tableName);
        return assertTable(dataList, resultList,
                TableElementDealer.getPrimaryKeysString(tableName, connection),desc);
        // assertSame( );
    }

    protected boolean assertTable(int caseNo, String tableName,
            List<Map> resultList)
    {
        List<Map> dataList = getTableDataList(caseNo, tableName, null);
        Map<String, Column> desc = TableDesc.getTableColumnsMap(tableName);
        return assertTable(dataList, resultList,
                TableElementDealer.getPrimaryKeysString(tableName, connection),desc);

    }

    protected boolean assertTable(String tableName, List<Map> resultList,
            String group)
    {
        return assertTable(currentCaseNo, tableName, resultList, group);
        // assertSame( );
    }

    public boolean assertTable(String tableName, List<Map> resultList)
    {
        return assertTable(currentCaseNo, tableName, resultList, null);
    }

    public boolean assertTable(List<Map> dataList, List<Map> resultList,
            String[] keys, Map<String, Column> typeStore)
    {
        return CompareDealer.sortCompareListMap(dataList, resultList, keys, typeStore);
    }

    protected boolean assertTable(int caseNo, String tblName, String group)
    {
        List<Map> dataList = getTableDataList(caseNo, tblName, group);
        List<Map> resultList = TableElementDealer.getTableData(tblName,
                connection);
        String[] primaryKeyList = getPrimaryKeyList(caseNo, tblName, group);
        if (primaryKeyList.length <= 0)
        {
            primaryKeyList = TableElementDealer.getPrimaryKeysString(tblName,
                    connection);
        }
        Map<String, Column> desc = TableDesc.getTableColumnsMap(tblName);
        return assertTable(dataList, resultList, primaryKeyList, desc);
    }

    public boolean assertTable(String tblName, String group)
    {
        return assertTable(currentCaseNo, tblName, group);
    }

    protected boolean assertTable(int caseNo)
    {
        List<Element> tblList = getTableElementList(caseNo, KEY_COMPARE);
        for (Element element : tblList)
        {

            Logger.println("TABLE:" + element.getName());
            List<Map> dataList = element.getList();
            List<Map> resultList = TableElementDealer.getTableData(
                    element.getName(), connection);
            String[] primaryKeyList = element.getPrimaryKeyList().toArray(
                    new String[0]);
            if (primaryKeyList.length <= 0)
            {
                primaryKeyList = TableElementDealer.getPrimaryKeysString(
                        element.getName(), connection);
            }
            Map<String, Column> desc = TableDesc.getTableColumnsMap(element.getName());
            if (!assertTable(dataList, resultList, primaryKeyList, desc))
            {
                //Logger.printList(dataList, resultList);
                return false;
            }
        }
        return true;
    }

    public boolean assertTable()
    {
        return assertTable(currentCaseNo);
    }

    /**
     * @return Returns the sheetName.
     */
    public String getSheetName()
    {
        return sheetName;
    }

    /**
     * @param sheetName The sheetName to set.
     */
    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }

}
