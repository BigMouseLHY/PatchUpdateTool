package com.bigmouse.put.core;

/**
 * Log of update information of file in one program
 * This model record the information and status of the update file in one program
 * @author lihaoyuan
 *
 */
public class FileItem
{
	/**
	 * The relative path of file
	 */
	private String filePath;
	
	/**
	 * Update operation, ADD or UPDATE or DELETE
	 */
	private String opt;
	
	/**
	 * Status of file
	 * READY: ready to update
	 * BACKUP: doing backup, copy the old file from program folder to backup folder
	 * UPDATE: do update, copy the new file from unzip folder to program folder
	 */
	private String status;
	
	/**
	 * The file path of backup
	 */
	private String backupPath;
	
	/**
	 * The file path of update
	 */
	private String updatePath;

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getOpt()
	{
		return opt;
	}

	public void setOpt(String opt)
	{
		this.opt = opt;
	}

	String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getBackupPath()
	{
		return backupPath;
	}

	public void setBackupPath(String backupPath)
	{
		this.backupPath = backupPath;
	}

	public String getUpdatePath()
	{
		return updatePath;
	}

	public void setUpdatePath(String updatePath)
	{
		this.updatePath = updatePath;
	}
}
