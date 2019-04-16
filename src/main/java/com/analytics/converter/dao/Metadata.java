package com.analytics.converter.dao;

/**
 * Hold Metdata information.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class Metadata {

	private String colName;
	private int colLength;
	private String colType;

	/**
	 * @param colName
	 * @param colLength
	 * @param colType
	 */
	public Metadata(String colName, int colLength, String colType) {
		super();
		this.colName = colName;
		this.colLength = colLength;
		this.colType = colType;
	}

	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}

	/**
	 * @return the colLength
	 */
	public int getColLength() {
		return colLength;
	}

	/**
	 * @return the colType
	 */
	public String getColType() {
		return colType;
	}

	@Override
	public String toString() {
		StringBuilder retStrBuilder = new StringBuilder();
		retStrBuilder.append("[ colName = " + this.colName);
		retStrBuilder.append(" , colLength = " + this.colLength);
		retStrBuilder.append(" , colType = " + this.colType + " ]");
		return retStrBuilder.toString();
	}

}
