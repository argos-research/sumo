package roadnetwork;

import classes.ProjectElement;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.Serializable;
import shapes.Polygon2D;
import shapes.Rectangle2DExt;

/**
 * Abstract class representing a road network element(edge or junction)
 */
public abstract class RoadNetworkElement extends ProjectElement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8383351961238071184L;

	/**
	 * The element's Id
	 */
	protected String id;

	/**
	 * The element's shape
	 */
	protected Polygon2D.Double shape;

	@Override
	public void Draw(Graphics2D g, double zoomFactor, boolean isDeleted, boolean isSelected) {

		// Save the previous color to restore it later
		Color prev = g.getColor();

		if ((!isSelected) && (!isDeleted)) {
			g.setColor(Color.BLACK);
		}

		if ((isSelected) && (!isDeleted)) {
			g.setColor(Color.GREEN);
		}

		if ((!isSelected) && (isDeleted)) {
			g.setColor(Color.RED);
		}

		if ((isSelected) && (isDeleted)) {
			g.setColor(Color.ORANGE);
		}

		// Draw the polygon representing the element
		g.fill(shape);

		g.setColor(prev);
	}

	/**
	 * Returns the element's bounds
	 * 
	 * @return
	 */
	public Rectangle2DExt getBounds() {
		return new Rectangle2DExt(shape.getBounds2D());
	}

	/**
	 * Returns the element's IDs
	 * @return
	 * @uml.property  name="id"
	 */
	public String getId() {
		return id;
	}

	@Override
	public boolean Hit(Point p) {

		if (shape.getBounds2D().contains(p.x, p.y)) {
			if (shape.contains(p.x, p.y)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean Hit(Rectangle transformedRectangle) {
		return shape.intersects(transformedRectangle);
	}

	/**
	 * Checks whether the specified shape intersects the element
	 * 
	 * @param s
	 * @return
	 */
	public boolean Overlaps(Shape s) {
		return shape.overlaps(s);
	}

	/**
	 * Sets the element's id
	 * @param  id
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}
}
