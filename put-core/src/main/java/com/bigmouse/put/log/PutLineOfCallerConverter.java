package com.bigmouse.put.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class PutLineOfCallerConverter extends ClassicConverter
{

	@Override
	public String convert(ILoggingEvent le)
	{
		StackTraceElement[] cda = le.getCallerData();
	    if (cda != null && cda.length > 0)
	    {
	    	if(cda.length > 5 && cda[4].getClassName().equals("com.bigmouse.put.log.PutLogObserverProxy"))
	    	{
	    		return Integer.toString(cda[6].getLineNumber());
	    	}
	    	else
	    	{
	    		return Integer.toString(cda[0].getLineNumber());
	    	}
	    }
	    else
	    {
	    	return CallerData.NA;
	    }
	}

}
