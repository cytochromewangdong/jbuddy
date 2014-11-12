package com.neu.jbuddy.basic.export;

public class ExportFactory {
	public static UnitExporter create() {
		return new SheetExporterImpl();
	}
}
