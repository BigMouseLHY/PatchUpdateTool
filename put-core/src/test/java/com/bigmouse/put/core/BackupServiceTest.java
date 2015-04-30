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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BackupServiceTest
{
	private static UpdateContext context;
	
	private static final String version = "PutTest_1.0.0_001_20150501_Release";

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
	}

	@Test
	public void test01_backup() throws PatchUpdateException
	{
		BackupService backupService = BackupServiceFactory.getBackService();
		for(ProgramItem program : context.getPrograms().values())
		{
			backupService.backup(context, program);
		}
	}

	@Test
	public void test02_checkBackupFolder() throws IOException
	{
		String backupFolderBase = SystemConfig.BASE_PATH + "/" + version + "/backup/";
		// Check backup folder
		assertTrue(TestUtils.checkPath(backupFolderBase));
	}

	@Test
	public void test03_checkProgramBackup_p1() throws IOException
	{
		String programBackupFolderBase = SystemConfig.BASE_PATH + "/" + version + "/backup/p1/";
		// Check backup folder
		assertTrue(TestUtils.checkPath(programBackupFolderBase));

		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder1/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder1/m1f1a.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module1/folder1/m1f1a.txt", 2, "OLD"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder2/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder2/m1f2b.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module1/folder2/m1f2b.txt", 2, "OLD"));

		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder1/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder1/m2f1a.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module2/folder1/m2f1a.txt", 2, "OLD"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder3/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder3/m2f3b.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module2/folder3/m2f3b.txt", 2, "OLD"));
	}

	@Test
	public void test03_checkProgramBackup_p2() throws IOException
	{
		String programBackupFolderBase = SystemConfig.BASE_PATH + "/" + version + "/backup/p2/";
		// Check backup folder
		assertTrue(TestUtils.checkPath(programBackupFolderBase));

		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder1/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder1/m1f1a.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module1/folder1/m1f1a.txt", 2, "OLD"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder2/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder2/m1f2b.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module1/folder2/m1f2b.txt", 2, "OLD"));

		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder1/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder1/m2f1a.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module2/folder1/m2f1a.txt", 2, "OLD"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder3/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder3/m2f3b.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module2/folder3/m2f3b.txt", 2, "OLD"));
	}

	@Test
	public void test03_checkProgramBackup_p3() throws IOException
	{
		String programBackupFolderBase = SystemConfig.BASE_PATH + "/" + version + "/backup/p3/";
		// Check backup folder
		assertTrue(TestUtils.checkPath(programBackupFolderBase));

		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder1/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder1/m1f1a.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module1/folder1/m1f1a.txt", 2, "OLD"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder2/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module1/folder2/m1f2b.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module1/folder2/m1f2b.txt", 2, "OLD"));

		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder1/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder1/m2f1a.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module2/folder1/m2f1a.txt", 2, "OLD"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder3/"));
		assertTrue(TestUtils.checkPath(programBackupFolderBase + "module2/folder3/m2f3b.txt"));
		assertTrue(TestUtils.checkText(programBackupFolderBase + "module2/folder3/m2f3b.txt", 2, "OLD"));
	}

}
