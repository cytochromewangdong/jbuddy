package com.neu.jbuddy.basic.export;

import java.util.ArrayList;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neu.jbuddy.basic.read.KeywordReader;
import com.neu.jbuddy.basic.read.Reader;
import com.neu.jbuddy.framework.excel.BasicSheet;

public abstract class BasicExporter implements UnitExporter {
	private int maxEmptyRowCnt = 50;
	private final int KEY_COLUMN=0;
	protected List<KeywordReader> keyWordHandlers = new ArrayList<KeywordReader>() ;
	
	protected void init(BasicSheet sheet, HSSFWorkbook workBook) {
	}
	
	/**
	 * @return Returns the maxEmptyRowCnt.
	 */
	public int getMaxEmptyRowCnt() {
		return maxEmptyRowCnt;
	}

	protected String getKeyword(BasicSheet sheet, int rowNo) {
		return sheet.getCellValue(rowNo,KEY_COLUMN);
	}

	/**
	 * @param maxEmptyRowCnt
	 *            The maxEmptyRowCnt to set.
	 */
	public void setMaxEmptyRowCnt(int maxEmptyRowCnt) {
		this.maxEmptyRowCnt = maxEmptyRowCnt;
	}

	protected Reader getHandler(String keyWord) {
		for (KeywordReader reader : keyWordHandlers) {
			if (reader.iskeyReader(keyWord)) {
				return reader;
			}
		}
		return null;
	}

	public boolean isKeyword(String value) {
		return getHandler(value) != null;
	}
}
