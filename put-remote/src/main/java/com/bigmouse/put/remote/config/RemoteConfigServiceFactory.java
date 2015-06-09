package com.bigmouse.put.remote.config;

public class RemoteConfigServiceFactory
{
	public static RemoteConfigService getRemoteConfigService()
	{
		return new XmlRemoteConfigReader();
	}
}
