package com.bigmouse.put.core.packageloader;

/**
 * Factory to create PackageLoadService instance
 * @author lihaoyuan
 *
 */
public class PackageLoadServiceFactory
{
	public static PackageLoadService getPackageLoadService()
	{
		return new ZipPackageLoader();
	}
}
