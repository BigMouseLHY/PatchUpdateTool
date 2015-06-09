package com.bigmouse.put.remote.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.bigmouse.put.remote.observer.RemoteObserver;
import com.bigmouse.put.remote.service.RemoteService;

public interface RemoteClient
{
	public void registerObserver(RemoteObserver observer) throws RemoteException;
	
	public RemoteService connect(String ip, int port, String serviceName) throws MalformedURLException, RemoteException, NotBoundException;
}
