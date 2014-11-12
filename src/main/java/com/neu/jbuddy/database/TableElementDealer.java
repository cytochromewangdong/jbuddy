package com.neu.jbuddy.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.neu.jbuddy.basic.BuddyException;
import com.neu.jbuddy.basic.container.Element;
import com.neu.jbuddy.log.Logger;
import com.neu.jbuddy.utils.StringUtils;


public class TableElementDealer
{
    public static void importElement(Element element, Connection connection)
    {
        String tblName = element.getName();
        String sql = null;
        List<String> headList = element.getHeader();
        List<Map> data = element.getList();
        try
        {
            deleteTable(tblName, connection);

            //to support empty table which is used to clear specified tables
            if (data == null)
                return;
                
            String sqlHead = getInsertSqlHeadByElement(tblName, headList);
            TableDesc.setConnection(connection);
            Map<String, Column> desc = TableDesc.getTableColumnsMap(tblName);
            String sqlBody = getInsertSqlBody(headList, desc, tblName);
            StringBuilder sb = new StringBuilder(sqlHead);
            sb.append(" VALUES ");
            sb.append(sqlBody);
            sql = sb.toString();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sql);
            // sb.append(getInsertSqlBody(head, map, desc, tblName));
            for (@SuppressWarnings("rawtypes") Map map : data)
            {
                setInsertParams(preparedStatement, headList, map, desc);
                preparedStatement.addBatch();
                // connection.createStatement().execute(sql);
            }
            preparedStatement.executeBatch();

            preparedStatement.close();
        }
        catch (SQLException e)
        {
            Logger.println(sql);
            throw new BuddyException(e);
        }
    }

    public static String[] getPrimaryKeysString(String tblName,
            Connection connection)
    {
        TableDesc.setConnection(connection);
        try
        {
            return TableDesc.getPrimaryKeysString(tblName);
        }
        catch (SQLException e)
        {
            throw new BuddyException(e);
        }
    }

    protected static String getInsertSqlBody(List<String> head,
            Map<String, Column> desc, String tblName)
    {
        StringBuilder sqlBuffer = new StringBuilder("(");
        for (String column : head)
        {
            Column colDesc = desc.get(column);
            if (colDesc == null)
            {
                colDesc = desc.get(column.toUpperCase());
            }
            if (colDesc == null)
            {
                throw new BuddyException("" + column + "‚" + tblName
                        + " does not exist");
            }
            sqlBuffer.append("?,");

        }
        String sql = sqlBuffer.toString();
        sql = sql.substring(0, sql.length() - 1) + ")";
        return sql;
    }

    protected static void setInsertParams(PreparedStatement preparedStatement,
            List<String> headList, Map row, Map<String, Column> desc)
    {
        StringBuilder sqlBuffer = new StringBuilder("(");
        int index = 1;
        for (String column : headList)
        {
            Column colDesc = desc.get(column);
            if (colDesc == null)
            {
                colDesc = desc.get(column.toUpperCase());
            }
            Object value = row.get(column);
            try
            {
                if (TableDesc.isNullIfEmpty(colDesc.getDataType()))
                {
                    if (value instanceof String)
                    {
                        if (StringUtils.isEmpty((String) value))
                        {
                            value = null;
                        }
                    }
                }
                preparedStatement.setObject(index++, value);
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }

        }
        String sql = sqlBuffer.toString();
        sql = sql.substring(0, sql.length() - 1) + ")";
    }

    protected static String getInsertSqlHeadByElement(String tblName,
            List<String> headList)
    {
        StringBuilder sqlBuffer = new StringBuilder("INSERT INTO ").append(
                tblName).append(" (");
        for (String column : headList)
        {
            sqlBuffer.append(column).append(",");
        }
        String sql = sqlBuffer.toString();
        sql = sql.substring(0, sql.length() - 1) + ")";
        return sql;
    }

    public static void deleteTable(String tblName, Connection connection)
            throws SQLException
    {
        String sql = "truncate " + tblName;
        connection.createStatement().execute(sql);
    }

    public static List<Map> getTableData(String tblName, Connection connection)
    {
        TableDesc.setConnection(connection);
        List<Column> desc = TableDesc.getTableColumns(tblName);
        StringBuilder sqlBuffer = new StringBuilder();
        for (Column column : desc)
        {
            // if ("Date".equals(column.getDataTypeName()))
            // {
            // sqlBuffer.append("TO_CHAR(").append(column.getColumnName())
            // .append(",'yyyyMMdd HH24:MI:SS') AS ")
            // .append(column.getColumnName()).append(",");
            //
            // }
            // else
            {
                sqlBuffer.append(column.getColumnName()).append(",");
            }
        }
        String sql = sqlBuffer.toString();
        sql = "SELECT " + sql.substring(0, sql.length() - 1) + " from "
                + tblName;
        List<Map> results = new ArrayList<Map>();
        try
        {
            Statement pstmt = connection.createStatement();
            ResultSet rs = pstmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            while (rs.next())
            {
                Map<String, Object> rowHash = new LinkedHashMap<String, Object>();
                for (int i = 1; i <= numberOfColumns; i++)
                {
                    String colName = rsmd.getColumnName(i);
                    int type = rsmd.getColumnType(i);
                    switch (type)
                    {
                    case Types.DATE:
                        rowHash.put(colName, rs.getDate(colName));
                        break;
                    case Types.TIME:
                        rowHash.put(colName, rs.getTime(colName));
                        break;
                    case Types.TIMESTAMP:
                        rowHash.put(colName, rs.getTimestamp(colName));
                        break;
                    default:
                        rowHash.put(colName, rs.getString(colName));
                    }
                }
                results.add(rowHash);
            }
        }
        catch (SQLException e)
        {
            throw new BuddyException(e);
        }
        return results;
    }
}
