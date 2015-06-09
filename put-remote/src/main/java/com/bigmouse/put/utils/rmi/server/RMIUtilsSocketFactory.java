package com.bigmouse.put.utils.rmi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class RMIUtilsSocketFactory extends RMISocketFactory
{
	private int regport = 54321;
	
	public RMIUtilsSocketFactory(int port)
	{
		regport = port;
	}
	
	@Override
	public Socket createSocket(String host, int port) throws IOException
	{
		return new Socket(host, port);
	}

	@Override
	public ServerSocket createServerSocket(int port) throws IOException
	{
		if(port == 0) port = regport;
		return new ServerSocket(port);
	}

}
