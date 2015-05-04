package com.bigmouse.put.core;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bigmouse.put.core.backup.BackupService;
import com.bigmouse.put.core.backup.BackupServiceFactory;
import com.bigmouse.put.core.config.ConfigService;
import com.bigmouse.put.core.config.ConfigServiceFactory;
import com.bigmouse.put.core.packageloader.PackageLoadService;
import com.bigmouse.put.core.packageloader.PackageLoadServiceFactory;
import com.bigmouse.put.core.update.UpdateService;
import com.bigmouse.put.core.update.UpdateServiceFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateServiceTest
{
	private static UpdateContext context;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		context = UpdateContext.getContext();
		TestObserverFacade facade = new TestObserverFacade(new TestObserver());
		context.setObserver(facade);
		
		ConfigService configService = ConfigServiceFactory.getConfigService();
		configService.loadConfig(context);
		
		PackageLoadService zipPackageLoader = PackageLoadServiceFactory.getPackageLoadService();
		zipPackageLoader.loadPackage(context, SystemConfig.BASE_PATH + "/source/update.zip");
		
		BackupService backupService = BackupServiceFactory.getBackService();
		for(ProgramItem program : context.getPrograms().values())
		{
			backupService.backup(context, program);
		}
	}

	@Test
	public void test01_update() throws PatchUpdateException
	{
		UpdateService updateService = UpdateServiceFactory.getUpdateService();
		for(ProgramItem program : context.getPrograms().values())
		{
			updateService.update(context, program);
		}
	}

	@Test
	public void test02_checkProgramUpdate_p1() throws IOException
	{
		String programUpdateFolderBase = SystemConfig.PROGRAM_MAP.get("p1");

		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder1/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder1/m1f1a.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module1/folder1/m1f1a.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder1/m1f1c.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module1/folder1/m1f1c.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder2/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder2/m1f2b.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module1/folder2/m1f2b.txt", 2, "NEW"));

		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder1/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder1/m2f1a.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module2/folder1/m2f1a.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder1/m2f1c.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module2/folder1/m2f1c.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder3/"));
		assertFalse(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder3/m2f3b.txt"));
	}

	@Test
	public void test02_checkProgramUpdate_p2() throws IOException
	{
		String programUpdateFolderBase = SystemConfig.PROGRAM_MAP.get("p2");

		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder1/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder1/m1f1a.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module1/folder1/m1f1a.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder1/m1f1c.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module1/folder1/m1f1c.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder2/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder2/m1f2b.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module1/folder2/m1f2b.txt", 2, "NEW"));

		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder1/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder1/m2f1a.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module2/folder1/m2f1a.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder1/m2f1c.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module2/folder1/m2f1c.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder3/"));
		assertFalse(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder3/m2f3b.txt"));
	}

	@Test
	public void test02_checkProgramUpdate_p3() throws IOException
	{
		String programUpdateFolderBase = SystemConfig.PROGRAM_MAP.get("p3");

		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder1/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder1/m1f1a.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module1/folder1/m1f1a.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder1/m1f1c.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module1/folder1/m1f1c.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder2/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module1/folder2/m1f2b.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module1/folder2/m1f2b.txt", 2, "NEW"));

		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder1/"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder1/m2f1a.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module2/folder1/m2f1a.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder1/m2f1c.txt"));
		assertTrue(TestUtils.checkText(programUpdateFolderBase + "/module2/folder1/m2f1c.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder3/"));
		assertFalse(TestUtils.checkPath(programUpdateFolderBase + "/module2/folder3/m2f3b.txt"));
	}
}
