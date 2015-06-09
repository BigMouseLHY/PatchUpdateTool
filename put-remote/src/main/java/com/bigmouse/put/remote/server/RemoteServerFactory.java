package com.bigmouse.put.remote.server;

public class RemoteServerFactory
{
	public static RemoteServer getRemoteServer()
	{
		return new DefaultRemoteServer();
	}
}
