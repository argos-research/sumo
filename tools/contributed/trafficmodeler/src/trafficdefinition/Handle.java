package trafficdefinition;

import enums.MotionRestriction;
import interfaces.HandleListener;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import shapes.Point2DExt;

/**
 * Class representing a graphical UI handle that allows the user to drag it to change the shape or properties of a graphical element on the screen. The handle is drawn on screen as a small white rectangle.
 */
public class Handle implements Serializable, Cloneable {

	private static final long serialVersionUID = -8791712547811891550L;

	/**
	 * The handle's location
	 */
	private Point2DExt location;

	/**
	 * The handle's name
	 */
	private String name;

	/**
	 * The object that will receive notifications when the handle's location has
	 * changed
	 */
	private HandleListener listener;

	/**
	 * Restriction on the way that the handle can be moved. The handle's motion
	 * can be horizontal, vertical or unrestricted.
	 */
	private MotionRestriction restriction;

	/**
	 * Default constructor.
	 * 
	 * @param location
	 *            the handle's initial location
	 * @param name
	 *            the handle's name
	 * @param restriction
	 *            the handle's motion restriction
	 */
	public Handle(Point2D.Double location, String name, MotionRestriction restriction) {
		this.location = new Point2DExt(location);
		this.name = name;
		this.restriction = restriction;
	}

	/**
	 * Constructor that creates a new handle as a copy of the specified handle
	 * 
	 * @param source
	 */
	public Handle(Handle source) {
		this.location = new Point2DExt(source.location);
		this.name = source.name;
		this.restriction = source.restriction;
	}

	/**
	 * Registers the specified {@link HandleListener} as the handle's listener.
	 * This listener will be notified when the handle's location changes.
	 * 
	 * @param listener
	 */
	public void setHandleListener(HandleListener listener) {
		this.listener = listener;
	}

	/**
	 * Returns the handle's name
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the handle's location
	 * @return
	 * @uml.property  name="location"
	 */

	public Point2D.Double getLocation() {
		return (Point2D.Double) location;
	}

	/**
	 * Changes the handle's location to the specified point notifies any
	 * registered listeners
	 * 
	 * @param location
	 */
	public void setLocationAndNotify(Point2D.Double location) {
		setLocation(location);

		if (listener != null) {
			listener.handleLocationChanged(this);
		}
	}

	/**
	 * Sets the handles location without taking in mind the handle's motion
	 * restriction and without notifying any registered listeners
	 * 
	 * @param location
	 */
	public void setUnrestrictedLocation(Point2D.Double location) {
		this.location.x = location.x;
		this.location.y = location.y;
	}

	/**
	 * Sets the handle's location taking in mind the handle's motion restriction
	 * but without notifying any registered listeners
	 * 
	 * @param location
	 */

	private void setLocation(Point2D.Double location) {
		switch (restriction) {
		case Horizontal:
			this.location.x = location.x;
			break;
		case Vertical:
			this.location.y = location.y;
			break;
		case None:
			this.location.x = location.x;
			this.location.y = location.y;
			break;
		}
	}

	/**
	 * Draws the handle on the specified graphics. The handle is drawn as a 2x2
	 * pixels square independent from the zooming.
	 * 
	 * @param g
	 */
	public void Draw(Graphics2D g) {

		// Get the previous transform
		AffineTransform currentTransform = g.getTransform();

		// Convert the handle's location using the transform
		Point2D t = currentTransform.transform(location, null);

		// Reset the transform on the graphics object
		g.setTransform(new AffineTransform());
		Rectangle2D.Double e = new Rectangle2D.Double();

		// Draw the handle
		e.setFrameFromCenter(t.getX(), t.getY(), t.getX() + 2, t.getY() + 2);

		g.setColor(Color.WHITE);
		g.fill(e);
		g.setColor(Color.BLACK);
		g.draw(e);

		// Restore the transform
		g.setTransform(currentTransform);
	}

	/**
	 * Checks to see if the specified point lies within the handle. This method
	 * is used to check if the mouse pointer is over the handle.
	 * 
	 * @param p
	 * @param tx
	 * @return
	 */
	public boolean hit(Point p, AffineTransform tx) {
		Point2D t = tx.transform(location, null);

		// Be a little flexible, check a bit around the handle as well
		Rectangle2D.Double e = new Rectangle2D.Double();
		e.setFrameFromCenter(t.getX(), t.getY(), t.getX() + 5, t.getY() + 5);

		if (e.contains(p)) {
			return true;
		} else {
			return false;
		}
	}

}
