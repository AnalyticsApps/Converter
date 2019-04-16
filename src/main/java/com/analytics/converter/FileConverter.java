package com.analytics.converter;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.analytics.converter.dao.Metadata;
import com.analytics.converter.data.DataFileProcessor;
import com.analytics.converter.data.DataProcessor;
import com.analytics.converter.metatdata.FileMetadataReader;
import com.analytics.converter.metatdata.MetadataException;
import com.analytics.converter.metatdata.MetadataReader;

/**
 * Starting point of the application. Get the input and initiate the data conversion.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class FileConverter {
	
	private static final Logger LOG = Logger.getLogger(FileConverter.class);
	
	public static void main(String[] args) throws Exception{
		LOG.info(" Initiating the File Converter");
		
		//Get the input details
		Scanner in = new Scanner(System.in);
		System.out.print("\n\n Path to metadata file: ");
		String metadataFile = in.nextLine();
		System.out.print("\n\n Path to data file: ");
		String dataFile = in.nextLine();
		System.out.print("\n\n Path to Out file: ");
		String outFile = in.nextLine();
		
		LOG.info(String.format(" Metadata Path: %s Input File Path: %s Output File Path: %s", metadataFile, dataFile,
				outFile));
		
		FileConverter converter = new FileConverter();
		converter.process(metadataFile, dataFile, outFile);
		
		System.out.println("\n\n File conversion completed. \n\n");
	}

	/**
	 * Process the date file based on metadata and write to output file
	 * 
	 * @param metadataFile
	 * @param dataFile
	 * @param outFile
	 * @throws MetadataException
	 * @throws IOException
	 */
	private void process(String metadataFile, String dataFile, String outFile)
			throws MetadataException, Exception {
		MetadataReader reader = new FileMetadataReader();
		List<Metadata> metadataList = reader.readMetadata(metadataFile);
		LOG.info(" Extracted Metadata Info: " + metadataList.toString());
		
		DataProcessor dpInst = new DataFileProcessor();
		dpInst.convert(dataFile, metadataList, outFile);
	}

}
