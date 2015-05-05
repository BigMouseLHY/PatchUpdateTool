package com.bigmouse.put.core;

import org.junit.Test;

public class PUTCoreTest
{

	@Test
	public void testPatchAutomatic() throws PatchUpdateException
	{
		TestObserverFacade observer = new TestObserverFacade(new TestObserver());
		String filePath = "/Users/lihaoyuan/TempFolder/put/source/update.zip";
		PUTCore.patchAutomatic(observer, filePath);
	}

}
