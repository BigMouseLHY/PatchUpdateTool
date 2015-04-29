package com.bigmouse.put.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Update informatin of program
 * @author lihaoyuan
 *
 */
public class ProgramItem
{
	public ProgramItem(String id, String rootPath)
	{
		this.id = id;
		this.rootPath = rootPath;
		this.files = new ArrayList<FileItem>();
		this.status = "INIT";
	}
	
	private String id;
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
	 * INIT, BACKUP, UPDATE, FINISH
	 */
	private String status;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
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
