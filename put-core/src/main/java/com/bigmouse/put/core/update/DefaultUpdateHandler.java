package com.bigmouse.put.core.update;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigmouse.put.core.FileDescription;
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
			if(fileDesc.getOpt().equals("A") || fileDesc.getOpt().equals("U"))
			{
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
			}
			else if(fileDesc.getOpt().equals("D"))
			{
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
			}
		}
		log.info("Update finish, add:" + addCount + ", update:" + updateCount + ", delete:" + deleteCount);
	}

}
