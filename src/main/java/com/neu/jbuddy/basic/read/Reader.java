package com.neu.jbuddy.basic.read;


import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.neu.jbuddy.basic.container.Element;
import com.neu.jbuddy.basic.export.UnitExporter;
import com.neu.jbuddy.framework.excel.BasicSheet;

public interface Reader {
	int read(UnitExporter exporter, List<Element> dataList,BasicSheet sheet,HSSFWorkbook workBook, int currentRow);
}
