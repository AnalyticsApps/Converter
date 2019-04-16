package com.analytics.converter.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.analytics.converter.constants.FileConverterConstants;
import com.analytics.converter.dao.Metadata;
import com.analytics.converter.util.FileUtil;

/**
 * Read the date file and convert to csv file based on metadata
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class DataFileProcessor implements DataProcessor{


	private static final String NUMERIC_REGEX_CHECK = "-?\\d+(\\.\\d+)?";
	private static final String DOUBLE_QUOTES = "\"";
	private static final Logger LOG = Logger.getLogger(DataFileProcessor.class);


	/**
	 * Converts the input file based on metadata and writes to a csv file.
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
		StringBuilder header = new StringBuilder();
		// Adding the column details in the output file & check the total length of the metadata columns defined. 
		for (Metadata metadata : metadataList) {
			if(isFirstLoop) {
				header.append(metadata.getColName());
				isFirstLoop = false;
			}else {
				header.append(FileConverterConstants.METADATA_COLUMN_DELIMITER + metadata.getColName());
			}
			metadataTotalLength +=  metadata.getColLength();
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
				if(index % FileConverterConstants.BATCH_SIZE == 0) {
					if(isFirstBatch) {
						String msg = String.format(" Writing the first batch of resultset to %s", outFile);
						LOG.info(msg);
						System.out.println(msg);
						FileUtil.writeToFile(outFile, false, resultset);
						isFirstBatch = false;
					}else {
						String msg = String.format(" Writing the %d batch of resultset to %s", batchLoop, outFile);
						LOG.debug(msg);
						System.out.println(msg);
						//Append the processed data to the output file
						FileUtil.writeToFile(outFile, true, resultset);
						batchLoop++;
					}
					resultset.clear();
				}					
				index++;
			}
			
			LOG.debug(String.format(" Writing the remaining resultset to %s", outFile));	
			//Append the remaining processed data to the output file
			FileUtil.writeToFile(outFile, true, resultset);

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
		StringBuilder valueBuilder = new StringBuilder();
		int startIndex = 0;
		int endIndex = 0;
		int index = 0;
		SimpleDateFormat dateformatInp = new SimpleDateFormat(FileConverterConstants.INPUT_DATE_FORMAT);
		SimpleDateFormat dateformatOut = new SimpleDateFormat(FileConverterConstants.OUTPUT_DATE_FORMAT);
		
		// Check length of line is greater than the total length of metadata
		if(metadataTotalLength > line.length()) {
			// Throw ParseException mentioning length of line is less than total length of metadata
			throw new ParseException("Error in parsing ( Length of line is less than total length of metadata columns )", 0);
		}
		
		for (Metadata metadata : metadataList) {
			if (index == 0) {
				endIndex = metadata.getColLength();
				valueBuilder.append(getValue(line, startIndex, endIndex, dateformatInp, dateformatOut, metadata));
				index++;
			} else {
				startIndex = endIndex;
				endIndex = startIndex + metadata.getColLength();
				valueBuilder.append(FileConverterConstants.METADATA_COLUMN_DELIMITER
						+ getValue(line, startIndex, endIndex, dateformatInp, dateformatOut, metadata));
			}
		}
		
		LOG.debug(" Transformed Line : " + valueBuilder.toString());
		return valueBuilder.toString();
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
		if (FileConverterConstants.DATA_TYPE_DATE.equals(metadata.getColType())) {
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
		if (FileConverterConstants.DATA_TYPE_STRING.equals(metadata.getColType())) {
			if(val.indexOf(",") != -1) {
				val = DOUBLE_QUOTES + val + DOUBLE_QUOTES;
			}
		}
		
		// For Numeric, check data is numeric. We use regular expression to verify big numbers.
		if (FileConverterConstants.DATA_TYPE_NUMERIC.equals(metadata.getColType())) {
			boolean numeric = val.matches(NUMERIC_REGEX_CHECK);
			if(!numeric) {
				// Throw ParseException mentioning invalid numeric value
				throw new ParseException("Invalid numeric data (Expected numeric value)", startIndex);
			}
		}


		return val;
	}

}
