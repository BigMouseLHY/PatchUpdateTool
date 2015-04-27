package com.bigmouse.put.core.config;

import com.bigmouse.put.core.PatchUpdateException;

/**
 * Load config file
 * @author lihaoyuan
 *
 */
public interface ConfigService
{
	public void loadConfig() throws PatchUpdateException;
}
