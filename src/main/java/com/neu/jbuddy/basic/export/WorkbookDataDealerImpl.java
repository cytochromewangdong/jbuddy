package com.neu.jbuddy.basic.export;

import java.util.ArrayList;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neu.jbuddy.basic.container.Element;
import com.neu.jbuddy.basic.convert.TypeTranslateFactory;

public class WorkbookDataDealerImpl implements WorkbookDataDealer {
	private String caseName = "case";

	private final int MAX_CASE = 100;

	public List<List<Element>> Export(HSSFWorkbook workBook) {
		List<List<Element>> caseAllList = new ArrayList<List<Element>>(0);
		for (int i = 0; i < MAX_CASE; i++) {
			UnitExporter exporter = ExportFactory.create();
			String sheetName = caseName + (i + 1);
			HSSFSheet sheet = workBook.getSheet(sheetName);
			if (sheet == null) {
				break;
			}
			// exporter.reset();
			List<Element> aList = new ArrayList<Element>();
			caseAllList.add(aList);
			exporter.ExportToMapContainer(aList, sheetName, workBook);
			TypeTranslateFactory.postAllEvent(aList);
		}
		return caseAllList;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public List<Element> Export(HSSFWorkbook workBook, String sheetName) {
		UnitExporter exporter = ExportFactory.create();
		HSSFSheet sheet = workBook.getSheet(sheetName);
		if (sheet == null) {
			return new ArrayList<Element>();
		}
		List<Element> aList = new ArrayList<Element>();
		exporter.ExportToMapContainer(aList, sheetName, workBook);
		return aList;
	}

}
