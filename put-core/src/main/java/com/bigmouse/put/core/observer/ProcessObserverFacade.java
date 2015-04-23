package com.bigmouse.put.core.observer;

/**
 * Facade of Obaserver, this can convert any class to fix ProcessObserver
 * @author lihaoyuan
 *
 * @param <T> any class you want to fix ProcessObserver
 */
public interface ProcessObserverFacade<T> extends ProcessObserver
{
	/**
	 * do facade
	 * @param observser
	 */
	public void facadeFor(T observser);
}
