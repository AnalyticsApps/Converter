package com.analytics.Converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.analytics.Converter.metatdata.Metadata;
import com.analytics.Converter.metatdata.MetadataConstants;
import com.analytics.Converter.metatdata.MetadataReader;

public class Converter {

	private static final String OUTPUT_DATE_FORMAT = "dd/MM/yyyy";
	private static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
	private static final int BATCH_SIZE = 10000;
	private static final Logger LOG = Logger.getLogger(Converter.class);

	public static void main(String[] args) throws Exception{
		System.out.println(" Initiating the File Converter");
		
		//Get the input details
		Scanner in = new Scanner(System.in);
		System.out.print("Path to metadata file: ");
		String metadataFile = in.nextLine();
		System.out.print("Path to data file: ");
		String dataFile = in.nextLine();
		System.out.print("Path to Out file: ");
		String outFile = in.nextLine();
		
		LOG.info(String.format(" Metadata Path: %s Input File Path: %s Output File Path: %s", metadataFile, dataFile,
				outFile));
				
		MetadataReader reader = new MetadataReader();
		List<Metadata> metadataList = reader.readMetadata(metadataFile);
		LOG.info(" Extracted Metadata Info: " + metadataList.toString());
		
		Converter inst = new Converter();
		inst.convert(dataFile, metadataList, outFile);
		
		System.out.print(" File conversion completed.");
	}

	/**
	 * Converts the input file based on metadata and writes to a file.
	 * 
	 * @param dataFile
	 * @param metadataList
	 * @param outFile
	 */
	public void convert(String dataFile, List<Metadata> metadataList, String outFile) throws IOException{
		String line = "";
		long index = 1;
		long batchLoop = 2;
		boolean isFirstBatch = true;
		// Holds the output to write to output file
		List<String> resultset = new ArrayList<String>();
		int metadataTotalLength = 0;
		
		boolean isFirstLoop = true;
		StringBuffer header = new StringBuffer();
		// Adding the column details in the output file & check the total length of the metadata columns defined. 
		for (Metadata metadata : metadataList) {
			if(isFirstLoop) {
				header.append(metadata.getColName());
				isFirstLoop = false;
			}else {
				header.append(MetadataConstants.METADATA_COLUMN_DELIMITER + metadata.getColName());
			}
			metadataTotalLength +=  Integer.parseInt(metadata.getColLength());
		}
		resultset.add(header.toString());
		
		LOG.info(" Resultset after adding header details: " + resultset.toString());
		
		try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
			while ((line = br.readLine()) != null) {
				try {
					resultset.add(transform(line, metadataList, metadataTotalLength));
				} catch (ParseException e) {
					String err = String.format(" %s in processing the line - %d: %s", e.getMessage(), (index+1), line);
					LOG.error(err);
					System.out.println(err);
				}
				
				// Process the data in a batch to minimize the memory footprint and helps in processing huge data file. 
				if(index % BATCH_SIZE == 0) {
					if(isFirstBatch) {
						String msg = String.format(" Writing the first batch of resultset to %s", outFile);
						LOG.info(msg);
						System.out.println(msg);
						writeToFile(outFile, false, resultset);
					}else {
						String msg = String.format(" Writing the %d batch of resultset to %s", batchLoop, outFile);
						LOG.debug(msg);
						System.out.println(msg);
						//Append the processed data to the output file
						writeToFile(outFile, true, resultset);
						batchLoop++;
					}
					resultset.clear();
				}					
				index++;
			}
			
			LOG.debug(String.format(" Writing the remaining resultset to %s", outFile));	
			//Append the remaining processed data to the output file
			writeToFile(outFile, true, resultset);

		} catch (IOException e) {
			LOG.error(" Failed to convert the file. ", e);
			System.exit(1);
		}

	}

	/**
	 * Transform the input file based on metadata.
	 * 
	 * @param line
	 * @param metadataList
	 * @oaram metadataTotalLength
	 * @return
	 * @throws ParseException
	 */
	private String transform(String line, List<Metadata> metadataList, int metadataTotalLength) throws ParseException {
		StringBuffer valueBuff = new StringBuffer();
		int startIndex = 0;
		int endIndex = 0;
		int index = 0;
		SimpleDateFormat dateformatInp = new SimpleDateFormat(INPUT_DATE_FORMAT);
		SimpleDateFormat dateformatOut = new SimpleDateFormat(OUTPUT_DATE_FORMAT);
		
		// Check length of line is greater than the total length of metadata
		if(metadataTotalLength > line.length()) {
			// Throw ParseException mentioning length of line is less than total length of metadata
			throw new ParseException("Error in parsing ( Length of line is less than total length of metadata columns )", 0);
		}
		
		for (Metadata metadata : metadataList) {
			if (index == 0) {
				endIndex = Integer.parseInt(metadata.getColLength());
				valueBuff.append(getValue(line, startIndex, endIndex, dateformatInp, dateformatOut, metadata));
				index++;
			} else {
				startIndex = endIndex;
				endIndex = startIndex + Integer.parseInt(metadata.getColLength());
				valueBuff.append(MetadataConstants.METADATA_COLUMN_DELIMITER
						+ getValue(line, startIndex, endIndex, dateformatInp, dateformatOut, metadata));
			}
		}
		
		LOG.debug(" Transformed Line : " + valueBuff.toString());
		return valueBuff.toString();
	}

	/**
	 * Extract the values from line
	 * 
	 * @param line
	 * @param startIndex
	 * @param endIndex
	 * @param dateformatInp
	 * @param dateformatOut
	 * @param metadata
	 * @return
	 * @throws ParseException
	 */
	private String getValue(String line, int startIndex, int endIndex, SimpleDateFormat dateformatInp,
			SimpleDateFormat dateformatOut, Metadata metadata) throws ParseException {
		
		 String val = line.substring(startIndex, endIndex).trim();

		// For date, transform from yyyy-MM-dd to dd/MM/yyyy
		if (MetadataConstants.DATA_TYPE_DATE.equals(metadata.getColType())) {
			Date dt = null;
			try {
				dt = dateformatInp.parse(val);
			} catch (ParseException e) {
				// Throw ParseException mentioning error in parsing date
				throw new ParseException("Error in parsing date ( Expected date format is yyyy-MM-dd )", startIndex);
			}
			val = dateformatOut.format(dt);
		}
		
		// For String, check data has comma, if yes then enclose value in double quotes
		if (MetadataConstants.DATA_TYPE_STRING.equals(metadata.getColType())) {
			if(val.indexOf(",") != -1) {
				val = "\"" + val + "\"";
			}
		}
		
		// For Numeric, check data is numeric. We use regular expression to verify big numbers.
		if (MetadataConstants.DATA_TYPE_NUMERIC.equals(metadata.getColType())) {
			boolean numeric = val.matches("-?\\d+(\\.\\d+)?");
			if(!numeric) {
				// Throw ParseException mentioning invalid numeric value
				throw new ParseException("Invalid numeric data (Expected numeric value)", startIndex);
			}
		}


		return val;
	}

	/**
	 * Writes the processed data to output file
	 * 
	 * @param outFile
	 * @param isAppend
	 * @param resultset
	 */
	private void writeToFile(String outFile, boolean isAppend, List<String> resultset) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(outFile, isAppend));
			for (String line : resultset) {
				writer.write(line + MetadataConstants.ROW_DELIMITER);
			}
			writer.flush();
			writer.close();
		} finally {
			if(writer != null)
				writer.close();
		}
	}
}
