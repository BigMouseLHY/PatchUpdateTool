package com.bigmouse.put.core.config;

import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.core.UpdateContext;

/**
 * Load config file
 * @author lihaoyuan
 *
 */
public interface ConfigService
{
	public void loadConfig(UpdateContext context) throws PatchUpdateException;
}
