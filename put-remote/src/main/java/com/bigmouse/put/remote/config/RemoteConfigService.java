package com.bigmouse.put.remote.config;

import com.bigmouse.put.core.PatchUpdateException;

public interface RemoteConfigService
{
	public void loadRemoteConfig() throws PatchUpdateException;
}
