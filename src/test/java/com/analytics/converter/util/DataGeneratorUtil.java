package com.analytics.converter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.analytics.converter.dao.Metadata;
import com.analytics.converter.metatdata.FileMetadataReader;
import com.analytics.converter.metatdata.MetadataReader;

/**
 * Utility to generate data
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class DataGeneratorUtil {

	private static final String COMMA_SEPERATOR = ",";
	private static final String NEWLINE = "\n";

	/**
	 * Generate the testdata based on no# of record needed.
	 * @param record
	 * @param listMetadata
	 * @param inputFile
	 * @param outputFile
	 */
	public static void generate(long record, List<Metadata> listMetadata, String inputFile, String outFile) throws IOException {
		Metadata columnID = listMetadata.get(0);
		Metadata columnFName = listMetadata.get(1);
		Metadata columnLName = listMetadata.get(2);
		
		StringBuilder strBuilderInp = new StringBuilder();
		StringBuilder strBuilderOut = new StringBuilder();
		
		// Add the Header
		strBuilderOut.append(columnID.getColName());
		strBuilderOut.append(COMMA_SEPERATOR);
		strBuilderOut.append(columnFName.getColName());
		strBuilderOut.append(COMMA_SEPERATOR);
		strBuilderOut.append(columnLName.getColName());
		strBuilderOut.append(NEWLINE);
		
		for (long i = 1; i <= record; i++) {
			strBuilderInp.append(String.format("%1$"+columnID.getColLength()+ "s", i));
			strBuilderOut.append(i);
			strBuilderOut.append(COMMA_SEPERATOR);
			
			String fName = getRandomString(columnFName.getColLength());
			strBuilderInp.append(String.format("%1$"+columnFName.getColLength()+ "s", fName));
			strBuilderOut.append(fName);
			strBuilderOut.append(COMMA_SEPERATOR);

			String lName = getRandomString(columnLName.getColLength());
			strBuilderInp.append(String.format("%1$"+columnLName.getColLength()+ "s", lName));
			strBuilderOut.append(lName);
			
			strBuilderInp.append(NEWLINE);
			strBuilderOut.append(NEWLINE);
		}
		
		writeToFile(inputFile, strBuilderInp.toString());
		writeToFile(outFile, strBuilderOut.toString());
	
	}

	/**
	 * Generate Random Sting of random size.
	 *  
	 * @param maxLen
	 * @return
	 */
	private static String getRandomString(int maxLen) {
		String stringSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		int stringSetLen = stringSet.length();

		Random random = new Random();
		int randLen = random.nextInt(maxLen);
		if(randLen == 0)
			randLen = random.nextInt(maxLen);
		
		StringBuilder sb = new StringBuilder(randLen);

		for (int i = 0; i < randLen; i++) {
			int index = random.nextInt(stringSetLen);
			if(index == 0)
				index = random.nextInt(stringSetLen);
			
			sb.append(stringSet.charAt(index));
		}
		return sb.toString();
	}
	
	/**
	 * Writes the generated data to output file
	 * 
	 * @param outFile
	 * @param data
	 */
	public static void writeToFile(String outFile, String data) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(outFile, false));
			writer.write(data);
			writer.flush();
			writer.close();
		} finally {
			if(writer != null)
				writer.close();
		}
	}
}
