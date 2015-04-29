package com.bigmouse.put.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class TestUtils
{
	
	public static boolean checkPath(String path)
	{
		File directory = new File(path);
		
		return directory.exists();
	}
	
	public static boolean checkText(String path, int lineNumber, String content) throws IOException
	{
		File file = new File(path);
		
		if(lineNumber > 0 && content != null)
		{
			@SuppressWarnings("unchecked")
			List<String> lines = FileUtils.readLines(file);
			if(lines.size() >= lineNumber && lines.get(lineNumber - 1).equals(content))
			{
				return file.exists();
			}
		}
		return false;
	}
	
	public static boolean checkText(String path, String[] contents) throws IOException
	{
		File file = new File(path);
		
		if(contents != null && contents.length > 0)
		{
			@SuppressWarnings("unchecked")
			List<String> lines = FileUtils.readLines(file);
			if(lines.size() >= contents.length)
			{
				if(lines.size() >= contents.length)
				{
					for(int i = 0; i < contents.length; i++)
					{
						if(!contents[i].equals(lines.get(i)))
						{
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}
}
