package trafficdefinition;

import enums.MotionRestriction;
import enums.SchoolType;
import helpers.TimeConverter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import roadnetwork.Edge;
import shapes.Point2DExt;
import shapes.Rectangle2DExt;

/**
 * Class representing a school. This is used when creating traffic based on demographic data.
 */
public class School extends TrafficDefinitionElement implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The closest edge to the school
	 */
	private Edge closestEdge = null;

	/**
	 * The school's location
	 */
	private Point2DExt location;

	/**
	 * The school's type
	 */
	private SchoolType schoolType;

	/**
	 * The shape used to draw the school
	 */
	private Rectangle2DExt shape;

	/**
	 * The time the school is closing
	 */
	private int timeClosing;

	/**
	 * The time the school is opening
	 */
	private int timeOpening;

	/**
	 * The total number of children that the school is able to accomodate
	 */
	private int capacity;
	
	/**
	 * The available capacity of the school (used during schoool assignemnt)
	 */
	private int availableCapacity;
	
	/**
	 * Default constructor.
	 * 
	 * @param location
	 *            the school's location
	 */
	public School(Point2D.Double location) {
		super();

		name = "School " + String.valueOf(increment++);

		// Create the school's shape
		shape = new Rectangle2DExt();
		shape.setFrameFromCenter(location.x, location.y, location.x + 15, location.y + 15);

		this.location = new Point2DExt(location);

		// Create the handle
		List<Handle> handles = new ArrayList<Handle>();
		handles.add(new Handle(location, "location", MotionRestriction.None));

		addHandles(handles);

		// Set default values to school times
		timeOpening = TimeConverter.toSeconds(7, 30);
		timeClosing = TimeConverter.toSeconds(13, 30);

		schoolType = SchoolType.Kindergarden;
		capacity = 800;
		
		// Set the default value to true to export the school shape to SUMO
		exportPolygon = true;
	}

	/**
	 * Class constructor that creates a new school as a copy of the specified
	 * school
	 * 
	 * @param source
	 */
	public School(School source) {
		super(source);

		closestEdge = source.closestEdge;

		location = new Point2DExt(source.location);
		shape = new Rectangle2DExt(source.shape);

		schoolType = source.schoolType;
		capacity = source.capacity;
		timeClosing = source.timeClosing;
		timeOpening = source.timeOpening;
	}

	@Override
	public void Draw(Graphics2D g, double zoomFactor, boolean isDeleted, boolean isSelected) {
		// Save the previous color to restore it later
		Color prev = g.getColor();

		if (!isSelected) {
			g.setColor(Color.ORANGE);
		}

		if (isSelected) {
			g.setColor(Color.GREEN);
		}

		if (shape != null) {
			g.fill(shape);
		}

		super.Draw(g, zoomFactor, isDeleted, isSelected);

		g.setColor(prev);
	}

	public Rectangle2DExt getBounds() {
		return shape;
	}

	/**
	 * Returns the closest edge to the school
	 * @return
	 * @uml.property  name="closestEdge"
	 */
	public Edge getClosestEdge() {
		return closestEdge;
	}

	/**
	 * Returns the school's location
	 * @return
	 * @uml.property  name="location"
	 */
	public Point2D.Double getLocation() {
		return location;
	}

	/**
	 * Returns how much time (in seconds) a child can wait after school to be
	 * picked up by its parent
	 * 
	 * @return
	 */
	public int amountOfTimeChildCanWaitAfterSchool() {
		int result = 0;

		switch (schoolType) {
		case Kindergarden:
			result = 0;
			break;
		case Elementary:
			result = 10 * 60;
			break;
		case Middle:
			result = 20 * 60;
			break;
		case High:
			result = 30 * 60;
			break;
		}

		return result;
	}

	/**
	 * Returns the priority that the school will have when assigning children to
	 * adults to take them to school
	 * 
	 * @return
	 */
	public int getPriority() {
		int result = 0;

		switch (schoolType) {
		case Kindergarden:
			result = 4;
			break;
		case Elementary:
			result = 3;
			break;
		case Middle:
			result = 2;
			break;
		case High:
			result = 1;
			break;
		}

		return result;
	}
	
	/**
	 * Helper class used for sorting a list of schools based on their priority
	 * 
	 */
	public static class SchoolPriorityComparator implements Comparator<School>{
		public int compare(School school1, School school2) {
			return school1.getPriority()-school2.getPriority();
		}	
	}

	/**
	 * @return  the schoolType
	 * @uml.property  name="schoolType"
	 */
	public SchoolType getSchoolType() {
		return schoolType;
	}

	/**
	 * @return  the capacity
	 * @uml.property  name="capacity"
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity  the capacity to set
	 * @uml.property  name="capacity"
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return  the availableCapacity
	 * @uml.property  name="availableCapacity"
	 */
	public int getAvailableCapacity() {
		return availableCapacity;
	}

	/**
	 * @param availableCapacity  the availableCapacity to set
	 * @uml.property  name="availableCapacity"
	 */
	public void setAvailableCapacity(int availableCapacity) {
		this.availableCapacity = availableCapacity;
	}

	/**
	 * @return  the timeClosing
	 * @uml.property  name="timeClosing"
	 */
	public int getTimeClosing() {
		return timeClosing;
	}

	/**
	 * @return  the timeOpening
	 * @uml.property  name="timeOpening"
	 */
	public int getTimeOpening() {
		return timeOpening;
	}

	@Override
	public String getToolTip() {
		return "<html>" + "<b>School</b> " + name + "<br>" + "<b>Type:</b> " + schoolType.name() + "<br>" + "<b>Time opening:</b> " + TimeConverter.toString(timeOpening) + "<br>" + "<b>Time closing:</b> " + TimeConverter.toString(timeClosing) + "<br>" + "<b>Capacity:</b> " + String.valueOf(capacity) + "</html>";
	}

	public void handleLocationChanged(Handle h) {
		if (h.getName().equals("location")) {
			location = new Point2DExt(h.getLocation());

			shape.setFrameFromCenter(location.x, location.y, location.x + 15, location.y + 15);
		}
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
	 * @param closestEdge  the closestEdge to set
	 * @uml.property  name="closestEdge"
	 */
	public void setClosestEdge(Edge closestEdge) {
		this.closestEdge = closestEdge;
	}

	/**
	 * @param schoolType  the schoolType to set
	 * @uml.property  name="schoolType"
	 */
	public void setSchoolType(SchoolType schoolType) {
		this.schoolType = schoolType;
	}

	/**
	 * @param timeClosing  the timeClosing to set
	 * @uml.property  name="timeClosing"
	 */
	public void setTimeClosing(int timeClosing) {
		this.timeClosing = timeClosing;
	}

	/**
	 * @param timeOpening  the timeOpening to set
	 * @uml.property  name="timeOpening"
	 */
	public void setTimeOpening(int timeOpening) {
		this.timeOpening = timeOpening;
	}

	@Override
	public TrafficDefinitionElement elementClone() {
		return new School(this);
	}


	/* (non-Javadoc)
	 * @see trafficdefinition.TrafficDefinitionElement#getGeometryXML()
	 */
	@Override
	public String getGeometryXML() {
		StringBuilder polyBuilder = new StringBuilder();
		
		polyBuilder.append("<poly id=\"" + schoolType.name() + " School " + name + "\" type=\"School\" color=\"1,0,0\" fill=\"true\" layer=\"-1\">");
		
		polyBuilder.append(String.valueOf(shape.getMinX()) + "," + String.valueOf(shape.getMinY()));
		polyBuilder.append(" " + String.valueOf(shape.getMinX()) + "," + String.valueOf(shape.getMaxY()));
		polyBuilder.append(" " + String.valueOf(shape.getMaxX()) + "," + String.valueOf(shape.getMaxY()));
		polyBuilder.append(" " + String.valueOf(shape.getMaxX()) + "," + String.valueOf(shape.getMinY()));
		
		polyBuilder.append("</poly>\n");
		
		return polyBuilder.toString();
	}
	
	@Override
	public String toXML() {
		StringBuilder sb=new StringBuilder();
		
		sb.append("<school name=\""+name+"\" openingTime=\""+String.valueOf(timeOpening)+"\" closingTime=\""+String.valueOf(timeClosing)+"\" level=\""+schoolType.name()+"\" capacity=\"" + String.valueOf(capacity) + "\" location=\""+String.valueOf(location.x)+","+String.valueOf(location.y)+"\" />\n");
		
		return sb.toString();
	}

}
