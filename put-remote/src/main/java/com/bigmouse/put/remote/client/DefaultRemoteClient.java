package com.bigmouse.put.remote.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.bigmouse.put.remote.RemoteConfig;
import com.bigmouse.put.remote.observer.RemoteObserver;
import com.bigmouse.put.remote.service.RemoteService;
import com.bigmouse.put.utils.rmi.client.RMIClient;
import com.bigmouse.put.utils.rmi.server.RMIServer;

public class DefaultRemoteClient implements RemoteClient
{
	@Override
	public void registerObserver(RemoteObserver observer) throws RemoteException
	{
		RMIServer.createServer(RemoteConfig.OBSERVER_PORT);
		RMIServer.appendService(RemoteConfig.OBSERVER_PORT, RemoteConfig.OBSERVER_NAME, observer);
	}

	@Override
	public RemoteService connect(String ip, int port, String serviceName) throws MalformedURLException, RemoteException, NotBoundException
	{
		RemoteService client = new RMIClient<RemoteService>().getRemoteService(ip, port, serviceName);
		client.bindObervser(RemoteConfig.LOCAL_IP, RemoteConfig.OBSERVER_PORT, RemoteConfig.OBSERVER_NAME);
		return client;
	}
}
