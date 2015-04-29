package com.bigmouse.put.core;

import java.util.LinkedHashMap;
import java.util.Map;

import com.bigmouse.put.core.observer.EmptyObserver;
import com.bigmouse.put.core.observer.ProcessObserver;

/**
 * Context of update process
 * @author lihaoyuan
 *
 */
public class UpdateContext
{
	private static UpdateContext context;
	
	/**
	 * Singleton context
	 * @return context
	 */
	public static synchronized UpdateContext getContext()
	{
		if(context == null) context = new UpdateContext();
		
		return context;
	}
	
	/**
	 * Version of the package
	 */
	private String version;
	
	/**
	 * List of files config in version.put in package
	 */
	private Map<String, FileDescription> files;
	
	/**
	 * List of programs
	 */
	private Map<String, ProgramItem> programs;
	
	/*
	 * Observer of process
	 */
	private ProcessObserver observer;

	public String getVersion()
	{
		return version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}
	public Map<String, FileDescription> getFiles()
	{
		if(files == null) files = new LinkedHashMap<String, FileDescription>();
		return files;
	}
	public Map<String, ProgramItem> getPrograms()
	{
		if(programs == null) programs = new LinkedHashMap<String, ProgramItem>();
		return programs;
	}
	public ProcessObserver getObserver()
	{
		if(observer == null) observer = new EmptyObserver();
		return observer;
	}
	public void setObserver(ProcessObserver observer)
	{
		this.observer = observer;
	}
}
