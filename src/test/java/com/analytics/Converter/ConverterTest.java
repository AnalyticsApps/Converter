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
		
		String metadataPath = new File("src/test/resources/converter/testcase_1/metadata.txt").getAbsolutePath();
		MetadataReader metadataReader = new MetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataPath);
		
		String inputFile = new File("src/test/resources/converter/testcase_1/input.txt").getAbsolutePath();
		File outFile = new File("src/test/resources/converter/testcase_1/output.txt");
		outFile.delete();
		Converter inst = new Converter();
		inst.convert(inputFile, listMetadata, outFile.getAbsolutePath());
		
		File expectedOutputFile = new File("src/test/resources/converter/testcase_1/expectedOutput.txt");
		assertEquals(true, FileUtils.contentEquals(expectedOutputFile, outFile));

		
	}
	


}