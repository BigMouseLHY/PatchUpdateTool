package com.bigmouse.put.core;

import java.util.List;

import com.bigmouse.put.core.observer.ProcessObserver;

/**
 * Context of update process
 * @author lihaoyuan
 *
 */
public class UpdateContext
{
	/**
	 * Version of the package
	 */
	private String version;
	
	/**
	 * List of files config in version.put in package
	 */
	private List<FileDescription> files;
	
	/**
	 * List of programs
	 */
	private List<ProgramItem> programs;
	
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
	
	public List<FileDescription> getFiles()
	{
		return files;
	}
	public void setFiles(List<FileDescription> files)
	{
		this.files = files;
	}
	
	public List<ProgramItem> getPrograms()
	{
		return programs;
	}
	public void setPrograms(List<ProgramItem> programs)
	{
		this.programs = programs;
	}
	public ProcessObserver getObserver()
	{
		return observer;
	}
	public void setObserver(ProcessObserver observer)
	{
		this.observer = observer;
	}
}
