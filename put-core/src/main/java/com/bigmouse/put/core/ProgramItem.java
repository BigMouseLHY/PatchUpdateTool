package com.bigmouse.put.core;

import java.util.List;

/**
 * Update informatin of program
 * @author lihaoyuan
 *
 */
public class ProgramItem
{
	/**
	 * The root path of program
	 */
	private String rootPath;
	
	/**
	 * Update logs of file
	 */
	private List<FileItem> files;
	
	/**
	 * The update status of program
	 */
	private String status;
	
	public String getRootPath()
	{
		return rootPath;
	}
	public void setRootPath(String rootPath)
	{
		this.rootPath = rootPath;
	}
	public List<FileItem> getFiles()
	{
		return files;
	}
	public void setFiles(List<FileItem> files)
	{
		this.files = files;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
}
