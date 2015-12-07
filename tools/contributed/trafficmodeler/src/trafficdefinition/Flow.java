package trafficdefinition;

import classes.Pair;
import classes.Project;
import classes.TypeSelection;
import helpers.DrawingHelper;
import helpers.TimeConverter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import roadnetwork.Edge;
import shapes.Line2DExt;
import shapes.Point2DExt;
import shapes.Rectangle2DExt;
import simulation.VehicleType;

/**
 * Class representing a flow of vehicles from one edge to another. The flow is drawn on screen using an arrow from the source edge to the destination edge. Vehicles will be emitted at random points in time between beginTime and endTime.
 */
public class Flow extends TrafficDefinitionElement implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The time that vehicles will start being emitted
	 */
	private int beginTime = 0;

	/**
	 * The time that vehicles will end being emitted
	 */
	private int endTime = 100;

	/**
	 * The number of vehicles to emit
	 */
	private int numberOfVehicles = 100;

	/**
	 * The flow's color. All created vehicles and routes will have this color.
	 */
	private Color color = Color.BLUE;

	/**
	 * The edge from which the vehicles will start
	 */
	private Edge start = null;

	/**
	 * The edge to which the vehicles will end
	 */
	private Edge end = null;

	/**
	 * The selection of the vehicle types that will be created for this area flow.
	 * @uml.property  name="vehicleSelection"
	 * @uml.associationEnd  
	 */
	private TypeSelection<VehicleType> vehicleSelection;

	/**
	 * The line used to draw the flow
	 */
	private Line2DExt shape;

	/**
	 * The point from which the line starts. This is the starting edge's center.
	 */
	private Point2DExt from;

	/**
	 * Default constructor. Assigns a random name to area flow and selects the
	 * default vehicle type.
	 * 
	 */
	public Flow() {
		super();
		
		name = "Flow " + String.valueOf(increment++);

		vehicleSelection = new TypeSelection<VehicleType>();
		vehicleSelection.add(new Pair<VehicleType, Float>(Project.getCurrentlyLoadedProject().getDefaultVehicleType(), 1.f));
	}

	/**
	 * Class constructor that creates a new flow as a copy of the specified flow
	 * 
	 * @param source
	 */
	public Flow(Flow source) {
		super(source);
		
		beginTime = source.beginTime;
		endTime = source.endTime;
		numberOfVehicles = source.numberOfVehicles;
		color = new Color(source.color.getRGB());

		start = source.start;
		end = source.end;
		
		shape = new Line2DExt(source.shape);
		from = new Point2DExt(source.from);

		vehicleSelection = new TypeSelection<VehicleType>(source.vehicleSelection);

		SetDependencies();
	}

	@Override
	public void Draw(Graphics2D g, double zoomFactor, boolean isDeleted, boolean isSelected) {
		// Save the previous color to restore it later
		Color prev = g.getColor();

		if (!isSelected) {
			g.setColor(color);
		}

		if (isSelected) {
			g.setColor(Color.GREEN);
		}

		// Draw the direction arrow
		if (shape != null) {

			DrawingHelper.drawArrowHead(g, shape.x1, shape.y1, shape.x2, shape.y2, 1.f);

			g.draw(shape);
		}

		g.setColor(prev);
	}

	public Rectangle2DExt getBounds() {
		return new Rectangle2DExt((Rectangle2D.Double)shape.getBounds2D());
	}

	/**
	 * @return  the beginTime
	 * @uml.property  name="beginTime"
	 */
	public int getBeginTime() {
		return beginTime;
	}

	/**
	 * @return  the color
	 * @uml.property  name="color"
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return  the end
	 * @uml.property  name="end"
	 */
	public Edge getEnd() {
		return end;
	}

	/**
	 * @return  the endTime
	 * @uml.property  name="endTime"
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * @return  the numberOfVehicles
	 * @uml.property  name="numberOfVehicles"
	 */
	public int getNumberOfVehicles() {
		return numberOfVehicles;
	}

	/**
	 * @return  the start
	 * @uml.property  name="start"
	 */
	public Edge getStart() {
		return start;
	}

	@Override
	public String getToolTip() {
		return "<html>" + "<b>Flow</b> " + name + "<br>" + "<b>From:</b> " + start.getId() + "<br>" + "<b>To:</b> " + end.getId() + "<br>" + "<b>Number of vehicles:</b> " + numberOfVehicles + "<br>" + "<b>Start time:</b> " + TimeConverter.toString(beginTime) + "<br>" + "<b>End time:</b> " + TimeConverter.toString(endTime) + "</html>";
	}

	/**
	 * @return  the vehicleSelection
	 * @uml.property  name="vehicleSelection"
	 */
	public TypeSelection<VehicleType> getVehicleSelection() {
		return vehicleSelection;
	}

	public void handleLocationChanged(Handle h) {
		// No handles for flows
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
	 * @param beginTime  the beginTime to set
	 * @uml.property  name="beginTime"
	 */
	public void setBeginTime(int beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @param color  the color to set
	 * @uml.property  name="color"
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Adds the dependencies to the starting and ending edges so that they
	 * cannot be deleted.
	 * 
	 */
	public void SetDependencies() {
		start.AddDependentTrafficElement(this);
		end.AddDependentTrafficElement(this);
	}

	/**
	 * Removes the dependencies from the starting and ending edges. This method
	 * is called before the flow is deleted.
	 * 
	 */
	public void ReleaseDependencies() {
		start.RemoveDependentTrafficElement(this);
		end.RemoveDependentTrafficElement(this);
	}

	/**
	 * @param end  the end to set
	 * @uml.property  name="end"
	 */
	public void setEnd(Edge end) {
		this.end = end;
	}

	/**
	 * @param endTime  the endTime to set
	 * @uml.property  name="endTime"
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public void setFrom(Point2D.Float from) {
		this.from = new Point2DExt(from.x, from.y);
	}

	/**
	 * @param numberOfVehicles  the numberOfVehicles to set
	 * @uml.property  name="numberOfVehicles"
	 */
	public void setNumberOfVehicles(int numberOfVehicles) {
		this.numberOfVehicles = numberOfVehicles;
	}

	/**
	 * @param start  the start to set
	 * @uml.property  name="start"
	 */
	public void setStart(Edge start) {
		this.start = start;
	}

	public void setTo(Point2D.Double to) {
		shape = new Line2DExt(from, to);
	}

	/**
	 * @param vehicleSelection  the vehicleSelection to set
	 * @uml.property  name="vehicleSelection"
	 */
	public void setVehicleSelection(TypeSelection<VehicleType> vehicleSelection) {
		this.vehicleSelection = vehicleSelection;
	}

	@Override
	public TrafficDefinitionElement elementClone() {
		return new Flow(this);
	}

	@Override
	public String toXML() {
		StringBuilder sb = new StringBuilder();

		sb.append("<flow name=\""+name+"\" from=\""+start.getId()+"\" to=\""+end.getId()+"\" startingTime=\""+String.valueOf(beginTime)+"\" endingTime=\""+String.valueOf(endTime)+"\" numberOfVehicles=\""+String.valueOf(numberOfVehicles)+"\">\n");
		
		sb.append("<vehicleSelection>\n");
		
		for(Pair<VehicleType,Float> vs:vehicleSelection){
			sb.append("<selectedVehicle vehicleType=\""+ vs.getFirst().getName()+"\" probability=\""+vs.getSecond().toString()+"\"/>\n");			
		}
		
		sb.append("</vehicleSelection>\n");
		
		sb.append("</flow>\n");
		
		return sb.toString();
	}
}
