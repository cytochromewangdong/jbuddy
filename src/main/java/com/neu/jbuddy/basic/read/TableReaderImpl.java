package com.neu.jbuddy.basic.read;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neu.jbuddy.basic.container.Element;
import com.neu.jbuddy.basic.export.UnitExporter;
import com.neu.jbuddy.framework.excel.BasicSheet;
import com.neu.jbuddy.utils.StringUtils;

public class TableReaderImpl extends BasicKeywordReader implements
		KeywordReader {
	private final String KEY_WORD = "table";

	private final short MAX_PROPERTY_CNT = 100;

	private static final short START_COLUMN = 0;

	public String getKeyword() {
		return KEY_WORD;
	}

	public boolean iskeyReader(String key) {
		if (key == null)
			return false;
		String[] keywords = key.split(EQUAL_MARK);
		if (keywords.length != 2) {
			return false;
		}
		if (KEY_WORD.equals(keywords[0].trim())) {
			return true;
		}
		return false;
	}

	public int read(UnitExporter exporter, List<Element> dataList,
			BasicSheet sheet, HSSFWorkbook workBook, int currentRow) {
		setExporter(exporter);
		short column = START_COLUMN;
		String keyword = sheet.getCellValue(currentRow, column);
		DefaultElement element = new DefaultElement(split(keyword)[1],
				Element.TABLE);
		dataList.add(element);
		while (column <= MAX_PROPERTY_CNT && !StringUtils.isEmpty(keyword)
				&& !isComment(keyword)) {
			String[] keywords = split(keyword);
			if (keywords.length == 2) {
				element.setAttribute(keywords[0], keywords[1]);
			} else {
				element.setAttribute(keyword, null);
			}
			keyword = sheet.getCellValue(currentRow, column);
			column++;
		}
		String group = element.getAttibute(KEY_GROUP);
		if (group == null) {
			group = "prepare";
		}
		element.setGroup(group);
		currentRow++;
		return readHeadData(sheet, workBook, currentRow, element);
	}

	protected String[] split(String cellValue) {
		String[] keywords = cellValue.split(EQUAL_MARK);
		for (int i = 0; i < keywords.length; i++) {
			keywords[i] = keywords[i].trim();
		}
		return keywords;
	}

}
