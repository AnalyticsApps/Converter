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

	public static void main(String[] args) {
		LOG.info(" Initiating the File Converter");
		
		//Get the input details
		Scanner in = new Scanner(System.in);
		System.out.print("Path to metadata file: ");
		String metaPath = in.nextLine();
		System.out.print("Path to input file: ");
		String inpFilePath = in.nextLine();
		System.out.print("Path to Out file: ");
		String outFilePath = in.nextLine();
		
		LOG.debug(String.format(" Metadata Path: %s Input File Path: %s Output File Path %s", metaPath, inpFilePath,
				outFilePath));
				
		MetadataReader reader = new MetadataReader();
		List<Metadata> metadataList = reader.readMetadata(metaPath);
		
		Converter inst = new Converter();
		inst.convert(inpFilePath, metadataList, outFilePath);
		
		LOG.info(" File conversion completed.");
	}

	/**
	 * Converts the input file based on metadata and writes to a file.
	 * 
	 * @param path
	 * @param metadataList
	 * @param outFilePath
	 */
	public void convert(String path, List<Metadata> metadataList, String outFilePath) {
		String line = "";
		long index = 1;
		boolean isFirstBatch = true;
		// Holds the output to write to output file
		List<String> resultset = new ArrayList<String>();
		
		int temp = 0;
		// Adding the column details in the output file
		for (Metadata metadata : metadataList) {
			if(temp == 0) {
				resultset.add(metadata.getColName());
				temp++;
			}else {
				resultset.add(MetadataConstants.METADATA_COLUMN_DELIMITER + metadata.getColName());
			}
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			while ((line = br.readLine()) != null) {
				try {
					resultset.add(transform(line, metadataList));
				} catch (ParseException e) {
					LOG.error(String.format(" %s in processing for line - %n: %s", e.getMessage(), (index+1), line));					
				}
				
				// Process the data in a batch to minimize the memory footprint and helps in processing huge data file. 
				if(index % BATCH_SIZE == 0) {
					if(isFirstBatch) {
						writeToFile(outFilePath, false, resultset);
					}else {
						//Append the processed data to the output file
						writeToFile(outFilePath, true, resultset);
					}
					resultset.clear();
				}					
				index++;
			}
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
	 * @return
	 * @throws ParseException
	 */
	private String transform(String line, List<Metadata> metadataList) throws ParseException {
		StringBuffer valueBuff = new StringBuffer();
		int startIndex = 0;
		int endIndex = 0;
		int index = 0;
		SimpleDateFormat dateformatInp = new SimpleDateFormat(INPUT_DATE_FORMAT);
		SimpleDateFormat dateformatOut = new SimpleDateFormat(OUTPUT_DATE_FORMAT);
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
				throw new ParseException(" Date parse error ", startIndex);
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
				throw new ParseException(" Numeric value invalid error ", startIndex);
			}
		}


		return val;
	}

	/**
	 * Writes the processed data to output file
	 * 
	 * @param path
	 * @param isAppend
	 * @param resultset
	 */
	private void writeToFile(String path, boolean isAppend, List<String> resultset) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path, isAppend));
			for (String line : resultset) {
				writer.write(line + MetadataConstants.ROW_DELIMITER);
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			LOG.error(" IO Error in writing outputfile ", e);
			System.exit(1);
		}
	}

}
