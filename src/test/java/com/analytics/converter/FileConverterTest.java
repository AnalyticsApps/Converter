package com.analytics.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import org.junit.jupiter.api.Test;

import com.analytics.converter.dao.Metadata;
import com.analytics.converter.data.DataFileProcessor;
import com.analytics.converter.data.DataProcessor;
import com.analytics.converter.metatdata.FileMetadataReader;
import com.analytics.converter.metatdata.MetadataReader;
import com.analytics.converter.util.DataGeneratorUtil;

/**
 * Test Cases for testing File Conversion
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
class ConverterTest {

	@Test
	public void convertTest1() throws Exception {
		/**
		 * Test Case 1 - Convert the data file based on metadata.
		 *               Write the records to the output file.
		 * 
		 * Input file: src/test/resources/converter/testcase_1/input.txt
		 * Metadata file: src/test/resources/converter/testcase_1/metadata.txt
		 * Expected output file: src/test/resources/converter/testcase_1/expectedOutput.txt
		 * Output file: src/test/resources/converter/testcase_1/output.txt
		 * 
		 */
		String testcase1Path = "src/test/resources/converter/testcase_1";
		String metadataPath = new File(testcase1Path + "/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);

		String inputFile = new File(testcase1Path + "/input.txt").getAbsolutePath();
		File outFile = new File(testcase1Path + "/output.txt");
		outFile.delete();
		DataProcessor inst = new DataFileProcessor();
		inst.convert(inputFile, listMetadata, outFile.getAbsolutePath());

		File expectedOutputFile = new File(testcase1Path + "/expectedOutput.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));

	}

	@Test
	public void convertTest2() throws Exception {
		/**
		 * Test Case 2 - Convert the data file based on metadata. Two records have invalid dates. 
		 *               Should not process the records that have invalid date and should log the rows that are not processed with proper reason.
		 *               Write the proper records to the output file.
		 * 
		 * Input file: src/test/resources/converter/testcase_2/input.txt
		 * Metadata file: src/test/resources/converter/testcase_2/metadata.txt
		 * Expected output file: src/test/resources/converter/testcase_2/expectedOutput.txt
		 * Output file: src/test/resources/converter/testcase_2/output.txt
		 * 
		 */
		String testcase2Path = "src/test/resources/converter/testcase_2";
		String metadataPath = new File(testcase2Path + "/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);

		String inputFile = new File(testcase2Path + "/input.txt").getAbsolutePath();
		File outFile = new File(testcase2Path + "/output.txt");
		outFile.delete();
		DataProcessor inst = new DataFileProcessor();
		inst.convert(inputFile, listMetadata, outFile.getAbsolutePath());

		File expectedOutputFile = new File(testcase2Path + "/expectedOutput.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));

	}

	@Test
	public void convertTest3() throws Exception {
		/**
		 * Test Case 3 - Convert the data file based on metadata. Two records have comma in the data. 
		 *               Should enclose the column that have comma in a double quotes and write to output file.
		 * 
		 * Input file: src/test/resources/converter/testcase_3/input.txt
		 * Metadata file: src/test/resources/converter/testcase_3/metadata.txt
		 * Expected output file: src/test/resources/converter/testcase_3/expectedOutput.txt
		 * Output file: src/test/resources/converter/testcase_3/output.txt
		 * 
		 */	
		String testcase3Path = "src/test/resources/converter/testcase_3";
		String metadataPath = new File(testcase3Path + "/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);

		String inputFile = new File(testcase3Path + "/input.txt").getAbsolutePath();
		File outFile = new File(testcase3Path + "/output.txt");
		outFile.delete();
		DataProcessor inst = new DataFileProcessor();
		inst.convert(inputFile, listMetadata, outFile.getAbsolutePath());

		File expectedOutputFile = new File(testcase3Path + "/expectedOutput.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));

	}

	@Test
	public void convertTest4() throws Exception {
		/**
		 * Test Case 4 - Convert the data file based on metadata. one records have a negative number and other other record has invalid numeric no#. 
		 *               Should not process the records that have invalid numeric number and should log the row that are not processed with proper reason.
		 *               Write the proper records to the output file.
		 * 
		 * Input file: src/test/resources/converter/testcase_4/input.txt
		 * Metadata file: src/test/resources/converter/testcase_4/metadata.txt
		 * Expected output file: src/test/resources/converter/testcase_4/expectedOutput.txt
		 * Output file: src/test/resources/converter/testcase_4/output.txt
		 * 
		 */	
		String testcase4Path = "src/test/resources/converter/testcase_4";
		String metadataPath = new File(testcase4Path + "/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);

		String inputFile = new File(testcase4Path + "/input.txt").getAbsolutePath();
		File outFile = new File(testcase4Path + "/output.txt");
		outFile.delete();
		DataProcessor inst = new DataFileProcessor();
		inst.convert(inputFile, listMetadata, outFile.getAbsolutePath());

		File expectedOutputFile = new File(testcase4Path + "/expectedOutput.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));

	}

	@Test
	public void convertTest5() throws Exception {
		/**
		 * Test Case 5 - Convert the data file based on metadata. one records have mismatch in record length vs total metadata length. 
		 *               Should not process the records that have improper length should log the row that are not processed with proper reason.
		 *               Write the proper records to the output file.
		 * 
		 * Input file: src/test/resources/converter/testcase_5/input.txt
		 * Metadata file: src/test/resources/converter/testcase_5/metadata.txt
		 * Expected output file: src/test/resources/converter/testcase_5/expectedOutput.txt
		 * Output file: src/test/resources/converter/testcase_5/output.txt
		 * 
		 */	
		String testcase4Path = "src/test/resources/converter/testcase_5";
		String metadataPath = new File(testcase4Path + "/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);

		String inputFile = new File(testcase4Path + "/input.txt").getAbsolutePath();
		File outFile = new File(testcase4Path + "/output.txt");
		outFile.delete();
		DataProcessor inst = new DataFileProcessor();
		inst.convert(inputFile, listMetadata, outFile.getAbsolutePath());

		File expectedOutputFile = new File(testcase4Path + "/expectedOutput.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));

	}

	@Test
	public void convertTest6() throws Exception {
		/**
		 * Test Case 6 - Convert the data file that have 25K records based on metadata. 
		 * 
		 * Input file: src/test/resources/converter/testcase_6/input.txt
		 * Metadata file: src/test/resources/converter/testcase_6/metadata.txt
		 * Expected output file: src/test/resources/converter/testcase_6/expectedOutput.txt
		 * Output file: src/test/resources/converter/testcase_6/output.txt
		 * 
		 */	
		String testcase4Path = "src/test/resources/converter/testcase_6";
		String metadataPath = new File(testcase4Path + "/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);

		DataGeneratorUtil.generate(25000, listMetadata, testcase4Path + "/input.txt", testcase4Path + "/expectedOutput.txt");
		String inputFile = new File(testcase4Path + "/input.txt").getAbsolutePath();
		File outFile = new File(testcase4Path + "/output.txt");
		outFile.delete();
		DataProcessor inst = new DataFileProcessor();
		inst.convert(inputFile, listMetadata, outFile.getAbsolutePath());

		File expectedOutputFile = new File(testcase4Path + "/expectedOutput.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));

	}

}