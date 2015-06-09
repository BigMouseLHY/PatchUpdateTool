package com.bigmouse.put.remote.service;

import java.rmi.RemoteException;

import com.bigmouse.put.core.PatchUpdateException;
import com.bigmouse.put.utils.rmi.RMIRemoteService;

public interface RemoteService extends RMIRemoteService
{
	public void bindObervser(String ip, int port, String serviceName) throws RemoteException;
	
	public void patchAutomatic(String filePath) throws RemoteException, PatchUpdateException;
	
	public void init() throws RemoteException, PatchUpdateException;
	
	public void loadPackage(String filePath) throws RemoteException, PatchUpdateException;
	
	public void backup() throws RemoteException, PatchUpdateException;
	
	public void update() throws RemoteException, PatchUpdateException;
}
