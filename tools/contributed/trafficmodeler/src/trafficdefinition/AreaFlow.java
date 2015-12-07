package trafficdefinition;

import classes.Pair;
import classes.Project;
import classes.TypeSelection;
import enums.MotionRestriction;
import helpers.DrawingHelper;
import helpers.TimeConverter;
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
import shapes.Line2DExt;
import shapes.Point2DExt;
import shapes.Rectangle2DExt;
import simulation.VehicleType;

/**
 * Class representing a flow of vehicles from one area to another. The area flow is drawn on screen using an ellipsoid for the starting and ending area, connected with an arrow. Vehicles will be emitted at random points in time between beginTime and endTime.
 */
public class AreaFlow extends TrafficDefinitionElement implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Used during the graphical creation of the area flow.
	 */
	public int constructionPhase = 0;

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
	 * The area flow's color. All created vehicles and routes will have this
	 * color.
	 */
	private Color color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());

	/**
	 * Arrow used for drawing the direction of flow.
	 */
	private Line2DExt arrow;

	/**
	 * The ellipsoid defining the area from which vehicles will be leaving
	 */
	private Ellipse2DExt startArea;

	/**
	 * The ellipsoid defining the area to which vehicles will be arriving
	 */
	private Ellipse2DExt endArea;

	/**
	 * The center of the source area.
	 */
	private Point2DExt startAreaCenter;

	/**
	 * The horizontal radius of the source area.
	 */
	private double startAreaRadiusX;

	/**
	 * The vertical radius of the source area.
	 */
	private double startAreaRadiusY;

	/**
	 * The center of the destination area
	 */
	private Point2DExt endAreaCenter;

	/**
	 * The horizontal radius of the destination area.
	 */
	private double endAreaRadiusX;

	/**
	 * The vertical radius of the destination area.
	 */
	private double endAreaRadiusY;

	/**
	 * The selection of the vehicle types that will be created for this area flow.
	 * @uml.property  name="vehicleSelection"
	 * @uml.associationEnd  
	 */
	private TypeSelection<VehicleType> vehicleSelection;

	/**
	 * Default constructor. Assigns a random name to the area flow and selects
	 * the default vehicle type.
	 * 
	 */
	public AreaFlow() {
		super();
		
		name = "Area Flow " + String.valueOf(increment++);

		startArea = new Ellipse2DExt();
		endArea = new Ellipse2DExt();
		arrow = new Line2DExt();

		// Assign a possibility of 1 to the default vehicle type
		vehicleSelection = new TypeSelection<VehicleType>();
		vehicleSelection.add(new Pair<VehicleType, Float>(Project.getCurrentlyLoadedProject().getDefaultVehicleType(), 1.f));
	}

	/**
	 * Class constructor that creates a new area flow as a copy of the specified
	 * area flow
	 * 
	 * @param source
	 */
	private AreaFlow(AreaFlow source) {
		super(source);

		constructionPhase = source.constructionPhase;
		beginTime = source.beginTime;
		endTime = source.endTime;
		numberOfVehicles = source.numberOfVehicles;
		color = new Color(source.color.getRGB());
		
		startArea = new Ellipse2DExt(source.startArea);
		startAreaCenter = new Point2DExt(source.startAreaCenter);
		startAreaRadiusX = source.startAreaRadiusX;
		startAreaRadiusY =  source.startAreaRadiusY;
		
		endArea = new Ellipse2DExt(source.endArea);
		endAreaCenter = new Point2DExt(source.endAreaCenter);
		endAreaRadiusX = source.endAreaRadiusX;
		endAreaRadiusY = source.endAreaRadiusY;
		
		arrow = new Line2DExt(source.arrow);

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

		// Draw the source area
		if (startArea != null) {
			g.fill(startArea);
		}

		// Draw the destination area
		if (endArea != null) {
			g.fill(endArea);
		}

		// Draw the direction arrow
		if (arrow != null) {
			g.setColor(Color.BLACK);

			DrawingHelper.drawArrowHead(g, arrow.x1, arrow.y1, arrow.x2, arrow.y2, 1.f);

			// TODO draw with thicker line
			g.draw(arrow);
		}

		// Draw handles
		super.Draw(g, zoomFactor, isDeleted, isSelected);

		g.setColor(prev);
	}

	/**
	 * Creates the handles that enable UI manipulation of the area flow
	 */
	public void createHandles() {
		List<Handle> handles = new ArrayList<Handle>();

		Handle startAreaCenterHandle = new Handle(startAreaCenter, "StartAreaCenter", MotionRestriction.None);
		Handle startAreaXRadiusHandle = new Handle(new Point2D.Double(startAreaCenter.x + startAreaRadiusX, startAreaCenter.y), "StartAreaRadiusX", MotionRestriction.Horizontal);
		Handle startAreaYRadiusHandle = new Handle(new Point2D.Double(startAreaCenter.x, startAreaCenter.y + startAreaRadiusY), "StartAreaRadiusY", MotionRestriction.Vertical);
		Handle endAreaCenterHandle = new Handle(endAreaCenter, "EndAreaCenter", MotionRestriction.None);
		Handle endAreaXRadiusHandle = new Handle(new Point2D.Double(endAreaCenter.x + endAreaRadiusX, endAreaCenter.y), "EndAreaRadiusX", MotionRestriction.Horizontal);
		Handle endAreaYRadiusHandle = new Handle(new Point2D.Double(endAreaCenter.x, endAreaCenter.y + endAreaRadiusY), "EndAreaRadiusY", MotionRestriction.Vertical);

		handles.add(startAreaCenterHandle);
		handles.add(startAreaXRadiusHandle);
		handles.add(startAreaYRadiusHandle);
		handles.add(endAreaCenterHandle);
		handles.add(endAreaXRadiusHandle);
		handles.add(endAreaYRadiusHandle);

		// Assign motion listeners to the handles
		super.addHandles(handles);
	}

	public Rectangle2DExt getBounds() {
		Rectangle2D bounds = startArea.getBounds2D();
		
		bounds.add(endArea.getBounds2D());
		
		return new Rectangle2DExt(bounds.getCenterX(),bounds.getCenterY(),bounds.getWidth(),bounds.getHeight());
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
	 * @return  the endArea
	 * @uml.property  name="endArea"
	 */
	public Ellipse2DExt getEndArea() {
		return endArea;
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
	 * @return  the startArea
	 * @uml.property  name="startArea"
	 */
	public Ellipse2DExt getStartArea() {
		return startArea;
	}

	@Override
	public String getToolTip() {
		return "<html>" + "<b>Area flow</b> " + name + "<br>" + "<b>Number of vehicles:</b> " + String.valueOf(numberOfVehicles) + "<br>" + "<b>Start time:</b> " + TimeConverter.toString(beginTime) + "<br>" + "<b>End time:</b> " + TimeConverter.toString(endTime) + "</html>";
	}

	/**
	 * @return  the vehicleSelection
	 * @uml.property  name="vehicleSelection"
	 */
	public TypeSelection<VehicleType> getVehicleSelection() {
		return vehicleSelection;
	}

	public void handleLocationChanged(Handle h) {
		if (h.getName().equals("StartAreaCenter")) {
			startAreaCenter = new Point2DExt(h.getLocation());

			// Move the other handles with it
			handles.get("StartAreaRadiusX").setUnrestrictedLocation(new Point2D.Double(startAreaCenter.x + startAreaRadiusX, startAreaCenter.y));
			handles.get("StartAreaRadiusY").setUnrestrictedLocation(new Point2D.Double(startAreaCenter.x, startAreaCenter.y + startAreaRadiusY));
		} else if (h.getName().equals("StartAreaRadiusX")) {
			startAreaRadiusX = h.getLocation().x - startAreaCenter.x;
		} else if (h.getName().equals("StartAreaRadiusY")) {
			startAreaRadiusY = h.getLocation().y - startAreaCenter.y;
		} else if (h.getName().equals("EndAreaCenter")) {
			endAreaCenter = new Point2DExt(h.getLocation());

			// Move the other handles with it
			handles.get("EndAreaRadiusX").setUnrestrictedLocation(new Point2D.Double(endAreaCenter.x + endAreaRadiusX, endAreaCenter.y));
			handles.get("EndAreaRadiusY").setUnrestrictedLocation(new Point2D.Double(endAreaCenter.x, endAreaCenter.y + endAreaRadiusY));
		} else if (h.getName().equals("EndAreaRadiusX")) {
			endAreaRadiusX = h.getLocation().x - endAreaCenter.x;
		} else if (h.getName().equals("EndAreaRadiusY")) {
			endAreaRadiusY = h.getLocation().y - endAreaCenter.y;
		}

		// Recreate the shapes
		startArea.setFrameFromCenter(startAreaCenter, new Point2D.Double(startAreaCenter.x + startAreaRadiusX, startAreaCenter.y + startAreaRadiusY));
		endArea.setFrameFromCenter(endAreaCenter, new Point2D.Double(endAreaCenter.x + endAreaRadiusX, endAreaCenter.y + endAreaRadiusY));
		arrow.setLine(startAreaCenter, endAreaCenter);
	}

	@Override
	public boolean Hit(Point p) {

		if (arrow.getBounds2D().contains(p.x, p.y)) {
			if (arrow.contains(p.x, p.y)) {
				return true;
			}
		}

		if (startArea.getBounds2D().contains(p.x, p.y)) {
			if (startArea.contains(p.x, p.y)) {
				return true;
			}
		}

		if (endArea.getBounds2D().contains(p.x, p.y)) {
			if (endArea.contains(p.x, p.y)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean Hit(Rectangle transformedRectangle) {
		return (arrow.intersects(transformedRectangle) || startArea.intersects(transformedRectangle) || endArea.intersects(transformedRectangle));
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

	public void setEndAreaCenter(Point2D.Double p) {
		endAreaCenter = new Point2DExt(p.x, p.y);

		arrow.setLine(startAreaCenter, endAreaCenter);
	}

	public void setEndAreaRadius(Point2D.Double p) {
		endAreaRadiusX = p.x - endAreaCenter.x;
		endAreaRadiusY = p.y - endAreaCenter.y;

		endArea.setFrameFromCenter(endAreaCenter, new Point2D.Double(endAreaCenter.x + endAreaRadiusX, endAreaCenter.y + endAreaRadiusY));
	}

	/**
	 * @param endTime  the endTime to set
	 * @uml.property  name="endTime"
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	/**
	 * @param numberOfVehicles  the numberOfVehicles to set
	 * @uml.property  name="numberOfVehicles"
	 */
	public void setNumberOfVehicles(int numberOfVehicles) {
		this.numberOfVehicles = numberOfVehicles;
	}

	public void setStartAreaCenter(Point2D.Double p) {
		startAreaCenter = new Point2DExt(p.x, p.y);
	}

	public void setStartAreaRadius(Point2D.Double p) {
		startAreaRadiusX = p.x - startAreaCenter.x;
		startAreaRadiusY = p.y - startAreaCenter.y;

		startArea.setFrameFromCenter(startAreaCenter, new Point2D.Double(startAreaCenter.x + startAreaRadiusX, startAreaCenter.y + startAreaRadiusY));
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
		return new AreaFlow(this);
	}

	@Override
	public String toXML() {
		StringBuilder sb=new StringBuilder();
		
		sb.append("<areaFlow name=\""+name+"\" numberOfVehicles=\""+String.valueOf(numberOfVehicles)+"\" startingTime=\""+String.valueOf(beginTime)+"\" endingTime=\""+String.valueOf(endTime)+"\">\n");
		sb.append(tdl.XMLHelpers.vehicleSelectionToXML("vehicleSelection", vehicleSelection));

		sb.append("<sourceAreaShape>");
		sb.append(tdl.XMLHelpers.shapeToXML(startArea));
		sb.append("</sourceAreaShape>\n");
		
		sb.append("<destinationAreaShape>");
		sb.append(tdl.XMLHelpers.shapeToXML(endArea));
		sb.append("</destinationAreaShape>\n");

		sb.append("</areaFlow>\n");

		return sb.toString();
	}
}
