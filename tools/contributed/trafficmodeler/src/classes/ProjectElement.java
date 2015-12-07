package classes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import shapes.Rectangle2DExt;

/**
 * Abstract class representing a project element (traffic or road network element)
 *
 */
public abstract class ProjectElement extends Object{
	/**
	 * Draws the element on the specified graphics
	 * @param g The graphics object to paint the element on
	 * @param zoomFactor The zoom factor to use when painting the element
	 * @param isDeleted Flag to state whether the element is deleted so that it will be drawn differently
	 * @param isSelected Flag to state whether the element is selected so that it will be drawn differently
	 */
	public abstract void Draw(Graphics2D g, double zoomFactor, boolean isDeleted,boolean isSelected);

	/**
	 * Returns a string that will be used as a tooltip when the mouse hovers over the element
	 * @return the element's tooltip
	 */
	public abstract String getToolTip();
	
	/**
	 * Tests if the specified point is inside the element
	 * @param p
	 * @return true if the point lies inside the element, false otherwise
	 */
	public abstract boolean Hit(Point p);
	
	/**
	 * Tests if the specified rectangle instersects the element
	 * @param r
	 * @return true if the rectangle intersects the element, false otherwise
	 */
	public abstract boolean Hit(Rectangle r);
	
	/**
	 * Returns the element's bounds
	 * @return
	 */
	public abstract Rectangle2DExt getBounds();

}
