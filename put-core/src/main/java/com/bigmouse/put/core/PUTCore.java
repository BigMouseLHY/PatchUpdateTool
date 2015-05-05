package com.bigmouse.put.core;

import com.bigmouse.put.core.backup.BackupService;
import com.bigmouse.put.core.backup.BackupServiceFactory;
import com.bigmouse.put.core.config.ConfigService;
import com.bigmouse.put.core.config.ConfigServiceFactory;
import com.bigmouse.put.core.observer.ProcessObserverFacade;
import com.bigmouse.put.core.packageloader.PackageLoadService;
import com.bigmouse.put.core.packageloader.PackageLoadServiceFactory;
import com.bigmouse.put.core.update.UpdateService;
import com.bigmouse.put.core.update.UpdateServiceFactory;

public class PUTCore
{
	public static void patchAutomatic(ProcessObserverFacade<?> observer, String filePath) throws PatchUpdateException
	{
		init(observer);
		loadPackage(filePath);
		backup();
		update();
	}
	
	public static void init(ProcessObserverFacade<?> observer) throws PatchUpdateException
	{
		UpdateContext.getContext().setObserver(observer);
		
		ConfigService configService = ConfigServiceFactory.getConfigService();
		configService.loadConfig(UpdateContext.getContext());
	}
	
	public static void loadPackage(String filePath) throws PatchUpdateException
	{
		PackageLoadService zipPackageLoader = PackageLoadServiceFactory.getPackageLoadService();
		zipPackageLoader.loadPackage(UpdateContext.getContext(), filePath);
	}
	
	public static void backup() throws PatchUpdateException
	{
		BackupService backupService = BackupServiceFactory.getBackService();
		for(ProgramItem program : UpdateContext.getContext().getPrograms().values())
		{
			backupService.backup(UpdateContext.getContext(), program);
		}
	}
	
	public static void update() throws PatchUpdateException
	{
		UpdateService updateService = UpdateServiceFactory.getUpdateService();
		for(ProgramItem program : UpdateContext.getContext().getPrograms().values())
		{
			try
			{
				updateService.update(UpdateContext.getContext(), program);
			}
			catch (PatchUpdateException e)
			{
				updateService.rollback(UpdateContext.getContext(), program);
			}
		}
	}
}
