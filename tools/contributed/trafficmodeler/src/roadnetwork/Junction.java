package roadnetwork;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import shapes.Polygon2D;

/**
 * Class representing a junction in the road network.
 */
public class Junction extends RoadNetworkElement implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The junction's type. This could be priority or traffic_lights
	 */
	private String type = "";

	@Override
	public String getToolTip() {
		return "<html>" + "<b>Junction</b> " + this.id + "<br>" + "<b>Type:</b> " + type + "</html>";
	}

	/**
	 * @return  the type
	 * @uml.property  name="type"
	 */
	public String getType() {
		return type;
	}

	/**
	 * Creates the junction's polygon based on the supplied points
	 * 
	 * @param shapepoints
	 *            List of points that define the junction's shape
	 */
	public void SetShape(List<Point2D.Float> shapepoints) {
		// If no points are defined for the shape just create an empty polygon
		if (shapepoints == null) {
			shapepoints = new ArrayList<Point2D.Float>();

			shapepoints.add(new Point2D.Float(0, 0));
		}

		int total = shapepoints.size();

		double[] coords = new double[total * 2];

		int i = 0;

		for (Point2D.Float p : shapepoints) {
			coords[i++] = p.x;
			coords[i++] = p.y;
		}

		shape = new Polygon2D.Double(coords);

	}

	/**
	 * @param type  the type to set
	 * @uml.property  name="type"
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Junction " + id;
	}

}
