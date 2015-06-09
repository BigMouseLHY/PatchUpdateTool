package com.bigmouse.put.remote.service;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.bigmouse.put.core.PUTCore;
import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.core.observer.ProcessObserverFacade;
import com.bigmouse.put.remote.observer.RemoteObserver;
import com.bigmouse.put.remote.observer.RemoteObserverFacade;
import com.bigmouse.put.utils.rmi.client.RMIClient;

public class DefaultRemoteService extends UnicastRemoteObject implements RemoteService
{
	private static final long serialVersionUID = 6669126417770722199L;
	
	private ProcessObserverFacade<RemoteObserver> facade;
	
	public DefaultRemoteService() throws RemoteException
	{
		super();
	}

	@Override
	public void test() throws RemoteException
	{
		
	}

	@Override
	public void bindObervser(String ip, int port, String serviceName) throws RemoteException
	{
		try
		{
			RemoteObserver observer = new RMIClient<RemoteObserver>().getRemoteService(ip, port, serviceName);
			
			facade = new RemoteObserverFacade();
			facade.facadeFor(observer);
			
			PUTCore.setObserver(facade);
		}
		catch (MalformedURLException | NotBoundException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void patchAutomatic(String filePath) throws RemoteException, PatchUpdateException
	{
		PUTCore.patchAutomatic(facade, filePath);
	}

	@Override
	public void init() throws RemoteException, PatchUpdateException
	{
		PUTCore.init();
	}

	@Override
	public void loadPackage(String filePath) throws RemoteException, PatchUpdateException
	{
		PUTCore.loadPackage(filePath);
	}

	@Override
	public void backup() throws RemoteException, PatchUpdateException
	{
		PUTCore.backup();
	}

	@Override
	public void update() throws RemoteException, PatchUpdateException
	{
		PUTCore.update();
	}

}
