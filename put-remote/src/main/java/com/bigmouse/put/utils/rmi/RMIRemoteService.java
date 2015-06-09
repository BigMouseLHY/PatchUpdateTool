package com.bigmouse.put.utils.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for remote service, all the service must implement this interface
 * @author lihaoyuan
 *
 */
public interface RMIRemoteService extends Remote
{
	/**
	 * Test for check the connection
	 * @throws RemoteException
	 */
	public void test() throws RemoteException;
}
