package com.bigmouse.put.core.update;

/**
 * Factory to create UpdateService instance
 * @author lihaoyuan
 *
 */
public class UpdateServiceFactory
{
	public static UpdateService getUpdateService()
	{
		return new DefaultUpdateHandler();
	}
}
