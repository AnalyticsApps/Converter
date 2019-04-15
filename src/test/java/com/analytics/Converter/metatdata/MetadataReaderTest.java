package com.analytics.Converter.metatdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.analytics.Converter.metatdata.MetadataException;

import java.io.File;
import java.util.List;

class MetadataReaderTest {

	@Test
	public void readMetadataTest1() throws MetadataException {
		/**
		 * Test Case 1 - Read the metadata file and load to Metadata Object
		 * 
		 * Input data
		 * ===========
		 * Birth date,10,date
		 * First name,15,string
		 * Last name,15,string
		 * Weight,5,numeric
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_1.txt").getAbsolutePath();
		MetadataReader mr = new MetadataReader();
		List<Metadata> listmr = mr.readMetadata(metadataFile);

		assertEquals(4, listmr.size());
		assertEquals("[ colName = Birth date , colLength = 10 , colType = date ]", listmr.get(0).toString());
		assertEquals("[ colName = First name , colLength = 15 , colType = string ]", listmr.get(1).toString());
		assertEquals("[ colName = Last name , colLength = 15 , colType = string ]", listmr.get(2).toString());
		assertEquals("[ colName = Weight , colLength = 5 , colType = numeric ]", listmr.get(3).toString());
	}

	@Test
	public void readMetadataTest2() {
		/**
		 * Test Case 2 - Read the metadata file that have 2 invalid metadata length
		 * 
		 * Input data
		 * ===========
		 * Birth date,10,date
		 * First name,15,string
		 * Last name,15,string
		 * Weight,invalidnum,numeric
		 * height,234f,numeric
		 * address,15,string
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_2.txt").getAbsolutePath();
		MetadataReader mr = new MetadataReader();
		List<Metadata> listmr = null;

		try {
			listmr = mr.readMetadata(metadataFile);
		} catch (MetadataException e) {
			assertEquals(
					" Error in processing metadata file. Found 2 errors in validating Metadata. Refer /opt/FileConverter/log/application.log for more details.",
					e.getMessage());
		}
	}

	@Test
	public void readMetadataTest3() {
		/**
		 * Test Case 3 - Read the metadata file that have 3 invalid metadata type
		 * 
		 * Input data
		 * ===========
		 * Birth date,10,date
		 * First name,15,map
		 * Last name,15,array
		 * Weight,5,list
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_3.txt").getAbsolutePath();
		MetadataReader mr = new MetadataReader();
		List<Metadata> listmr = null;

		try {
			listmr = mr.readMetadata(metadataFile);
		} catch (MetadataException e) {
			assertEquals(
					" Error in processing metadata file. Found 3 errors in validating Metadata. Refer /opt/FileConverter/log/application.log for more details.",
					e.getMessage());
		}
	}

	@Test
	public void readMetadataTest4() {
		/**
		 * Test Case 4 - Read the metadata file that less no# of columns
		 * 
		 * Input data
		 * ===========
		 * Birth date,10,date
		 * First name,15,string
		 * Last name,15,string
		 * Weight,numeric
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_4.txt").getAbsolutePath();
		MetadataReader mr = new MetadataReader();
		List<Metadata> listmr = null;

		try {
			listmr = mr.readMetadata(metadataFile);
		} catch (MetadataException e) {
			assertEquals(
					" Error in processing metadata file. Found 1 errors in validating Metadata. Refer /opt/FileConverter/log/application.log for more details.",
					e.getMessage());
		}
	}

	@Test
	public void readMetadataTest5() {
		/**
		 * Test Case 5 - Read the metadata file that column empty
		 * 
		 * Input data
		 * ===========
		 * Birth date,10,date
		 * First name,15,string
		 * Last name,,string
		 * Weight,,numeric
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_5.txt").getAbsolutePath();
		MetadataReader mr = new MetadataReader();
		List<Metadata> listmr = null;

		try {
			listmr = mr.readMetadata(metadataFile);
		} catch (MetadataException e) {
			assertEquals(
					" Error in processing metadata file. Found 2 errors in validating Metadata. Refer /opt/FileConverter/log/application.log for more details.",
					e.getMessage());
		}
	}

	@Test
	public void readMetadataTest6() {
		/**
		 * Test Case 6 - Read the metadata file that length less than or equal to 0
		 * 
		 * Input data
		 * ===========
		 * Birth date,0,date
		 * First name,-15,string
		 * Last name,-1,string
		 * Weight,-9,numeric
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_6.txt").getAbsolutePath();
		MetadataReader mr = new MetadataReader();
		List<Metadata> listmr = null;

		try {
			listmr = mr.readMetadata(metadataFile);
		} catch (MetadataException e) {
			assertEquals(
					" Error in processing metadata file. Found 4 errors in validating Metadata. Refer /opt/FileConverter/log/application.log for more details.",
					e.getMessage());
		}

	}

	@Test
	public void readMetadataTest7() {
		/**
		 * Test Case 7 - Invalid matadata path
		 */

		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput.txt").getAbsolutePath();
		MetadataReader mr = new MetadataReader();
		List<Metadata> listmr = null;

		try {
			listmr = mr.readMetadata(metadataFile);
		} catch (MetadataException e) {
			assertEquals(" Metadata File Not Found Error. ", e.getMessage());
		}

	}
}