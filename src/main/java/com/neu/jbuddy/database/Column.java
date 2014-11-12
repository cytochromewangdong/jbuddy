package com.neu.jbuddy.database;

public class Column {
	private String columnName;

	private int dataType;

	private String dataTypeName;

	private int columnSize;

	private int decimalDigits;

	private int nullable;

	private boolean isPrimary;

	/**
	 * @return Returns the columnName.
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName
	 *            The columnName to set.
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return Returns the columnSize.
	 */
	public int getColumnSize() {
		return columnSize;
	}

	/**
	 * @param columnSize
	 *            The columnSize to set.
	 */
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	/**
	 * @return Returns the dataType.
	 */
	public int getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            The dataType to set.
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return Returns the dataTypeName.
	 */
	public String getDataTypeName() {
		return dataTypeName;
	}

	/**
	 * @param dataTypeName
	 *            The dataTypeName to set.
	 */
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	/**
	 * @return Returns the decimalDigits.
	 */
	public int getDecimalDigits() {
		return decimalDigits;
	}

	/**
	 * @param decimalDigits
	 *            The decimalDigits to set.
	 */
	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	/**
	 * @return Returns the isPrimary.
	 */
	public boolean isPrimary() {
		return isPrimary;
	}

	/**
	 * @param isPrimary
	 *            The isPrimary to set.
	 */
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}


	/**
	 * @return Returns the nullable.
	 */
	public int getNullable() {
		return nullable;
	}

	/**
	 * @param nullable
	 *            The nullable to set.
	 */
	public void setNullable(int nullable) {
		this.nullable = nullable;
	}
}
