package com.bigmouse.put.remote;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.remote.client.RemoteClient;
import com.bigmouse.put.remote.client.RemoteClientFactory;
import com.bigmouse.put.remote.config.RemoteConfigServiceFactory;
import com.bigmouse.put.remote.service.RemoteService;

public class TestClient
{
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException, PatchUpdateException
	{
		RemoteConfigServiceFactory.getRemoteConfigService().loadRemoteConfig();
		
		RemoteClient client = RemoteClientFactory.getRemoteClient();
		
		client.registerObserver(new TestRemoteObserver());
		
		RemoteService service = client.connect("localhost", 54321, "testservice");
		
		service.init();
		
	}
}
