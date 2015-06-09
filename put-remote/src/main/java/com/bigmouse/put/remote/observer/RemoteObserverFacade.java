package com.bigmouse.put.remote.observer;

import java.rmi.RemoteException;

import com.bigmouse.put.core.observer.ProcessObserverFacade;

public class RemoteObserverFacade implements ProcessObserverFacade<RemoteObserver>
{
	private RemoteObserver observer;
	
	@Override
	public void message(String msg, String type)
	{
		try
		{
			observer.message(msg, type);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void message(String msg, Exception e)
	{
		try
		{
			observer.message(msg, e);
		}
		catch (RemoteException e1)
		{
			e1.printStackTrace();
		}
	}

	@Override
	public void facadeFor(RemoteObserver obs)
	{
		observer = obs;
	}
	
}
