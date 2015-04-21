package com.bigmouse.put.core;

/**
 * The update information of file
 * @author lihaoyuan
 *
 */
public class FileDescription
{
	private String filePath;
	private String opt;
	
	/**
	 * The relative path of file
	 * @return
	 */
	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	/**
	 * Update operation, ADD or UPDATE or DELETE
	 * @return
	 */
	public String getOpt()
	{
		return opt;
	}

	public void setOpt(String opt)
	{
		this.opt = opt;
	}
}
