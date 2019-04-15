package com.analytics.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.analytics.Converter.Converter;
import com.analytics.Converter.metatdata.Metadata;
import com.analytics.Converter.metatdata.MetadataReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import org.apache.commons.io.FileUtils;

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
		 */		
		String testcase1Path = "src/test/resources/converter/testcase_1";
		String metadataPath = new File(testcase1Path + "/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new MetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);
		
		String inputFile = new File(testcase1Path + "/input.txt").getAbsolutePath();
		File outFile = new File(testcase1Path + "/output.txt");
		outFile.delete();
		Converter inst = new Converter();
		inst.convert(inputFile, listMetadata, outFile.getAbsolutePath());
		
		File expectedOutputFile = new File(testcase1Path + "/expectedOutput.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));
		
	}
	
	@Test
	public void convertTest2() throws Exception {
		/**
		 * Test Case 2 - Convert the data file based on metadata. Two records have invalid dates. 
		 *               Should not process the records that have invalid date and should log the rows that are not processed with proper reason.
		 *               Should display the records that are rejected with proper reason in console.
		 *               Write the proper records to the output file.
		 * 
		 * Input file: src/test/resources/converter/testcase_2/input.txt
		 * Metadata file: src/test/resources/converter/testcase_2/metadata.txt
		 * Expected output file: src/test/resources/converter/testcase_2/expectedOutput.txt
		 * Output file: src/test/resources/converter/testcase_2/output.txt
		 */
		String testcase2Path = "src/test/resources/converter/testcase_2";
		String metadataPath = new File(testcase2Path + "/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new MetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);
		
		String inputFile = new File(testcase2Path + "/input.txt").getAbsolutePath();
		File outFile = new File(testcase2Path + "/output.txt");
		outFile.delete();
		Converter inst = new Converter();
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
		 */		
		String testcase3Path = "src/test/resources/converter/testcase_3";
		String metadataPath = new File(testcase3Path + "/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new MetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);
		
		String inputFile = new File(testcase3Path + "/input.txt").getAbsolutePath();
		File outFile = new File(testcase3Path + "/output.txt");
		outFile.delete();
		Converter inst = new Converter();
		inst.convert(inputFile, listMetadata, outFile.getAbsolutePath());
		
		File expectedOutputFile = new File(testcase3Path + "/expectedOutput.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));
		
	}
	
	@Test
	public void convertTest4() throws Exception {
		/**
		 * Test Case 4 - Convert the data file based on metadata. one records have a negative number and other other record has invalid numeric no#. 
		 *               Should not process the records that have invalid numeric number and should log the row that are not processed with proper reason.
		 *               Should display the records that are rejected with proper reason in console.
		 *               Write the proper records to the output file.
		 * 
		 * Input file: src/test/resources/converter/testcase_3/input.txt
		 * Metadata file: src/test/resources/converter/testcase_3/metadata.txt
		 * Expected output file: src/test/resources/converter/testcase_3/expectedOutput.txt
		 * Output file: src/test/resources/converter/testcase_3/output.txt
		 */		
		String testcase4Path = "src/test/resources/converter/testcase_4";
		String metadataPath = new File(testcase4Path + "/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new MetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);
		
		String inputFile = new File(testcase4Path + "/input.txt").getAbsolutePath();
		File outFile = new File(testcase4Path + "/output.txt");
		outFile.delete();
		Converter inst = new Converter();
		inst.convert(inputFile, listMetadata, outFile.getAbsolutePath());
		
		File expectedOutputFile = new File(testcase4Path + "/expectedOutput.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));
		
	}


}