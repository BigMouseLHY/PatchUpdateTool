package com.bigmouse.put.core.backup;

/**
 * Factory to create BackupService instance
 * @author lihaoyuan
 *
 */
public class BackupServiceFactory
{
	public static BackupService getBackService()
	{
		return new DefaultBackupHandler();
	}
}
