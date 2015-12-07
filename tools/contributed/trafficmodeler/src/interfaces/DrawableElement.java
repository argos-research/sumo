package interfaces;

import java.awt.Graphics2D;

public interface DrawableElement {

	/**
	 * Draws the element on the specified graphics using the specified zoom
	 * factor.
	 */
	public abstract void Draw(Graphics2D g, double zoomFactor, boolean isDeleted, boolean isSelected);

}