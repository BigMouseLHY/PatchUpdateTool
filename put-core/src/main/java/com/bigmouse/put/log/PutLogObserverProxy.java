package com.bigmouse.put.log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;

import com.bigmouse.put.core.UpdateContext;

public class PutLogObserverProxy implements InvocationHandler
{
	
	private Logger obj;
	
	public Logger proxy(Logger o)
	{
		obj = o;
		
		return (Logger)Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		Object result = null;
		result = method.invoke(obj, args);
		
		// Method name is the log level
		String logLevel = method.getName().toUpperCase();
		
		if(args.length == 1)
		{
			// Info debug warn level
			UpdateContext.getContext().getObserver().message(args[0].toString(), logLevel);
		}
		else if(args.length > 1 && args[args.length - 1] instanceof Exception)
		{
			// Error log with exception
			UpdateContext.getContext().getObserver().message(args[0].toString(), (Exception)args[args.length - 1]);
		}
		
		return result;
	}

}
