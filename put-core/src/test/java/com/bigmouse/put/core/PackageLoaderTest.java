package com.bigmouse.put.core;

import com.bigmouse.put.core.config.ConfigService;
import com.bigmouse.put.core.config.ConfigServiceFactory;
import com.bigmouse.put.core.packageloader.PackageLoadService;
import com.bigmouse.put.core.packageloader.PackageLoadServiceFactory;

import junit.framework.TestCase;

public class PackageLoaderTest extends TestCase
{

	public void testLoadPackage() throws PatchUpdateException
	{
		ConfigService configService = ConfigServiceFactory.getConfigService();
		configService.loadConfig();
		
		UpdateContext context = new UpdateContext();
		
		PackageLoadService zipPackageLoader = PackageLoadServiceFactory.getPackageLoadService();
		zipPackageLoader.loadPackage(context, "/Users/lihaoyuan/TempFolder/put/source/归档.zip");
	}

}
