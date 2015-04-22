package com.bigmouse.put.core.packageloader;

import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.core.UpdateContext;

/**
 * Load package file, unzip and check file structure
 * 1. unzip into temp folder
 * 2. create version folder and structure in it
 * 3. move files from temp folder to update version folder
 * @author lihaoyuan
 *
 */
public interface PackageLoadService
{
	public void loadPackage(UpdateContext context, String packageFilePath) throws PatchUpdateException;
}
