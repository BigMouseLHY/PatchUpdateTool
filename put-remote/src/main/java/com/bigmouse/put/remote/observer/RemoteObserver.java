package com.bigmouse.put.remote.observer;

import java.rmi.RemoteException;

import com.bigmouse.put.utils.rmi.RMIRemoteService;

public interface RemoteObserver extends RMIRemoteService
{
	public void message(String msg, String type) throws RemoteException;
	
	public void message(String msg, Exception e) throws RemoteException;
}
