package com.analytics.converter.constants;

/**
 * Constants used in data conversion.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class FileConverterConstants {

	// Datatypes used in metadata
	public static final String DATA_TYPE_DATE = "date";
	public static final String DATA_TYPE_NUMERIC = "numeric";
	public static final String DATA_TYPE_STRING = "string";

	public static final String METADATA_COLUMN_DELIMITER = ",";

	public static final String ROW_DELIMITER = "\n";
	
	// Date format
	public static final String OUTPUT_DATE_FORMAT = "dd/MM/yyyy";
	public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
	
	// Batch size used to insert csv resultset to output file
	public static final int BATCH_SIZE = 10000;

}
