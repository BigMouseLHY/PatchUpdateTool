package com.bigmouse.put.core;

import java.util.List;

/*
 * Global config of PUT. All the properties are configed in conf.xml, you need not to set theme is you java code.
 * 
 * "PASE_PATH" is a path for unzip and backup files.
 * "PROGRAM_LIST" is a list of config the root path of the program you want to update. 
 * When error occurred during the process, "ERROR_HANDLE" can let the process rollback the update files or ignore the error.
 */
public class SystemConfig
{
	/**
	 * A place for unzip and backup files.
	 * e.g. BASE_PATH = /User/YourName/PUT  or  C:\PUT
	 */
	public static String BASE_PATH;
	
	/**
	 * The list of root path of the program.
	 */
	public static List<String> PROGRAM_LIST;
	
	/**
	 * How to do when error occurred.
	 * ROLLBACK: rollback the whole update files
	 * IGNORE: skip the error file and continue update
	 */
	public static String ERROR_HANDLE;
}
