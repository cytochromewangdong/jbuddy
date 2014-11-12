package com.neu.jbuddy.basic.export;

import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neu.jbuddy.basic.container.Element;

public interface WorkbookDataDealer {
	List<List<Element>>Export(HSSFWorkbook workBook);
	List<Element>Export(HSSFWorkbook workBook,String sheetName);
}
