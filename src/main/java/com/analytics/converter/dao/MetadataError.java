package com.analytics.converter.dao;

/**
 * Hold the error details while validating the metadata input file.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class MetadataError {

	private long lineNo;
	private String errorDetails;
	private String line;

	/**
	 * @param lineNo
	 * @param errorDetails
	 */
	public MetadataError(long lineNo, String line, String errorDetails) {
		super();
		this.lineNo = lineNo;
		this.errorDetails = errorDetails;
		this.line = line;
	}

	/**
	 * @param lineNo
	 *            the lineNo to set
	 */
	public void setLineNo(long lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * @param errorDetails
	 *            the errorDetails to set
	 */
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	/**
	 * @param line
	 *            the line to set
	 */
	public void setLine(String line) {
		this.line = line;
	}

	/**
	 * @return the lineNo
	 */
	public long getLineNo() {
		return lineNo;
	}

	/**
	 * @return the errorDetails
	 */
	public String getErrorDetails() {
		return errorDetails;
	}

	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}

	@Override
	public String toString() {
		StringBuilder retStrBuilder = new StringBuilder();
		retStrBuilder.append("[ Line No = " + this.lineNo);
		retStrBuilder.append(" , Error Details = " + this.errorDetails);
		retStrBuilder.append(" , Line = " + this.line + " ]");
		return retStrBuilder.toString();
	}

}
