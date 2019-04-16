package com.analytics.converter.data;

import java.io.IOException;
import java.util.List;

import com.analytics.converter.dao.Metadata;

/**
 * @author Nisanth Simon
 * @version 1.0
 */
public interface DataProcessor {
	
	/**
	 * Converts the input file based on metadata and writes to output file.
	 * 
	 * @param dataFile
	 * @param metadataList
	 * @param outFile
	 * @throws IOException
	 */
	public void convert(String dataFile, List<Metadata> metadataList, String outFile) throws Exception;
}
