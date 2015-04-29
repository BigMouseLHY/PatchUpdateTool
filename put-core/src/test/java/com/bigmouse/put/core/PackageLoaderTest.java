package com.bigmouse.put.core;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bigmouse.put.core.config.ConfigService;
import com.bigmouse.put.core.config.ConfigServiceFactory;
import com.bigmouse.put.core.packageloader.PackageLoadService;
import com.bigmouse.put.core.packageloader.PackageLoadServiceFactory;

import static org.junit.Assert.*;

public class PackageLoaderTest
{
	private static UpdateContext context;
	
	private static final String version = "PutTest_1.0.0_001_20150501_Release";

	@BeforeClass
	public static void initConfig() throws PatchUpdateException
	{
		context = UpdateContext.getContext();
		TestObserverFacade facade = new TestObserverFacade(new TestObserver());
		context.setObserver(facade);
		
		ConfigService configService = ConfigServiceFactory.getConfigService();
		configService.loadConfig(context);
	}
	
	@Test
	public void loadPackage() throws PatchUpdateException
	{
		PackageLoadService zipPackageLoader = PackageLoadServiceFactory.getPackageLoadService();
		zipPackageLoader.loadPackage(context, SystemConfig.BASE_PATH + "/source/update.zip");
	}
	
	@Test
	public void checkBaseFolder()
	{
		assertFalse(TestUtils.checkPath(SystemConfig.BASE_PATH + "/temp/"));
		assertTrue(TestUtils.checkPath(SystemConfig.BASE_PATH + "/" + version + "/"));
	}

	@Test
	public void checkUpdateFolder() throws IOException
	{
		String updateFolderBase = SystemConfig.BASE_PATH + "/" + version + "/update/";
		// Check update folder
		assertTrue(TestUtils.checkPath(updateFolderBase));
		
		String[] versionText = new String[]
		{
			"version: " + version
			, ""
			, "#qwdfv"
			, "U /module1/folder1/m1f1a.txt"
			, "U /module1/folder2/m1f2b.txt"
			, "A /module1/folder1/m1f1c.txt"
			, ""
			, "#kjmnbgf"
			, "U /module2/folder1/m2f1a.txt"
			, "D /module2/folder3/m2f3b.txt"
			, "A /module2/folder1/m2f1c.txt"
		};
		assertTrue(TestUtils.checkPath(updateFolderBase + "version.put"));
		assertTrue(TestUtils.checkText(updateFolderBase + "version.put", versionText));
		assertTrue(TestUtils.checkPath(updateFolderBase + "files/"));

		assertTrue(TestUtils.checkPath(updateFolderBase + "files/module1/"));
		assertTrue(TestUtils.checkPath(updateFolderBase + "files/module1/folder1/"));
		assertTrue(TestUtils.checkPath(updateFolderBase + "files/module1/folder1/m1f1a.txt"));
		assertTrue(TestUtils.checkText(updateFolderBase + "files/module1/folder1/m1f1a.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(updateFolderBase + "files/module1/folder1/m1f1c.txt"));
		assertTrue(TestUtils.checkText(updateFolderBase + "files/module1/folder1/m1f1c.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(updateFolderBase + "files/module1/folder2/"));
		assertTrue(TestUtils.checkPath(updateFolderBase + "files/module1/folder2/m1f2b.txt"));
		assertTrue(TestUtils.checkText(updateFolderBase + "files/module1/folder2/m1f2b.txt", 2, "NEW"));

		assertTrue(TestUtils.checkPath(updateFolderBase + "files/module2/"));
		assertTrue(TestUtils.checkPath(updateFolderBase + "files/module2/folder1/"));
		assertTrue(TestUtils.checkPath(updateFolderBase + "files/module2/folder1/m2f1a.txt"));
		assertTrue(TestUtils.checkText(updateFolderBase + "files/module2/folder1/m2f1a.txt", 2, "NEW"));
		assertTrue(TestUtils.checkPath(updateFolderBase + "files/module2/folder1/m2f1c.txt"));
		assertTrue(TestUtils.checkText(updateFolderBase + "files/module2/folder1/m2f1c.txt", 2, "NEW"));
	}
}
