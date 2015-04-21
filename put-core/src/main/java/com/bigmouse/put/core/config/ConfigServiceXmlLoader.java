package com.bigmouse.put.core.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.core.SystemConfig;
import com.bigmouse.put.utils.XmlReader;
import com.bigmouse.put.utils.XmlReader.XmlReaderException;

/**
 * Load config file with xml type, read the properties and fill into SystemConfig.
 * @author lihaoyuan
 *
 */
public class ConfigServiceXmlLoader implements ConfigService
{
	private transient final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void loadConfig() throws PatchUpdateException
	{
		// get config file path
		String configPath = this.getClass().getResource("/").getPath() + File.separator + "conf.xml";
		
		log.info("Start to load config in " + configPath);
		XmlReader reader = new XmlReader(configPath);
		try
		{
			// get base path
			String basePath = reader.getElementText("/patch-update-tool/base-path");
			if(basePath == null) throw new PatchUpdateException("conf.xml MUST has <base-path> node!");
			SystemConfig.BASE_PATH = basePath;
			log.info("Got base path: " + SystemConfig.BASE_PATH);
			
			// get error handle
			String errorHandle = reader.getElementText("/patch-update-tool/error-handle");
			if(errorHandle == null) errorHandle = "IGNORE";
			SystemConfig.ERROR_HANDLE = errorHandle;
			log.info("Got error handle type: " + SystemConfig.ERROR_HANDLE);
			
			// get program path list
			List<String> programs = reader.getElementTextList("/patch-update-tool/program/path");
			SystemConfig.PROGRAM_LIST = new ArrayList<String>();
			for(String path : programs)
			{
				SystemConfig.PROGRAM_LIST.add(path);
				log.info("Got program path: " + path);
			}
			log.info("Load config finish!");
		}
		catch (XmlReaderException e)
		{
			throw new PatchUpdateException("Read config file ERROR! " + e.getMessage(), e);
		}
	}
	

}
