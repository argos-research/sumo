package trafficdefinition;

import classes.Pair;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import roadnetwork.Edge;
import roadnetwork.Lane;
import shapes.Line2DExt;
import shapes.Point2DExt;
import shapes.Rectangle2DExt;

/**
 * Class representing an accident. An accident closes specific lanes of an edge by setting a vehicle to stop at a specified point on the lane. The accident is drawn on screen using an X on the edge.
 */
public class Accident extends TrafficDefinitionElement implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The edge on which the accident happens
	 */
	private Edge edge = null;
	
	/**
	 * The exact location where the accident happens
	 */
	private Point2DExt location = null;

	/**
	 * The relative location of the accident from the edge starting point
	 */
	private float edgeRelativeLocation = 0;
	
	/**
	 * The time that the edge re-opens
	 */
	private int endingTime;

	/**
	 * The shape to draw for the accident
	 */
	private Shape shape = null;

	/**
	 * The time that the accident happens and the edge closes
	 */
	private int startingTime;

	/**
	 * The lanes of the edge affected by the accident. 
	 */
	private List<Pair<Lane,Boolean>> affectedLanes = new ArrayList<Pair<Lane,Boolean>>();

	/**
	 * Class constructor that creates a new accident as a copy of the specified
	 * accident
	 * 
	 * @param source
	 */
	public Accident(Accident source) {
		super(source);

		startingTime = source.startingTime;
		endingTime = source.endingTime;

		edge = source.edge;

		shape = source.shape;
		
		location = source.location;

		edgeRelativeLocation = source.edgeRelativeLocation;
		
		for (Pair<Lane,Boolean> al : source.affectedLanes) {
			affectedLanes.add(new Pair<Lane, Boolean>(al.getFirst(),al.getSecond().booleanValue()));
		}
		
		//Set the accident to be dependent on the edge so that the edge can not be deleted
		edge.AddDependentTrafficElement(this);
	}

	/**
	 * Default constructor. 
	 */
	public Accident(Edge edge, Point2D.Double location) {
		super();

		name = "Accident " + String.valueOf(increment++);

		this.edge = edge;

		this.location = new Point2DExt(location);
		
		shape = new Rectangle2DExt(location.x-10,location.y-10,20,20);

		//Find the point on the edge where the accident happens relative to the edge starting point
		Point2D edgeStartingPoint = edge.getLanes().get(0).GetPoint(0);
			
		double relativeLocation = Math.min(edgeStartingPoint.distance(this.location),edge.getLength());
		
		if(relativeLocation>edge.getLength()){
			edgeRelativeLocation = edge.getLength()/2f;
		}
		else{
			edgeRelativeLocation = (float)relativeLocation;
		}
		
		// Close all lanes
		for(Lane l:edge.getLanes()){
			affectedLanes.add(new Pair<Lane, Boolean>(l,true));
		}
		
		//Set the accident to be dependent on the edge so that the edge can not be deleted
		edge.AddDependentTrafficElement(this);
		//TODO Should set dependencies to connected edges
	}

	@Override
	public void Draw(Graphics2D g, double zoomFactor, boolean isDeleted, boolean isSelected) {

		// Save the previous color to restore it later
		Color prev = g.getColor();

		if (!isSelected) {
			g.setColor(Color.RED);
		}

		if (isSelected) {
			g.setColor(Color.GREEN);
		}
		
		// Draw the shape
		Line2DExt l1= new Line2DExt(new Point2DExt(location.x-10,location.y-10),new Point2DExt(location.x+10,location.y+10));
		Line2DExt l2= new Line2DExt(new Point2DExt(location.x-10,location.y+10),new Point2DExt(location.x+10,location.y-10));
		
		Stroke previousStroke = g.getStroke();
		
		g.setStroke(new BasicStroke(6,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		g.draw(l1);
		g.draw(l2);
		
		//g.fill(shape);

		g.setColor(prev);
		g.setStroke(previousStroke);
	}

	@Override
	public TrafficDefinitionElement elementClone() {
		return new Accident(this);
	}

	/**
	 * @return  the endingTime
	 * @uml.property  name="endingTime"
	 */
	public int getEndingTime() {
		return endingTime;
	}

	/**
	 * @return  the startingTime
	 * @uml.property  name="startingTime"
	 */
	public int getStartingTime() {
		return startingTime;
	}

	@Override
	public String getToolTip() {
		String tooltip = "<html>" + "<b>Accident</b> " + name + "<br>" + "<b>Starting time: </b> " + String.valueOf(startingTime) + "<br>" + "<b>Ending time: </b>" + String.valueOf(endingTime);

		return tooltip;
	}

	public Rectangle2DExt getBounds() {
		return new Rectangle2DExt((Rectangle2D.Double) shape.getBounds2D());
	}

	public void handleLocationChanged(Handle h) {
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
	 * @param endingTime  the endingTime to set
	 * @uml.property  name="endingTime"
	 */
	public void setEndingTime(int endingTime) {
		this.endingTime = endingTime;
	}

	/**
	 * @param startingTime  the startingTime to set
	 * @uml.property  name="startingTime"
	 */
	public void setStartingTime(int startingTime) {
		this.startingTime = startingTime;
	}

	/**
	 * @return  the edge
	 * @uml.property  name="edge"
	 */
	public Edge getEdge() {
		return edge;
	}

	/**
	 * Returns the affected lanes from the accident.
	 * @uml.property  name="affectedLanes"
	 */
	public List<Pair<Lane,Boolean>> getAffectedLanes() {
		return affectedLanes;
	}

	/**
	 * @return  the edgeRelativeLocation
	 * @uml.property  name="edgeRelativeLocation"
	 */
	public float getEdgeRelativeLocation() {
		return edgeRelativeLocation;
	}

	@Override
	public String toXML() {
		StringBuilder sb=new StringBuilder();
		
		sb.append("<accident name=\""+name+"\" streetName=\""+edge.getId()+"\" startingTime=\""+String.valueOf(startingTime)+"\" endingTime=\""+String.valueOf(endingTime)+"\" location=\""+String.valueOf(edgeRelativeLocation)+"\" >\n");
		sb.append("<affectedLanes>\n");
		
		for(Pair<Lane,Boolean> l:affectedLanes){
			if(l.getSecond()){
				sb.append("<lane name=\""+l.getFirst().getId()+"\" />\n");
			}
		}
		
		sb.append("</affectedLanes>\n");
		sb.append("</accident>\n");
		
		return sb.toString();
	}
}
