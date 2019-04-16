package com.analytics.converter.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.analytics.converter.constants.FileConverterConstants;

/**
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class FileUtil {

	/**
	 * Writes the processed data to output file
	 * 
	 * @param outFile
	 * @param isAppend
	 * @param resultset
	 */
	public static void writeToFile(String outFile, boolean isAppend, List<String> resultset) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(outFile, isAppend));
			for (String line : resultset) {
				writer.write(line + FileConverterConstants.ROW_DELIMITER);
			}
			writer.flush();
			writer.close();
		} finally {
			if(writer != null)
				writer.close();
		}
	}
}
