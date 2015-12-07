package interfaces;

import trafficdefinition.Handle;

/**
 * Interface used by project elements to be notified when the user moves a
 * handle on the screen.
 * 
 */
public interface HandleListener {
	/**
	 * Called when the user moves a handle on the screen
	 * @param h The handle that was moved
	 */
	public void handleLocationChanged(Handle h);
}
