package com.analytics.Converter.metatdata;

public class Metadata {

	private String colName;
	private String colLength;
	private String colType;

	/**
	 * @param colName
	 * @param colLength
	 * @param colType
	 */
	public Metadata(String colName, String colLength, String colType) {
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
	public String getColLength() {
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
		StringBuffer retStrBuf = new StringBuffer();
		retStrBuf.append("[ colName = " + this.colName);
		retStrBuf.append(" , colLength = " + this.colLength);
		retStrBuf.append(" , colType = " + this.colType + " ]");
		return retStrBuf.toString();
	}

}
