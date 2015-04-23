package com.bigmouse.put.core.observer;

/**
 * Observer of update process
 * @author lihaoyuan
 *
 */
public interface ProcessObserver
{
	/**
	 * Send message to observer
	 * @param msg message text
	 * @param type massage type, e.g. INFO ERROR WARN DEBUG 
	 */
	public void message(String msg, String type);
	
	/**
	 * Send error to observer
	 * @param msg message text
	 * @param e exception object
	 */
	public void message(String msg, Exception e);
}
