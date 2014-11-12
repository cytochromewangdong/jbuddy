package com.neu.jbuddy.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.neu.jbuddy.basic.BuddyException;
import com.neu.jbuddy.utils.StringUtils;


public class TableDesc {
	private static Connection connection = null;

	/**
	 * @param connection
	 *            The connection to set.
	 */
	public static void setConnection(Connection connection) {
		TableDesc.connection = connection;
	}

	public static Map<String, Column> getTableColumnsMap(String tableName) {
		List<Column> colList = getTableColumns(tableName);
		Map<String, Column> map = new LinkedHashMap<String, Column>();
		for (Column column : colList) {
			map.put(column.getColumnName(), column); //.toUpperCase()
		}
		return map;
	}

	public static List<Column> getTableColumns(String tableName) {
		List<Column> result = new ArrayList<Column>();
		try {
			Map<String, String> map = getPrimaryKeys(tableName); // TODO .toUpperCase()
			ResultSet rs = connection.getMetaData().getColumns(null,
					connection.getMetaData().getUserName(),
					tableName, "%");//.toUpperCase()

			while (rs.next()) {
				Column column = new Column();
				column.setColumnName(rs.getString("COLUMN_NAME"));
				if (StringUtils.isEmpty(map.get(column.getColumnName()))) {
					column.setPrimary(true);
				} else {
					column.setPrimary(false);
				}
				column.setColumnSize(rs.getInt("COLUMN_SIZE"));
				column.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
				column.setDataType(rs.getInt("DATA_TYPE"));
				column.setDataTypeName(convertSqlTypeToJavaTypeName(column
						.getDataType(), column.getColumnSize(), column
						.getDecimalDigits()));
				column.setNullable(rs.getInt("NULLABLE"));
				result.add(column);
			}

		} catch (SQLException e) {
			throw new BuddyException(e);
		}
		return result;
	}

	public static Map<String, String> getPrimaryKeys(String tableName)
			throws SQLException {
		ResultSet rs = connection.getMetaData().getPrimaryKeys(null,
				connection.getMetaData().getUserName(), tableName);
		Map<String, String> map = new LinkedHashMap<String, String>();
		while (rs.next()) {
			map.put(rs.getString("COLUMN_NAME"), rs.getString("COLUMN_NAME"));
		}
		return map;
	}

	public static String[] getPrimaryKeysString(String tableName)
			throws SQLException {
		ResultSet rs = connection.getMetaData().getPrimaryKeys(null,
				connection.getMetaData().getUserName(), tableName);
		List<String> keys = new ArrayList<String>();
		while (rs.next()) {
			keys.add (rs.getString("COLUMN_NAME"));
		}
		return keys.toArray(new String[0]);
	}

	public static final String convertSqlTypeToJavaTypeName(int dataType,
			int columnSize, int decimal) {
		String typeString = "";
		int integerUnit = 0;
		switch (dataType) {
		case -5:
			typeString = "Long";
			break;

		case -2:
			typeString = "byte[]";
			break;

		case -7:
			typeString = "Boolean";
			break;

		case 2004:
			typeString = "byte[]";
			break;

		case 16: // '\020'
			typeString = "Boolean";
			break;

		case 1: // '\001'
			typeString = "String";
			break;

		case 2005:
			typeString = "byte[]";
			break;

		case 91: // '['
			typeString = "Date";
			break;

		case 3: // '\003'
			integerUnit = columnSize - decimal;
			if (decimal == 0) {
				if (integerUnit > 9)
					typeString = "Long";
				else
					typeString = "Integer";
			} else {
				typeString = "Double";
			}
			break;

		case 8: // '\b'
			typeString = "Double";
			break;

		case 6: // '\006'
			typeString = "Float";
			break;

		case 4: // '\004'
			typeString = "Integer";
			break;

		case -4:
			typeString = "byte[]";
			break;

		case -1:
			typeString = "String";
			break;

		case 2: // '\002'
			integerUnit = columnSize - decimal;
			if (decimal == 0) {
				if (integerUnit > 9)
					typeString = "Long";
				else
					typeString = "Integer";
			} else {
				typeString = "Double";
			}
			break;

		case 1111:
			typeString = "Object";
			break;

		case 7: // '\007'
			typeString = "Float";
			break;

		case 5: // '\005'
			typeString = "Short";
			break;

		case 92: // '\\'
			typeString = "Date";
			break;

		case 93: // ']'
			typeString = "Date";
			break;

		case -6:
			typeString = "Byte";
			break;

		case -3:
			typeString = "byte[]";
			break;

		case 12: // '\f'
			typeString = "String";
			break;

		default:
			typeString = "";
			break;
		}
		return typeString;
	}
	
	public static final boolean isNullIfEmpty(int dataType) {
	    boolean ret = true;
        int integerUnit = 0;
        switch (dataType) {
        case 1: // '\001'
            //typeString = "String";
            ret = false;
            break;

        case -1:
//            typeString = "String";
            ret = false;
            break;
        case 12: // '\f'
//          typeString = "String";
          ret = false;
            break;

        default:
            break;
        }
        return ret;
    }
	
	
	   public static final boolean isNumber(int dataType) {
	        boolean isNumber = true;
	        int integerUnit = 0;
	        switch (dataType) {
	        case -2:
	            isNumber = false;
	            break;

	        case -7:
                isNumber = false;
	            break;

	        case 2004:
                isNumber = false;
	            break;

	        case 16: // '\020'
                isNumber = false;
	            break;

	        case 1: // '\001'
                isNumber = false;
	            break;

	        case 2005:
                isNumber = false;
	            break;

	        case 91: // '['
                isNumber = false;
	            break;
	        case -4:
                isNumber = false;
	            break;

	        case -1:
                isNumber = false;
	            break;

	        case 1111:
                isNumber = false;
	            break;

	        case 92: // '\\'
                isNumber = false;
	            break;

	        case 93: // ']'
                isNumber = false;
	            break;
	        case 12: // '\f'
                isNumber = false;
	            break;

	        default:
                //isNumber = false;
	            break;
	        }
	        return isNumber;
	    }
}
