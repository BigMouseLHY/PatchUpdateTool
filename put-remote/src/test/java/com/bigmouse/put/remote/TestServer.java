package com.bigmouse.put.remote;

import java.rmi.RemoteException;

import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.remote.config.RemoteConfigServiceFactory;
import com.bigmouse.put.remote.server.RemoteServerFactory;

public class TestServer
{
	public static void main(String[] args) throws RemoteException, PatchUpdateException
	{
		RemoteConfigServiceFactory.getRemoteConfigService().loadRemoteConfig();
		
		RemoteServerFactory.getRemoteServer().startServer();
	}
}
