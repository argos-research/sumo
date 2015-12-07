package roadnetwork;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import shapes.Line2DExt;
import shapes.Polygon2D;
import trafficdefinition.TrafficDefinitionElement;

/**
 * Class that represents an edge (street) of the road network.
 */
public class Edge extends RoadNetworkElement implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * List containing any traffic definition elements that depent on this edge.
	 * If this list is not empty, then the edge will not be deleted.
	 */
	private List<TrafficDefinitionElement> dependentTrafficElements = null;

	/**
	 * The lines that represent the dividers between the edge's lanes.
	 */
	private List<Line2DExt> dividers = new ArrayList<Line2DExt>();

	/**
	 * The id of the junction that this edge starts from.
	 */
	private String from = "";

	/**
	 * The edge's lanes.
	 */
	private List<Lane> lanes;

	/**
	 * The edge's length in meters.
	 */
	private float length = 0;

	/**
	 * The edge's priority
	 */
	private int priority = 0;

	/**
	 * The edge's speed limit in m/s
	 */
	private float speedLimit = 0;

	/**
	 * The id of the junction that this edge ends to.
	 */
	private String to = "";

	/**
	 * Default constructor
	 * 
	 */
	public Edge() {
		lanes = new ArrayList<Lane>();
	}

	/**
	 * Adds the given {@link TrafficDefinitionElement} to the list of the
	 * elements that depent on this edge.
	 * 
	 * @param t
	 */
	public void AddDependentTrafficElement(TrafficDefinitionElement t) {
		if (dependentTrafficElements == null) {
			dependentTrafficElements = new ArrayList<TrafficDefinitionElement>();
		}

		if (!dependentTrafficElements.contains(t)) {
			dependentTrafficElements.add(t);
		}
	}

	/**
	 * Adds a new lane to the edge.
	 * 
	 * @param l
	 */
	public void AddLane(Lane l) {
		lanes.add(l);
	}

	/**
	 * Method that creates the shape of the edge. This method creates the
	 * polygon that defines the edge's shape and the dividers that are drawn
	 * between the lanes.
	 * 
	 */
	public void CalculateShape() {
		// Each lane is 3.2 meters wide.A lane is defined by a series of points
		// that pass through the lane's center. All lanes have the same number
		// of points. The dividers are calculated as the lines passing between
		// two consecutive lane points

		// Calculate the width of half the edge.
		double halfwidth = 1.6 * lanes.size();

		// Get the total points that the edge has
		int parts = Integer.MAX_VALUE;
		//TODO what happens if each lane has a different number of parts
		//int parts = lanes.get(0).GetTotalPoints();

		for(Lane l:lanes){
			parts = Math.min(parts, l.GetTotalPoints());
		}
		
		// Lists holding the left and right side points that will define the
		// polygon of the edge.
		List<Point2D.Double> leftPoints = new ArrayList<Point2D.Double>();
		List<Point2D.Double> rightPoints = new ArrayList<Point2D.Double>();

		// Lists holding the previous and next points for all lanes
		List<Point2D.Double> previousLanePoints = new ArrayList<Point2D.Double>();
		List<Point2D.Double> nextLanePoints = new ArrayList<Point2D.Double>();

		Point2D.Double previousCenter;
		Point2D.Double nextCenter;

		Point2D.Double newLeftPoint;
		Point2D.Double newRightPoint;

		//Loop through the number of points that the edge passes through
		for (int i = 0; i < parts; i++) {
			previousLanePoints.clear();
			nextLanePoints.clear();

			// last part
			if (i == parts - 1) {
				for (Lane l : lanes) {
					previousLanePoints.add(l.GetPoint(i));
					nextLanePoints.add(l.GetPoint(i - 1));
				}
			}
			// all others
			else {
				for (Lane l : lanes) {
					previousLanePoints.add(l.GetPoint(i));
					nextLanePoints.add(l.GetPoint(i + 1));
				}
			}

			// Calculate dividers
			Point2D.Double lineStart = new Point2D.Double();
			Point2D.Double lineEnd = new Point2D.Double();

			//We have as many dividers as lanes minus one
			for (int dp = 0; dp < lanes.size() - 1; dp++) {

				lineStart.x = (previousLanePoints.get(dp).x + previousLanePoints.get(dp + 1).x) / 2;
				lineStart.y = (previousLanePoints.get(dp).y + previousLanePoints.get(dp + 1).y) / 2;

				lineEnd.x = (nextLanePoints.get(dp).x + nextLanePoints.get(dp + 1).x) / 2;
				lineEnd.y = (nextLanePoints.get(dp).y + nextLanePoints.get(dp + 1).y) / 2;

				dividers.add(new Line2DExt(lineStart, lineEnd));
			}

			// Find the center of the previous and next points
			previousCenter = PointsCenter(previousLanePoints);
			nextCenter = PointsCenter(nextLanePoints);

			// if they are horizontal
			if (previousCenter.y == nextCenter.y) {
				newLeftPoint = new Point2D.Double(previousCenter.x, previousCenter.y - halfwidth);
				newRightPoint = new Point2D.Double(previousCenter.x, previousCenter.y + halfwidth);
			}
			// if they are vertical
			else if (previousCenter.x == nextCenter.x) {
				newLeftPoint = new Point2D.Double(previousCenter.x - halfwidth, previousCenter.y);
				newRightPoint = new Point2D.Double(previousCenter.x + halfwidth, previousCenter.y);
			}
			// if they are diagonal
			else {
				// use circles
				double m = -(nextCenter.x - previousCenter.x) / (nextCenter.y - previousCenter.y);

				newLeftPoint = new Point2D.Double((halfwidth / Math.sqrt(m * m + 1)) + previousCenter.x, previousCenter.y + m * halfwidth / Math.sqrt(m * m + 1));

				newRightPoint = new Point2D.Double((-halfwidth / Math.sqrt(m * m + 1)) + previousCenter.x, previousCenter.y - m * halfwidth / Math.sqrt(m * m + 1));
			}

			leftPoints.add(newLeftPoint);
			rightPoints.add(newRightPoint);
		}

		double[] f = new double[(parts) * 2 * 2];
		int v = 0;

		for (Point2D.Double k : rightPoints) {
			f[v++] = k.x;
			f[v++] = k.y;
		}

		for (int k = leftPoints.size() - 1; k >= 0; k--) {
			f[v++] = leftPoints.get(k).x;
			f[v++] = leftPoints.get(k).y;
		}

		shape = new Polygon2D.Double(f);
	}

	/**
	 * Calculates the distance from another edge.
	 * @param e
	 * @return the distance in meters between the two edges
	 */
	public int DistanceFrom(Edge e) {
		return (int) Math.round(lanes.get(0).GetPoint(0).distance(e.lanes.get(0).GetPoint(0)));
	}

	/**
	 * Calculates the distance from the specified point
	 * @param point
	 * @return
	 */
	public double DistanceFrom(Point2D.Double point) {
		return lanes.get(0).GetPoint(0).distance(point);
	}

	@Override
	public void Draw(Graphics2D g, double zoomFactor, boolean isDeleted, boolean isSelected) {

		super.Draw(g, zoomFactor, isDeleted, isSelected);

		// Save the previous color to restore it later
		Color prev = g.getColor();

		// Draw the lane divider lines in yellow
		g.setColor(Color.YELLOW);

		// Draw the dividers only if we are zoomed in enough
		if (zoomFactor > 2.f) {
			Stroke previous = g.getStroke();

			float[] dashes = { 1.f, 2.f };
			g.setStroke(new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.f, dashes, 0.f));

			for (Line2D.Double l : dividers) {
				g.draw(l);
			}

			g.setStroke(previous);
		}

		g.setColor(prev);
	}

	/**
	 * @return  the from
	 * @uml.property  name="from"
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @return  the lanes
	 * @uml.property  name="lanes"
	 */
	public List<Lane> getLanes() {
		return lanes;
	}

	/**
	 * @return  the length
	 * @uml.property  name="length"
	 */
	public float getLength() {
		return length;
	}

	/**
	 * @return  the priority
	 * @uml.property  name="priority"
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @return  the speedLimit
	 * @uml.property  name="speedLimit"
	 */
	public float getSpeedLimit() {
		return speedLimit;
	}

	/**
	 * @return  the to
	 * @uml.property  name="to"
	 */
	public String getTo() {
		return to;
	}

	@Override
	public String getToolTip() {
		return "<html>" + "<b>Edge</b> " + id + "<br>" + "<b>Priority:</b> " + priority + "<br>" + "<b>Speed limit:</b> " + speedLimit + "<br>" + "</html>";
	}

	/**
	 * Checks whether this edge has traffic definition elements that depend on it.
	 * 
	 * @return true if the edge has dependent traffic elements, false otherwise
	 */
	public boolean HasDependentTrafficElements() {
		if (dependentTrafficElements == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns the list of traffic elements that depend on this edge
	 * @return
	 * @uml.property  name="dependentTrafficElements"
	 */
	public List<TrafficDefinitionElement> getDependentTrafficElements(){
		return dependentTrafficElements;
	}
	
	/**
	 * Removes the specified traffic element from this edge's list of dependent traffic elements
	 * @param t the traffic element to remove
	 */
	public void RemoveDependentTrafficElement(TrafficDefinitionElement t) {
		if ((dependentTrafficElements != null) && (dependentTrafficElements.contains(t))) {
			dependentTrafficElements.remove(t);

			if (dependentTrafficElements.isEmpty()) {
				dependentTrafficElements = null;
			}
		}
	}

	/**
	 * @param from  the from to set
	 * @uml.property  name="from"
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @param length  the length to set
	 * @uml.property  name="length"
	 */
	public void setLength(float length) {
		this.length = length;
	}

	/**
	 * @param priority  the priority to set
	 * @uml.property  name="priority"
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @param speedLimit  the speedLimit to set
	 * @uml.property  name="speedLimit"
	 */
	public void setSpeedLimit(float speed) {
		this.speedLimit = speed;
	}

	/**
	 * @param to  the to to set
	 * @uml.property  name="to"
	 */
	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Edge " + id;
	}

	/**
	 * Calculates the center of the specified points by averaging them
	 * @param points
	 * @return
	 */
	private Point2D.Double PointsCenter(List<Point2D.Double> points) {
		Point2D.Double center = new Point2D.Double(0, 0);

		for (Point2D.Double p : points) {
			center.x += p.x;
			center.y += p.y;
		}

		center.x /= points.size();
		center.y /= points.size();

		return center;
	}

}
