package com.bigmouse.put.log;

import ch.qos.logback.classic.PatternLayout;

public class PutPatternLayout extends PatternLayout
{
	static
	{
		defaultConverterMap.put("L", PutLineOfCallerConverter.class.getName());
		defaultConverterMap.put("line", PutLineOfCallerConverter.class.getName());
	}
}
