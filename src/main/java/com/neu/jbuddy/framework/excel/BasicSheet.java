package com.neu.jbuddy.framework.excel;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neu.jbuddy.utils.StringUtils;

public class BasicSheet {
	private HSSFSheet sheet;

	private String name = null;

	public BasicSheet(String name, HSSFWorkbook workBook) {
		sheet = workBook.getSheet(name);
		name = this.getName();
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the sheet.
	 */
	public HSSFSheet getSheet() {
		return sheet;
	}

	/**
	 * @param row
	 * @param column
	 * @return
	 */
	public String getCellValue(int row, int column) {
		HSSFRow hssfRow = sheet.getRow(row);
		if (hssfRow == null || hssfRow.getCell(column) == null) {
			return "";
		}
		HSSFCell cell = hssfRow.getCell(column);
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		}
		//cell.getRichStringCellValue().getString();
		return StringUtils.rightTrim(cell.getStringCellValue());// cell.getRichStringCellValue().getString();
	}
}
