package interfaces;

import classes.Layer;
import classes.ProjectElement;

/**
 * Interface that defines a listener for the current layer changed event
 * 
 */
public interface CurrentLayerChangedListener {
	/**
	 * Event fired when the project's current layer has changed. This occurs
	 * when the user select a different layer.
	 * 
	 * @param newCurrentLayer The newly selected layer.
	 */
	public void CurrentLayerChanged(Layer<? extends ProjectElement> newCurrentLayer);
}
