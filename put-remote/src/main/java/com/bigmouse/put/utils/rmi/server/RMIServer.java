package com.bigmouse.put.utils.rmi.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.util.HashMap;
import java.util.Map;

import com.bigmouse.put.utils.rmi.RMIRemoteService;

/**
 * RMI server provider<br />
 * This class is used for register RMI server port and RMI service class.<br />
 * e.g.<br />
 * <pre>
 * // Register RMI server port 65001
 * RMIServer.createServer(65001);
 * 
 * // RemoteObjService is a interface extend RMIRemoteService, see{@link RMIRemoteService}
 * // RemoteObj is implement RemoteObjService
 * RemoteObjService serviceObj = new RemoteObj();
 * 
 * // Register service named "TestService" to port 65001
 * RMIServer.appendService(65001, "TestService", serviceObj);
 * </pre>
 * @author lihaoyuan
 *
 */
public class RMIServer
{
	private static Map<Integer, Registry> registryMap = new HashMap<Integer, Registry>();
	
	/**
	 * Register RMI server port
	 * @param port
	 * @throws RemoteException
	 */
	public static void createServer(int port) throws RemoteException
	{
		Registry registry = getServer(port);
		if(registry == null)
		{
			registry = LocateRegistry.createRegistry(port);
			registry.list();
			registryMap.put(port, registry);
		}
	}
	
	public static void setRegistryPort(int regport)
	{
		if(RMISocketFactory.getSocketFactory() == null)
		{
			RMISocketFactory factory = new RMIUtilsSocketFactory(regport);
			try
			{
				RMISocketFactory.setSocketFactory(factory);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Register service to server port
	 * @param port RMI server port
	 * @param serviceName service name
	 * @param obj service class, must implement {@link RMIRemoteService}
	 * @throws RemoteException
	 */
	public static void appendService(int port, String serviceName, RMIRemoteService obj) throws RemoteException
	{
		if(registryMap.containsKey(port))
		{
			Registry registry = registryMap.get(port);
			registry.rebind(serviceName, obj);
		}
	}
	
	private static Registry getServer(int port)
	{
		if(registryMap.containsKey(port))
		{
			Registry registry = registryMap.get(port);
			return registry;
		}
		else
		{
			return null;
		}
	}
}
