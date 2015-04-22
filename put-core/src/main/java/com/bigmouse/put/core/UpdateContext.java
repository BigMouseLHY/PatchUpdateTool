package com.bigmouse.put.core;

import java.util.List;

/**
 * Context of update process
 * @author lihaoyuan
 *
 */
public class UpdateContext
{
	private String version;
	private List<FileDescription> files;
	private List<ProgramItem> programs;

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
}
