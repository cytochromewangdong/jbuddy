package com.neu.jbuddy.basic.read;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neu.jbuddy.basic.container.Element;
import com.neu.jbuddy.basic.convert.TypeTranslate;
import com.neu.jbuddy.basic.convert.TypeTranslateFactory;
import com.neu.jbuddy.basic.export.UnitExporter;
import com.neu.jbuddy.framework.excel.BasicSheet;
import com.neu.jbuddy.utils.StringUtils;
//import java.util.HashMap;

public abstract class BasicKeywordReader {
	private final static String COMMET_1 = "#";

	//private final static String COMMET_2 = "*";

	private final static String COMMET_3 = "//";
	
	
	private final static String PRIMARY_KEY = "*";

	private final static short VALUE_START = 0;

	public final static int ROW_TYPE_EMPTY = 0;

	public final static int ROW_TYPE_COMMENT = 1;

	public final static int ROW_TYPE_KEYWORD = 2;

	public final static int ROW_TYPE_DATA = 3;

	protected UnitExporter exporter;

	protected final static int MAX_BORINGBLANK = 50;

	protected boolean isComment(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		if (COMMET_1.equals(value.substring(0, 1))) {
			return true;
		}
//		if (COMMET_2.equals(value.substring(0, 1))) {
//			return true;
//		}
		if (value.length() > 2) {
			if (COMMET_3.equals(value.substring(0, 2))) {
				return true;
			}
		}
		return false;
	}

	protected int readHeadData(BasicSheet sheet, HSSFWorkbook workBook,
			int currentRow, Element element) {
		currentRow = readHead(sheet, workBook, currentRow, element);
		currentRow = readData(sheet, workBook, currentRow, element);
		return currentRow;
	}

	protected int readHead(BasicSheet sheet, HSSFWorkbook workBook,
			int currentRow, Element elment) {
		int missingRow = 0;
		while (missingRow <= MAX_BORINGBLANK) {
			String cellValue = sheet.getCellValue(currentRow, VALUE_START);
			if (!StringUtils.isEmpty(cellValue)) {
				missingRow = 0;
			} else {
				currentRow++;
				missingRow++;
				continue;
			}
			if (isComment(cellValue)) {
				currentRow++;
				continue;
			}
			if (exporter.isKeyword(cellValue)) {
				return currentRow;
			}
			short column = VALUE_START;
			while (isValid(cellValue)) {
			    if(cellValue.startsWith(PRIMARY_KEY))
			    {
			        cellValue = cellValue.substring(PRIMARY_KEY.length());
			        elment.addPrimaryKey(cellValue);
			    }
				elment.AddHeader(cellValue);
				column++;
				cellValue = sheet.getCellValue(currentRow, column);
			}
			currentRow++;
			break;
		}

		return currentRow;
	}

	protected boolean isValid(String value) {
		return !StringUtils.isEmpty(value) && !isComment(value);
	}

	protected int readData(BasicSheet sheet, HSSFWorkbook workBook,
			int currentRow, Element element) {

		short maxColumnIndex = (short) (element.getHeader().size() - 1);
		if (maxColumnIndex == -1) {
			maxColumnIndex = 0;
		}
		int rowType = getRowType(sheet, currentRow, maxColumnIndex);

		while (rowType == ROW_TYPE_COMMENT || rowType == ROW_TYPE_DATA) {
			if (rowType == ROW_TYPE_DATA) {
				element.AddRow(sheet, currentRow);
			}
			currentRow++;
			rowType = getRowType(sheet, currentRow, maxColumnIndex);
		}

		return currentRow;
	}

	protected int getRowType(BasicSheet sheet, int currentRow,
			short maxColumnIndex) {
		if (maxColumnIndex == 0) {
			maxColumnIndex = 1;
		}
		String value = sheet.getCellValue(currentRow, VALUE_START);
		if (isComment(value)) {
			return ROW_TYPE_COMMENT;
		}
		if (exporter.isKeyword(value)) {
			return ROW_TYPE_KEYWORD;
		}
		for (short i = VALUE_START; i <= maxColumnIndex; i++) {
			value = sheet.getCellValue(currentRow, i);
			if (!StringUtils.isEmpty(value)) {
				return ROW_TYPE_DATA;
			}
		}

		return ROW_TYPE_EMPTY;

	}

	/**
	 * @param exporter
	 *            The exporter to set.
	 */
	public void setExporter(UnitExporter exporter) {
		this.exporter = exporter;
	}

	protected static class DefaultElement implements Element {

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = -7857785307650956171L;

		public DefaultElement(String name, int type) {
			this.name = name;
			setAttribute("name", name);
			this.type = type;
		}

		private String name;

		private String group;

		private int type;

		private List<String> header = new ArrayList<String>();

		private List<String> originHeader = new ArrayList<String>();

		private List<Map> list = new ArrayList<Map>();

		private List<TypeTranslate> translates = new ArrayList<TypeTranslate>();

		private Map<String, String> attribute = new LinkedHashMap<String, String>();
		
		private Set<String> primaryKeyList = new HashSet<String>();

		/**
		 * @return Returns the group.
		 */
		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		public void setAttribute(String name, String value) {
			if (!"name".equals(name)) {
				attribute.put(name, value);
			}
		}

		/**
		 * @return Returns the name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return Returns the type.
		 */
		public int getType() {
			return type;
		}

		public Object getValue() {
			if (list == null || list.size() == 0) {
				return null;
			}
			/*if (list.size() == 1) {
				return list.get(0);
			}*/
			return list;
		}

		public List<Map> getList() {
			return (List<Map>) getValue();
		}

		public Map getMap() {
			return (Map) getValue();
		}

		public int getCount() {
			return list == null ? 0 : list.size();
		}

		public List<String> getHeader() {
			return header;
		}

		public void AddMap(Map<String, Object> rowData) {
			list.add(rowData);

		}

		public String getHeaderOriginName(short index) {
			return originHeader.get(index);
		}

		public String getHeaderName(short index) {
			return header.get(index);
		}

		public String AddHeader(String orignHead) {
			orignHead = orignHead.trim();
			TypeTranslate typeTranslate = TypeTranslateFactory
					.create(this,orignHead);
			translates.add(typeTranslate);
			String pureHead = typeTranslate.getPureHead(orignHead);
			header.add(pureHead);
			originHeader.add(orignHead);
			return pureHead;
		}

		public void addCellValue(Map<String, Object> dataRow, short index,
				String value) {
			TypeTranslate typeTranslate = translates.get(index);
			String headName = getHeaderName(index);
			dataRow.put(headName, typeTranslate.convert(value));

		}

		public void AddRow(BasicSheet sheet, int currentRow) {
			Map<String, Object> dataRow = new LinkedHashMap<String, Object>();
			for (short i = 0; i < header.size(); i++) {
				String value = sheet.getCellValue(currentRow, i);
				addCellValue(dataRow, i, value);
			}
			AddMap(dataRow);

		}

		public String getAttibute(String key) {
			return attribute.get(key);
		}

        @Override
        public void addPrimaryKey(String key)
        {
            
            primaryKeyList.add(key);
        }

        @Override
        public Set<String> getPrimaryKeyList()
        {
            return primaryKeyList;
        }

        @Override
        public boolean hasPrimaryKey()
        {
            return !primaryKeyList.isEmpty();
        }

	}
}
