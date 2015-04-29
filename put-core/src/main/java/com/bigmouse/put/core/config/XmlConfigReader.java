package com.bigmouse.put.core.config;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.core.ProgramItem;
import com.bigmouse.put.core.SystemConfig;
import com.bigmouse.put.core.UpdateContext;
import com.bigmouse.put.log.PutLogObserverProxy;
import com.bigmouse.put.utils.XmlReader;
import com.bigmouse.put.utils.XmlReader.XmlReaderException;

/**
 * Load config file with xml type, read the properties and fill into SystemConfig.
 * @author lihaoyuan
 *
 */
public class XmlConfigReader implements ConfigService
{
	private transient final Logger log = new PutLogObserverProxy().proxy(LoggerFactory.getLogger(getClass()));

	@Override
	public void loadConfig(UpdateContext context) throws PatchUpdateException
	{
		// get config file path
		String configPath = this.getClass().getResource("/").getPath();
		if(configPath.endsWith(File.separator)) configPath += "conf.xml";
		else configPath += File.separator + "conf.xml";
		
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
			List<Node> programs = reader.getElementNodes("/patch-update-tool/program/path");
			SystemConfig.PROGRAM_MAP = new HashMap<String, String>();
			for(Node node : programs)
			{
				String pid = node.valueOf("@id");
				String val = node.getText();
				SystemConfig.PROGRAM_MAP.put(pid, val);
				
				ProgramItem program = new ProgramItem(pid, val);
				context.getPrograms().put(pid, program);
				
				log.info("Got program path: " + pid + " -> " + val);
			}
			log.info("Load config finish!");
		}
		catch (XmlReaderException e)
		{
			PatchUpdateException ex = new PatchUpdateException("Read config file ERROR! " + e.getMessage(), e);
			throw ex;
		}
	}
	

}
