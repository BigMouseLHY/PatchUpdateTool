package com.bigmouse.put.remote.server;

import java.rmi.RemoteException;

import com.bigmouse.put.remote.RemoteConfig;
import com.bigmouse.put.remote.service.RemoteServiceFactory;
import com.bigmouse.put.utils.rmi.server.RMIServer;

public class DefaultRemoteServer implements RemoteServer
{
	@Override
	public void startServer() throws RemoteException
	{
		RMIServer.createServer(RemoteConfig.SERVICE_PORT);
		RMIServer.appendService(RemoteConfig.SERVICE_PORT, RemoteConfig.SERVICE_NAME, RemoteServiceFactory.getRemoteService());
	}
}
