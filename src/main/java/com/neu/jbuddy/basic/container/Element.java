package com.neu.jbuddy.basic.container;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.neu.jbuddy.framework.excel.BasicSheet;


public interface Element extends Serializable {
	final int MAP = 1;

	final int TABLE = 2;

	final int OBJECT = 3;

	public String getGroup();

	public String getName();

	public int getType();

	public Object getValue();

	public List<Map> getList();

	public Map getMap();

	public int getCount();

	public List<String> getHeader();
	
	public String getAttibute(String key);

	public String getHeaderOriginName(short index);

	public String getHeaderName(short index);

	public String AddHeader(String orignHead);

	public void addCellValue(Map<String, Object> dataRow, short index,
			String value);

	public void AddRow(BasicSheet sheet, int currentRow);

	// public List<List> getData();
	public void AddMap(Map<String, Object> rowData);
	
	
	public void addPrimaryKey(String key);
	
	public Set<String> getPrimaryKeyList();
	
	public boolean hasPrimaryKey();
}
