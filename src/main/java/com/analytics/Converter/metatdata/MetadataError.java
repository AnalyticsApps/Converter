package com.analytics.Converter.metatdata;

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
		StringBuffer retStrBuf = new StringBuffer();
		retStrBuf.append("[ Line No = " + this.lineNo);
		retStrBuf.append(" , Error Details = " + this.errorDetails);
		retStrBuf.append(" , Line = " + this.line + " ]");
		return retStrBuf.toString();
	}

}
