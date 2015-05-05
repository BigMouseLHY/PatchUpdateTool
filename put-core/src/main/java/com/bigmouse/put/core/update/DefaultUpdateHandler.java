package com.bigmouse.put.core.update;

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

public class DefaultUpdateHandler implements UpdateService
{
	private transient final Logger log = new PutLogObserverProxy().proxy(LoggerFactory.getLogger(getClass()));

	@Override
	public void update(UpdateContext context, ProgramItem program) throws PatchUpdateException
	{
		String srcPathBase = SystemConfig.BASE_PATH + File.separator + context.getVersion() + File.separator + "update" + File.separator + "files";
		String targetpathBase = program.getRootPath();
		
		log.info("Start to update program: " + program.getId());
		
		int addCount = 0, updateCount = 0, deleteCount = 0;
		
		for(FileDescription fileDesc : context.getFiles().values())
		{
			// Get file item in program object
			FileItem fileItem = program.getFiles().get(fileDesc.getFilePath());
			if(fileItem == null)
			{
				fileItem = new FileItem();
				fileItem.setOpt(fileDesc.getOpt());
				fileItem.setStatus("UN-BACKUP");
				program.getFiles().put(fileDesc.getFilePath(), fileItem);
			}
			
			if(fileDesc.getOpt().equals("A") || fileDesc.getOpt().equals("U"))
			{
				// Do update from update folder to program folder
				String srcFilePath = srcPathBase + File.separator + fileDesc.getFilePath();
				String targetFilePath = targetpathBase + File.separator + fileDesc.getFilePath();
				try
				{
					FileUtils.copyFile(new File(srcFilePath), new File(targetFilePath));
					log.debug("Update file type " + fileDesc.getOpt() + ": " + srcFilePath + " -> " + targetFilePath);
				}
				catch (IOException e)
				{
					log.error("Can not update file: " + fileDesc.getFilePath());
					PatchUpdateException ex = new PatchUpdateException("Can not update file: " + fileDesc.getFilePath(), e);
					throw ex;
				}
				
				if(fileDesc.getOpt().equals("A")) addCount++;
				else updateCount++;
				
				fileItem.setFilePath(srcFilePath);
				fileItem.setUpdatePath(targetFilePath);
			}
			else if(fileDesc.getOpt().equals("D"))
			{
				// Delete file which need to delete
				String targetFilePath = targetpathBase + File.separator + fileDesc.getFilePath();
				try
				{
					FileUtils.forceDelete(new File(targetFilePath));
					log.debug("Delete file: " + targetFilePath);
				}
				catch (IOException e)
				{
					log.error("Can not delete file: " + fileDesc.getFilePath());
					PatchUpdateException ex = new PatchUpdateException("Can not delete file: " + fileDesc.getFilePath(), e);
					throw ex;
				}
				deleteCount++;
				
				fileItem.setUpdatePath(targetFilePath);
			}
			fileItem.setStatus("UPDATE");
		}
		log.info("Update finish, add:" + addCount + ", update:" + updateCount + ", delete:" + deleteCount);
	}
	
	@Override
	public void rollback(UpdateContext context, ProgramItem program) throws PatchUpdateException
	{
		log.info("Start to rollback program: " + program.getId());
		
		int rollbackCount = 0;
		
		for(FileItem fileItem : program.getFiles().values())
		{
			if(fileItem.getOpt().equals("U") || fileItem.getOpt().equals("D"))
			{
				try
				{
					FileUtils.copyFile(new File(fileItem.getBackupPath()), new File(fileItem.getUpdatePath()));
					log.debug("Rollback file type: " + fileItem.getBackupPath() + " -> " + fileItem.getUpdatePath());
				}
				catch (IOException e)
				{
					log.error("Can not rollback file: " + fileItem.getUpdatePath());
					PatchUpdateException ex = new PatchUpdateException("Can not rollback file: " + fileItem.getUpdatePath(), e);
					throw ex;
				}
				
				rollbackCount++;
			}
			else if(fileItem.getOpt().equals("A"))
			{
				try
				{
					FileUtils.forceDelete(new File(fileItem.getUpdatePath()));
					log.debug("Rollback delete file: " + fileItem.getUpdatePath());
				}
				catch (IOException e)
				{
					log.error("Can not rollback delete file: " + fileItem.getUpdatePath());
					PatchUpdateException ex = new PatchUpdateException("Can not rollback delete file: " + fileItem.getUpdatePath(), e);
					throw ex;
				}
				rollbackCount++;
			}
		}
		log.info("Rollback finish, rollback:" + rollbackCount + " files");
	}

}
