package com.bigmouse.put.core;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bigmouse.put.core.config.ConfigService;
import com.bigmouse.put.core.config.ConfigServiceFactory;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfigServiceTest
{
	@Test
	public void test01_loadConfig() throws PatchUpdateException
	{
		ConfigService configService = ConfigServiceFactory.getConfigService();
		UpdateContext context = UpdateContext.getContext();
		TestObserverFacade facade = new TestObserverFacade(new TestObserver());
		context.setObserver(facade);
		configService.loadConfig(context);
		
		assertEquals("/Users/lihaoyuan/TempFolder/put", SystemConfig.BASE_PATH);
		
		assertEquals("IGNORE", SystemConfig.ERROR_HANDLE);
		
		assertNotNull(SystemConfig.PROGRAM_MAP);
		assertEquals(3, SystemConfig.PROGRAM_MAP.size());
		
		assertTrue(SystemConfig.PROGRAM_MAP.containsKey("p1"));
		assertEquals("/Users/lihaoyuan/TempFolder/program1", SystemConfig.PROGRAM_MAP.get("p1"));
		
		assertTrue(SystemConfig.PROGRAM_MAP.containsKey("p2"));
		assertEquals("/Users/lihaoyuan/TempFolder/program2", SystemConfig.PROGRAM_MAP.get("p2"));
		
		assertTrue(SystemConfig.PROGRAM_MAP.containsKey("p3"));
		assertEquals("/Users/lihaoyuan/TempFolder/program3", SystemConfig.PROGRAM_MAP.get("p3"));
	}
}
