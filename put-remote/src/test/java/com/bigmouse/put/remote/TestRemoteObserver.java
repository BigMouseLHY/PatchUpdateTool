package com.bigmouse.put.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.bigmouse.put.remote.observer.RemoteObserver;

public class TestRemoteObserver extends UnicastRemoteObject implements RemoteObserver
{
	
	private static final long serialVersionUID = -5983546356160223670L;
	
	protected TestRemoteObserver() throws RemoteException
	{
		super();
	}

	@Override
	public void test() throws RemoteException
	{
			
	}

	@Override
	public void message(String msg, String type) throws RemoteException
	{
		System.out.println("RMI:" + msg);
	}

	@Override
	public void message(String msg, Exception e) throws RemoteException
	{
		System.out.println("RMI:" + msg + ". " + e.getMessage());
	}

}
