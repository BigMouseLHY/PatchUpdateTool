package com.bigmouse.put.remote.service;

import java.rmi.RemoteException;

public class RemoteServiceFactory
{
	public static RemoteService getRemoteService() throws RemoteException
	{
		return new DefaultRemoteService();
	}
}
