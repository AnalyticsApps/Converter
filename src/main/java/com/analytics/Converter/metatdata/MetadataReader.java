package com.analytics.Converter.metatdata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

public class MetadataReader {

	private static final Logger LOG = Logger.getLogger(MetadataReader.class);

	public enum Headers {
		NAME, LENGTH, TYPE
	}

	/**
	 * Read the metadata and load to List<Metadata>
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public List<Metadata> readMetadata(String path) throws MetadataException {

		List<Metadata> metadataList = new ArrayList<Metadata>();
		Metadata metadata = null;
		List<MetadataError> metadataErrList = new ArrayList<MetadataError>();
		MetadataError mdError = null;
		MetadataError mdTypeError = null;
		MetadataError mdNumericError = null;

		Iterable<CSVRecord> records = null;
		try {
			Reader in = new FileReader(path);
			records = CSVFormat.DEFAULT.withHeader(Headers.class).parse(in);
		} catch (FileNotFoundException e) {
			LOG.error(" Metadata File Not Found Error. ", e);
			throw new MetadataException(" Metadata File Not Found Error. ");
		} catch (IOException e) {
			LOG.error(" IO Error in reading metadata file ", e);
			throw new MetadataException(" IO Error in reading metadata file ");
		}

		// Read the metadata and load it to Metadata Object.
		// If there is any error in metadata then add it to List<MetadataError> and
		// continue processing.
		for (CSVRecord record : records) {
			try {
				mdTypeError = validateDataType(record);
				if (mdTypeError != null) {
					metadataErrList.add(mdTypeError);
					continue;
				}
				mdNumericError = validateNumericLength(record);
				if (mdNumericError != null) {
					metadataErrList.add(mdNumericError);
					continue;
				}

				metadata = new Metadata(record.get(Headers.NAME).trim(), record.get(Headers.LENGTH).trim(),
						record.get(Headers.TYPE).trim().toLowerCase());
				metadataList.add(metadata);
			} catch (IllegalArgumentException e) {
				mdError = new MetadataError(record.getRecordNumber(), record.toString(),
						" The metadata structure is invalid - less columns");
				metadataErrList.add(mdError);
			}

		}

		if (!metadataErrList.isEmpty()) {
			LOG.error(" Error in validating Metadata.");
			LOG.error(String.format(" Found %d errors in validating Metadata.", metadataErrList.size()));
			for (MetadataError err : metadataErrList) {
				LOG.error(err);
			}
			throw new MetadataException(String.format(" Error in processing metadata file. Found %d errors in validating Metadata. Refer /opt/FileConverter/log/application.log for more details.", metadataErrList.size()));
		}

		return metadataList;
	}

	/**
	 * Validate the length of datatype.
	 * 
	 * @param record
	 * @return
	 */
	private MetadataError validateNumericLength(CSVRecord record) {
		MetadataError mdError = null;
		try {
			int val = Integer.parseInt(record.get(Headers.LENGTH).trim());
			if (val <= 0) {
				mdError = new MetadataError(record.getRecordNumber(), getLine(record)  ,
						" The column length should be greater than 0");
			}
		} catch (NumberFormatException e) {
			mdError = new MetadataError(record.getRecordNumber(), getLine(record),
					" The column length is not numeric");
		}
		return mdError;
	}
	
	private String getLine(CSVRecord record) {
		return record.get(0) + MetadataConstants.METADATA_COLUMN_DELIMITER +
				record.get(1) + MetadataConstants.METADATA_COLUMN_DELIMITER +
				record.get(2);
		
	}

	/**
	 * Validate the metadata types. The valid types are Date, String, Numeric
	 * 
	 * @param record
	 * @return
	 */
	private MetadataError validateDataType(CSVRecord record) {

		MetadataError mdError = null;
		switch (record.get(Headers.TYPE).trim().toLowerCase()) {
		case MetadataConstants.DATA_TYPE_DATE:
			break;
		case MetadataConstants.DATA_TYPE_STRING:
			break;
		case MetadataConstants.DATA_TYPE_NUMERIC:
			break;
		default:
			mdError = new MetadataError(record.getRecordNumber(), record.toString(),
					" The metadata type is not valid. Valid types are date, string or numeric");
			break;
		}

		return mdError;
	}


}
