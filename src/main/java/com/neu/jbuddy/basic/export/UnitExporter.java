package com.neu.jbuddy.basic.export;


import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neu.jbuddy.basic.container.Element;

public interface UnitExporter {

	int getMaxEmptyRowCnt();

	void setMaxEmptyRowCnt(int maxEmptyRowCnt);

	void ExportToMapContainer(List<Element> sheetDataList,String name,
			HSSFWorkbook workBook);

	String ExportToText(HSSFSheet sheet, HSSFWorkbook workBook);
	
	boolean hasReaded(String sheetName);
	void reset();
	boolean isKeyword(String value);
}
