package com.bigmouse.put.utils.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import com.bigmouse.put.utils.rmi.RMIRemoteService;

/**
 * RMI服务调用类<br />
 * 该类用于获取RMI远程服务
 * 实例：<br />
 * <pre>
 * //获取rmi://192.168.1.2:65001/TestService服务
 * //RemoteObjService接口继承RMIRemoteService，描述远程方法，详见{@link RMIRemoteService}
 * //RemoteObj为RemoteObjService接口的实现类，实现了远程方法
 * RemoteObjService service = new RMIClient<RemoteObjService>().getRemoteService("192.168.1.2", 65001, "TestService");
 * service.saySomething();
 * </pre>
 * @author Haoyuan
 *
 * @param <T>
 */
public class RMIClient<T extends RMIRemoteService>
{
	private static Map<String, RMIRemoteService> remoteServiceMap = new HashMap<String, RMIRemoteService>();
	
	/**
	 * 获取RMI远程服务
	 * @param ip RMI远程服务IP
	 * @param port RMI远程服务端口
	 * @param name RMI远程服务名
	 * @return RMI远程服务对象
	 * @throws MalformedURLException RMI远程服务URL异常
	 * @throws RemoteException RMI远程服务调用异常
	 * @throws NotBoundException RMI远程服务未被绑定异常
	 */
	@SuppressWarnings("unchecked")
	public T getRemoteService(String ip, int port, String name) throws MalformedURLException, RemoteException, NotBoundException
	{
		RMIRemoteService service = null;
		if(remoteServiceMap.containsKey(ip + ":" + port + ":" + name))
		{
			service = remoteServiceMap.get(ip + ":" + port + ":" + name);
		}
		else
		{
			service = connect(ip, port, name);
		}
		int retryCount = 5;
		int index = 0;
		while(index < retryCount)
		{
			try
			{
				index++;
				service.test();
				break;
			}
			catch (Exception e)
			{
				remoteServiceMap.remove(ip + ":" + port + ":" + name);
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
				service = connect(ip, port, name);
			}
		}
		
		return (T)service;
	}
	
	@SuppressWarnings("unchecked")
	private synchronized RMIRemoteService connect(String ip, int port, String name) throws MalformedURLException, RemoteException, NotBoundException
	{
		RMIRemoteService service = null;
		if(!remoteServiceMap.containsKey(ip + ":" + port + ":" + name))
		{
		service = (T)Naming.lookup("rmi://" + ip + ":" + port + "/" + name);
		remoteServiceMap.put(ip + ":" + port + ":" + name, service);
		}
		else
		{
			service = remoteServiceMap.get(ip + ":" + port + ":" + name);
		}
		return service;
	}
}
