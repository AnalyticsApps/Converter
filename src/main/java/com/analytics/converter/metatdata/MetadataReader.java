package com.analytics.converter.metatdata;

import java.util.List;

import com.analytics.converter.dao.Metadata;

/**
 * @author Nisanth Simon
 * @version 1.0
 */
public interface MetadataReader {

	/**
	 * Populate the Metadata to List<Metadata> Object.
	 * 
	 * @param path
	 * @return
	 * @throws MetadataException
	 */
	public List<Metadata> readMetadata(String path) throws MetadataException;
	
	public enum Headers {
		NAME, LENGTH, TYPE
	}
}
