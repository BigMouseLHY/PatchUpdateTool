package com.bigmouse.put.remote.config;

import java.io.File;

import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.log.PutLogObserverProxy;
import com.bigmouse.put.remote.RemoteConfig;
import com.bigmouse.put.utils.XmlReader;
import com.bigmouse.put.utils.XmlReader.XmlReaderException;

public class XmlRemoteConfigReader implements RemoteConfigService
{
	private transient final Logger log = new PutLogObserverProxy().proxy(LoggerFactory.getLogger(getClass()));

	@Override
	public void loadRemoteConfig() throws PatchUpdateException
	{
		// get config file path
		String configPath = this.getClass().getResource("/").getPath();
		if(configPath.endsWith(File.separator)) configPath += "conf.xml";
		else configPath += File.separator + "conf.xml";
		
		log.info("Start to load remote config in " + configPath);
		XmlReader reader = new XmlReader(configPath);
		try
		{
			// Get local ip
			Node remoteNode = reader.getElementNode("/patch-update-tool/remote");
			RemoteConfig.LOCAL_IP = remoteNode.valueOf("@localip");
			
			// Get service config
			Node serviceNode = reader.getElementNode("/patch-update-tool/remote/service");
			RemoteConfig.SERVICE_PORT = Integer.valueOf(serviceNode.valueOf("@port"));
			RemoteConfig.SERVICE_NAME = serviceNode.getText();
			
			// Get observer config
			Node observerNode = reader.getElementNode("/patch-update-tool/remote/observer");
			RemoteConfig.OBSERVER_PORT = Integer.valueOf(observerNode.valueOf("@port"));
			RemoteConfig.OBSERVER_NAME = observerNode.getText();
			
			
			log.info("Load remote config finish!");
		}
		catch (XmlReaderException e)
		{
			PatchUpdateException ex = new PatchUpdateException("Read remote config file ERROR!", e);
			log.error("Read remote config file ERROR!", e);
			throw ex;
		}
	}
}
