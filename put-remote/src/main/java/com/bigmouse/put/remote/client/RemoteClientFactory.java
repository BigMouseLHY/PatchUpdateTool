package com.bigmouse.put.remote.client;

public class RemoteClientFactory
{
	public static RemoteClient getRemoteClient()
	{
		return new DefaultRemoteClient();
	}
}
