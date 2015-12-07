package interfaces;

import java.awt.Graphics2D;

public interface DrawableLayer {

	/**
	 * Draws the layer on the specified graphics using the specified zoom
	 * factor.
	 * 
	 * @param g
	 * @param zoomFactor
	 */
	public abstract void Draw(Graphics2D g, double zoomFactor);

}