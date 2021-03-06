package com.bigmouse.put.core.backup;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigmouse.put.core.FileDescription;
import com.bigmouse.put.core.FileItem;
import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.core.ProgramItem;
import com.bigmouse.put.core.SystemConfig;
import com.bigmouse.put.core.UpdateContext;
import com.bigmouse.put.log.PutLogObserverProxy;

public class DefaultBackupHandler implements BackupService
{
	private transient final Logger log = new PutLogObserverProxy().proxy(LoggerFactory.getLogger(getClass()));
	
	@Override
	public void backup(UpdateContext context, ProgramItem program) throws PatchUpdateException
	{
		// Create backup folder
		String backupPathBase = SystemConfig.BASE_PATH + File.separator + context.getVersion() + File.separator + "backup";

		log.info("Create backup folder: " + backupPathBase);
		File backupFolder = new File(backupPathBase);
		backupFolder.mkdir();
		

		String programBackupPathBase = backupPathBase + File.separator + program.getId();
		File programBackupFolder = new File(programBackupPathBase);
		programBackupFolder.mkdir();
		
		// Loop files in version.put, and backup file with Type of U and D
		log.info("Start to backup files to: " + programBackupPathBase);
		int backupFileCount = 0;
		for(FileDescription fileDesc : context.getFiles().values())
		{
			FileItem fileItem = new FileItem();
			fileItem.setOpt(fileDesc.getOpt());
			fileItem.setStatus("INIT");
			program.getFiles().put(fileDesc.getFilePath(), fileItem);
			
			if(fileDesc.getOpt().equals("U") || fileDesc.getOpt().equals("D"))
			{
				String oldFilePath = program.getRootPath() + fileDesc.getFilePath();
				File oldFile = new File(oldFilePath);
				if(oldFile.exists())
				{
					// Backup file to backup folder
					File backupFile = new File(programBackupPathBase + fileDesc.getFilePath());
					try
					{
						log.debug("Copy file to backup folder: " + programBackupPathBase + fileDesc.getFilePath());
						FileUtils.copyFile(oldFile, backupFile);
						backupFileCount++;

						fileItem.setBackupPath(programBackupPathBase + fileDesc.getFilePath());
					}
					catch (IOException e)
					{
						PatchUpdateException ex = new PatchUpdateException(SystemConfig.IGNORETIP() + "Can not backup file: " + oldFilePath, e);
						log.error(SystemConfig.IGNORETIP() + "Can not backup file: " + oldFilePath, e);
						if(!SystemConfig.ERROR_HANDLE.equals("IGNORE")) throw ex;
					}
				}
				else
				{
					PatchUpdateException e = new PatchUpdateException(SystemConfig.IGNORETIP() + "No such file for backup: " + oldFilePath);
					log.error(SystemConfig.IGNORETIP() + "No such file for backup: " + oldFilePath, e);
					// If not ignore, throw exception
					if(!SystemConfig.ERROR_HANDLE.equals("IGNORE")) throw e;
				}
			}
			fileItem.setStatus("BACKUP");
		}
		log.info("All files has backup to program backup folder: " + programBackupFolder + ", " + backupFileCount + " files.");
	}
}
