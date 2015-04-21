package com.bigmouse.put.core;

public class PatchUpdateException extends Exception
{
	private static final long serialVersionUID = -664416245493242786L;

	public PatchUpdateException(String errorMsg)
	{
		super(errorMsg);
	}

	public PatchUpdateException(String errorMsg, Exception e)
	{
		super(errorMsg, e);
	}
}
