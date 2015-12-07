package trafficdefinition;

import classes.Pair;
import classes.Project;
import classes.TypeSelection;
import enums.MotionRestriction;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import shapes.Ellipse2DExt;
import shapes.Point2DExt;
import shapes.Rectangle2DExt;
import simulation.VehicleType;
import tdl.XMLHelpers;

/**
 * Class representing a hotspot. A hotspot can be defined as incoming, outgoing, or both. An incoming hotspot serves as a destination for vehicles that start from anywhere on the map. An outgoing hotspot serves as the source of vehicles that drive to anywhere on the map. If the hotspot is both incoming and outgoing, the vehicles that leave the hotspot will drive back where they came from. The hotspot is drawn on screen using an ellipsoid.
 */
public class HotSpot extends TrafficDefinitionElement implements Serializable {

	/**
	 * Enumeration used to define how the incoming time will be used when creating traffic for the hotspot.
	 */
	public enum DirectionInTimeType {
		/**
		 * Specifies that the time entered by the user is the time that the
		 * vehicles will be arriving to their destination. The emission time of
		 * the vehicles is estimated based on their distance from the
		 * destination.
		 */
		TimeOfArrivalToDestination,

		/**
		 * Specifies that the time entered by the user is the time that the
		 * vehicles will be leaving their source (i.e. their emission time)
		 */
		TimeOfDepartureFromSource
	};

	private static final long serialVersionUID = 1L;

	/**
	 * The ellipsoid defining the shape of the hotspot
	 */
	private Ellipse2DExt area;

	/**
	 * The hotspot's center
	 */
	private Point2DExt center;

