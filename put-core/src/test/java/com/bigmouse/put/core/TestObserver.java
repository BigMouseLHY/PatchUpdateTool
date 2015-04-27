package com.bigmouse.put.core;

public class TestObserver
{
	public void log(String msg, String level)
	{
		System.out.println("OBSERVER_LOG -> [" + level + "]" + msg);
	}
	
	public void error(String msg, Exception e)
	{
		System.out.println("OBSERVER_ERR -> [ERROR]" + msg + ". " + e.getMessage());
	}
}
