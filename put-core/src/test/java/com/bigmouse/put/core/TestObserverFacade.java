package com.bigmouse.put.core;

import com.bigmouse.put.core.observer.ProcessObserverFacade;

public class TestObserverFacade implements ProcessObserverFacade<TestObserver>
{
	
	private TestObserver observer;
	
	public TestObserverFacade(TestObserver obs)
	{
		facadeFor(obs);
	}

	@Override
	public void message(String msg, String type)
	{
		observer.log(msg, type);
	}

	@Override
	public void message(String msg, Exception e)
	{
		observer.error(msg, e);
	}

	@Override
	public void facadeFor(TestObserver observser)
	{
		this.observer = observser;
	}
	
}