	/**
	 * The hotspot's color. All created vehicles and routes will have this
	 * color.
	 */
	private Color color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());

	/**
	 * Flag to know whether this hotspot is an incoming hotspot
	 */
	private boolean DirectionIn = false;

	/**
	 * The time that vehicles will start coming (or arriving) to the hotspot.
	 * How this value will be used is defined by the value of the
	 * DirectionInType variable.
	 */
	private int directionInBeginTime = 0;

	/**
	 * The time that vehicles will stop coming (or arriving) to the hotspot. How
	 * this value will be used is defined by the value of the DirectionInType
	 * variable.
	 */
	private int directionInEndTime = 100;

	/**
	 * How the incoming time will be used when creating traffic for the hotspot
	 */
	private DirectionInTimeType DirectionInType = DirectionInTimeType.TimeOfDepartureFromSource;

	/**
	 * Flag to know whether this hotspot is an outgoing hotspot
	 */
	private boolean DirectionOut = false;

	/**
	 * The time that vehicles will start leaving from the hotspot.
	 */
	private int directionOutBeginTime = 0;

	/**
	 * The time that vehicles will end leaving from the hotspot.
	 */
	private int directionOutEndTime = 100;

	/**
	 * The number of vehicles to create
	 */
	private int numberOfVehicles = 100;

	/**
	 * The hotspot's horizontal radius
	 */
	private double radiusx;

	/**
	 * The hotspot's vertical radius
	 */
	private double radiusy;

	/**
	 * The selection of the vehicle types that will be created for this hotspot
	 * @uml.property  name="vehicleSelection"
	 * @uml.associationEnd  
	 */
	private TypeSelection<VehicleType> vehicleSelection;

	/**
	 * Default constructor. Assigns a random name to the hotspot and selects
	 * the default vehicle type.
	 * @param center 
	 */
	public HotSpot(Point2D.Double center) {
		super();
		
		name = "Hotspot " + String.valueOf(increment++);

		this.center = new Point2DExt(center);

		area = new Ellipse2DExt();

		vehicleSelection = new TypeSelection<VehicleType>();
		vehicleSelection.add(new Pair<VehicleType, Float>(Project.getCurrentlyLoadedProject().getDefaultVehicleType(), 1.f));
	}

	/**
	 * Class constructor that creates a new hotspot as a copy of the specified hotspot
	 * 
	 * @param source
	 */
	public HotSpot(HotSpot source) {
		super(source);
		
		numberOfVehicles = source.numberOfVehicles;
		color = new Color(source.color.getRGB());
		
		area = new Ellipse2DExt(source.area);
		center = new Point2DExt(source.center);
		radiusx = source.radiusx;
		radiusy = source.radiusy;
		
		DirectionIn = source.DirectionIn;
		directionInBeginTime = source.directionInBeginTime;
		directionInEndTime = source.directionInEndTime;
		
		DirectionInType = source.DirectionInType;
		
		DirectionOut = source.DirectionOut;
		directionOutBeginTime = source.directionOutBeginTime;
		directionOutEndTime = source.directionOutEndTime;
		
		vehicleSelection = new TypeSelection<VehicleType>(source.vehicleSelection);
	}

	@Override
	public void Draw(Graphics2D g, double zoomFactor, boolean isDeleted, boolean isSelected) {

		// Save the previous color to restore it later
		Color prev = g.getColor();

		if (!isSelected) {
			g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
		}

		if (isSelected) {
			g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 200));
		}

		// Draw the ellipsoid
		if (area != null) {
			g.fill(area);
		}

		// Draw the handles
		super.Draw(g, zoomFactor, isDeleted, isSelected);

		g.setColor(prev);
	}

	public Rectangle2DExt getBounds() {
		Rectangle2D bounds = area.getBounds2D();
		
		return new Rectangle2DExt(bounds.getCenterX(),bounds.getCenterY(),bounds.getWidth(),bounds.getHeight());
	}

	/**
	 * Creates the handles that enable UI manipulation of the hotspot
	 */
	public void createHandles() {
		List<Handle> handles = new ArrayList<Handle>();

		Handle centerHandle = new Handle(center, "center", MotionRestriction.None);
		Handle XRadiusHandle = new Handle(new Point2D.Double(center.x + radiusx, center.y), "XRadius", MotionRestriction.Horizontal);
		Handle YRadiusHandle = new Handle(new Point2D.Double(center.x, center.y + radiusy), "YRadius", MotionRestriction.Vertical);

		handles.add(centerHandle);
		handles.add(XRadiusHandle);
		handles.add(YRadiusHandle);

		addHandles(handles);
	}

	/**
	 * @return  the area
	 * @uml.property  name="area"
	 */
	public Ellipse2DExt getArea() {
		return area;
	}

	/**
	 * @return  the color
	 * @uml.property  name="color"
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return  the directionInBeginTime
	 * @uml.property  name="directionInBeginTime"
	 */
	public int getDirectionInBeginTime() {
		return directionInBeginTime;
	}

	/**
	 * @return  the directionInEndTime
	 * @uml.property  name="directionInEndTime"
	 */
	public int getDirectionInEndTime() {
		return directionInEndTime;
	}

	/**
	 * @return  the directionInType
	 * @uml.property  name="directionInType"
	 */
	public DirectionInTimeType getDirectionInType() {
		return DirectionInType;
	}

	/**
	 * @return  the directionOutBeginTime
	 * @uml.property  name="directionOutBeginTime"
	 */
	public int getDirectionOutBeginTime() {
		return directionOutBeginTime;
	}

	/**
	 * @return  the directionOutEndTime
	 * @uml.property  name="directionOutEndTime"
	 */
	public int getDirectionOutEndTime() {
		return directionOutEndTime;
	}

	/**
	 * @return  the numberOfVehicles
	 * @uml.property  name="numberOfVehicles"
	 */
	public int getNumberOfVehicles() {
		return numberOfVehicles;
	}

	@Override
	public String getToolTip() {
		String tooltip = "<html>" + "<b>HotSpot</b> " + name + "<br>" + "<b>Number of vehicles: </b> " + String.valueOf(numberOfVehicles) + "<br>" + "<b>Direction: </b>";

		if (DirectionIn) {
			tooltip += "Incoming ";
		}

		if (DirectionOut) {
			tooltip += "Outgoing ";
		}

		return tooltip;
	}

	/**
	 * @return  the vehicleSelection
	 * @uml.property  name="vehicleSelection"
	 */
	public TypeSelection<VehicleType> getVehicleSelection() {
		return vehicleSelection;
	}

	public void handleLocationChanged(Handle h) {
		if (h.getName().equals("center")) {
			center = new Point2DExt(h.getLocation());

			handles.get("XRadius").setUnrestrictedLocation(new Point2D.Double(center.x + radiusx, center.y));
			handles.get("YRadius").setUnrestrictedLocation(new Point2D.Double(center.x, center.y + radiusy));
		} else if (h.getName().equals("XRadius")) {
			radiusx = h.getLocation().x - center.x;
		} else if (h.getName().equals("YRadius")) {
			radiusy = h.getLocation().y - center.y;
		}

		area.setFrameFromCenter(center, new Point2D.Double(center.x + radiusx, center.y + radiusy));
	}

	@Override
	public boolean Hit(Point p) {

		if (area.getBounds2D().contains(p.x, p.y)) {
			if (area.contains(p.x, p.y)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean Hit(Rectangle transformedRectangle) {
		return area.intersects(transformedRectangle);
	}

	/**
	 * @return  the directionIn
	 * @uml.property  name="directionIn"
	 */
	public boolean isDirectionIn() {
		return DirectionIn;
	}

	/**
	 * @return  the directionOut
	 * @uml.property  name="directionOut"
	 */
	public boolean isDirectionOut() {
		return DirectionOut;
	}

	/**
	 * @param color  the color to set
	 * @uml.property  name="color"
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @param directionIn  the directionIn to set
	 * @uml.property  name="directionIn"
	 */
	public void setDirectionIn(boolean directionIn) {
		DirectionIn = directionIn;
	}

	/**
	 * @param directionInBeginTime  the directionInBeginTime to set
	 * @uml.property  name="directionInBeginTime"
	 */
	public void setDirectionInBeginTime(int directionInBeginTime) {
		this.directionInBeginTime = directionInBeginTime;
	}

	/**
	 * @param directionInEndTime  the directionInEndTime to set
	 * @uml.property  name="directionInEndTime"
	 */
	public void setDirectionInEndTime(int directionInEndTime) {
		this.directionInEndTime = directionInEndTime;
	}

	/**
	 * @param directionInType  the directionInType to set
	 * @uml.property  name="directionInType"
	 */
	public void setDirectionInType(DirectionInTimeType directionInType) {
		DirectionInType = directionInType;
	}

	/**
	 * @param directionOut  the directionOut to set
	 * @uml.property  name="directionOut"
	 */
	public void setDirectionOut(boolean directionOut) {
		DirectionOut = directionOut;
	}

	/**
	 * @param directionOutBeginTime  the directionOutBeginTime to set
	 * @uml.property  name="directionOutBeginTime"
	 */
	public void setDirectionOutBeginTime(int directionOutBeginTime) {
		this.directionOutBeginTime = directionOutBeginTime;
	}

	/**
	 * @param directionOutEndTime  the directionOutEndTime to set
	 * @uml.property  name="directionOutEndTime"
	 */
	public void setDirectionOutEndTime(int directionOutEndTime) {
		this.directionOutEndTime = directionOutEndTime;
	}

	/**
	 * @param numberOfVehicles  the numberOfVehicles to set
	 * @uml.property  name="numberOfVehicles"
	 */
	public void setNumberOfVehicles(int numberOfVehicles) {
		this.numberOfVehicles = numberOfVehicles;
	}

	public void setRadius(Point2D.Double p) {
		radiusx = p.x - center.x;
		radiusy = p.y - center.y;

		area.setFrameFromCenter(center, new Point2D.Double(center.x + radiusx, center.y + radiusy));
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
		return new HotSpot(this);
	}

	@Override
	public String toXML() {
		StringBuilder sb = new StringBuilder();

		sb.append("<hotspot name=\""+name+"\" incoming=\""+String.valueOf(DirectionIn)+"\" incomingTimeType=\""+this.DirectionInType.name()+"\" incomingStartingTime=\""+String.valueOf(directionInBeginTime)+"\" incomingEndingTime=\""+String.valueOf(directionInEndTime)+"\" outgoing=\""+String.valueOf(DirectionOut) +"\" outgoingStartingTime=\""+String.valueOf(directionOutBeginTime)+"\" outgoingEndingTime=\""+String.valueOf(directionOutEndTime)+"\" numberOfVehicles=\""+String.valueOf(numberOfVehicles)+"\">\n");

		sb.append(XMLHelpers.vehicleSelectionToXML("vehicleSelection",vehicleSelection));
		
		sb.append("<areaShape>");
		
		sb.append(XMLHelpers.shapeToXML(area));
		
		sb.append("</areaShape>\n");

		sb.append("</hotspot>\n");
		
		return sb.toString();
	}

}
