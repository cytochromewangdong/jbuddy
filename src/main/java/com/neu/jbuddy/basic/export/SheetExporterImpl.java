package com.neu.jbuddy.basic.export;

import java.util.ArrayList;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neu.jbuddy.basic.container.Element;
import com.neu.jbuddy.basic.read.IncludeReaderImpl;
import com.neu.jbuddy.basic.read.MapReaderImpl;
import com.neu.jbuddy.basic.read.Reader;
import com.neu.jbuddy.basic.read.TableReaderImpl;
import com.neu.jbuddy.framework.excel.BasicSheet;

public class SheetExporterImpl extends BasicExporter implements SheetExporter {

	public SheetExporterImpl() {
		keyWordHandlers.add(new TableReaderImpl());
		keyWordHandlers.add(new MapReaderImpl());
		keyWordHandlers.add(new IncludeReaderImpl());
	}

	List<String> sheetList = new ArrayList<String>();

	public void ExportToMapContainer(List<Element> sheetDataList,String name, HSSFWorkbook workBook) {
		BasicSheet sheet = new BasicSheet(name, workBook);
		int rowNo = 0;
		int skipRow = 0;
		this.init(sheet, workBook);
		sheetList.add(name);
		while (skipRow < this.getMaxEmptyRowCnt()) {
			String keyword = getKeyword(sheet, rowNo);
			Reader reader = getHandler(keyword);
			if (reader != null) {
				skipRow = 0;
				rowNo = reader
						.read(this, sheetDataList, sheet, workBook, rowNo);
			} else {
				skipRow++;
				rowNo++;
			}
		}
	}

	public String ExportToText(HSSFSheet sheet, HSSFWorkbook workBook) {
		// this.init(sheet, workBook);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void init(BasicSheet sheet, HSSFWorkbook workBook) {
		super.init(sheet, workBook);

	}

	public boolean hasReaded(String sheetName) {
		if (sheetName == null)
			return false;
		for (String name : sheetList) {
			if (sheetName.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	public void reset() {
		sheetList = new ArrayList<String>();

	}
}
