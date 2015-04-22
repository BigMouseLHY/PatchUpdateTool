package com.bigmouse.put.core.config;

/**
 * Factory to create ConfigService instance
 * @author lihaoyuan
 *
 */
public class ConfigServiceFactory
{
	public static ConfigService getConfigService()
	{
		return new XmlConfigReader();
	}
}
