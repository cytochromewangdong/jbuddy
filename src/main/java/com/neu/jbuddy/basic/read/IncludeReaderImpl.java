package com.neu.jbuddy.basic.read;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neu.jbuddy.basic.container.Element;
import com.neu.jbuddy.basic.export.UnitExporter;
import com.neu.jbuddy.framework.excel.BasicSheet;
import com.neu.jbuddy.log.Logger;
import com.neu.jbuddy.utils.StringUtils;

public class IncludeReaderImpl extends BasicKeywordReader implements
		KeywordReader {
	private static final String KEYWORD = "INCLUDE";

	private static final short START_COLUMN = 1;
	
	public String getKeyword() {
		return KEYWORD;
	}

	public boolean iskeyReader(String key) {
		if (key == null)
			return false;
		return KEYWORD.equalsIgnoreCase(key.trim());
	}

	public int read(UnitExporter exporter, List<Element> dataList,
			BasicSheet sheet, HSSFWorkbook workBook, int currentRow) {
		setExporter(exporter);
		short column = START_COLUMN;
		String sheetName = sheet.getCellValue(currentRow, column);
		while (column <= MAX_COLUMN && !StringUtils.isEmpty(sheetName)
				&& !this.isComment(sheetName)) {
			// Set tmpSheet = getSheetByName(book, shtName)
			BasicSheet includeSheet = new BasicSheet(sheetName, workBook);
			if (includeSheet.getSheet() == null) {
				Logger.println("sheet " + sheetName + " can't be found");
				column++;
				continue;
			}
			if (!exporter.hasReaded(sheetName)) {
				exporter.ExportToMapContainer(dataList,sheetName, workBook);
			}
			column++;
		}
		return ++currentRow;
	}


}
