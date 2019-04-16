package com.analytics.converter.metatdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.analytics.converter.dao.Metadata;
import com.analytics.converter.metatdata.FileMetadataReader;
import com.analytics.converter.metatdata.MetadataException;
import com.analytics.converter.metatdata.MetadataReader;

/**
 * Test Cases for testing Metadata Reader
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
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
		 * 
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_1.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = metadataReader.readMetadata(metadataFile);

		assertEquals(4, listMetadata.size());
		assertEquals("[ colName = Birth date , colLength = 10 , colType = date ]", listMetadata.get(0).toString());
		assertEquals("[ colName = First name , colLength = 15 , colType = string ]", listMetadata.get(1).toString());
		assertEquals("[ colName = Last name , colLength = 15 , colType = string ]", listMetadata.get(2).toString());
		assertEquals("[ colName = Weight , colLength = 5 , colType = numeric ]", listMetadata.get(3).toString());
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
		 * 
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_2.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = null;

		try {
			listMetadata = metadataReader.readMetadata(metadataFile);
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
		 * 
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_3.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = null;

		try {
			listMetadata = metadataReader.readMetadata(metadataFile);
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
		 * 
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_4.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = null;

		try {
			listMetadata = metadataReader.readMetadata(metadataFile);
		} catch (MetadataException e) {
			assertEquals(
					" Error in processing metadata file. Found 1 errors in validating Metadata. Refer /opt/FileConverter/log/application.log for more details.",
					e.getMessage());
		}
	}

	@Test
	public void readMetadataTest5() {
		/**
		 * Test Case 5 - Read the metadata file that have column empty
		 * 
		 * Input data
		 * ===========
		 * Birth date,10,date
		 * First name,15,string
		 * Last name,,string
		 * Weight,,numeric
		 * 
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_5.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = null;

		try {
			listMetadata = metadataReader.readMetadata(metadataFile);
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
		 * 
		 */
		String metadataFile = new File("src/test/resources/metadata/TestMetadataReaderInput_6.txt").getAbsolutePath();
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = null;

		try {
			listMetadata = metadataReader.readMetadata(metadataFile);
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
		MetadataReader metadataReader = new FileMetadataReader();
		List<Metadata> listMetadata = null;

		try {
			listMetadata = metadataReader.readMetadata(metadataFile);
		} catch (MetadataException e) {
			assertEquals(" Metadata File Not Found Error. ", e.getMessage());
		}

	}
}