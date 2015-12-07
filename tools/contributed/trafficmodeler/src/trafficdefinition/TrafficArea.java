package trafficdefinition;

import classes.Pair;
import classes.Project;
import classes.TypeSelection;
import enums.MotionRestriction;
import helpers.RandomNumberProvider;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import shapes.AreaExt;
import shapes.Rectangle2DExt;
import simulation.JobType;
import tdl.XMLHelpers;

/**
 * @author  PapaleonLe01
 */
public class TrafficArea extends TrafficDefinitionElement implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The area's color.
	 */
	private Color color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());

	/**
	 * The area's population
	 */
	private int population = 0;

	/**
	 * The jobs of the people living in this area
	 * @uml.property  name="residentsJobSelection"
	 * @uml.associationEnd  
	 */
	private TypeSelection<JobType> residentsJobSelection;

	/**
	 * The shape defining the area
	 */
	private AreaExt shape;

	/**
	 * The jobs of the people working in this area
	 * @uml.property  name="workersJobSelection"
	 * @uml.associationEnd  
	 */
	private TypeSelection<JobType> workersJobSelection;

	/**
	 * The total number of work positions
	 */
	private int workPositions = 0;

	/**
	 * The average adults per house
	 */
	float averageAdultsPerHouse = 3f;

	/**
	 * The average children per house
	 */
	float averageChildrenPerHouse = 2f;

	/**
	 * The possibility that an adult is has a job
	 */
	float possibilityAdultHasJob = 0.98f;

	/**
	 * The possibility that an adult is a driver
	 */
	float possibilityAdultIsDriver = 0.8f;

	/**
	 * The possibility that a driver has a car
	 */
	float possibilityDriverHasCar = 0.8f;

	/**
	 * Class constructor.
	 * 
	 * @param area
	 */
	public TrafficArea(Shape area) {
		super();

		name = "Area " + String.valueOf(increment++);
		shape = new AreaExt(area);

		List<Handle> handles = new ArrayList<Handle>();
		handles.add(new Handle(new Point2D.Double(area.getBounds2D().getCenterX(), area.getBounds2D().getCenterY()), "location", MotionRestriction.None));

		addHandles(handles);

		// Add the default job type to the area's residents job selection
		residentsJobSelection = new TypeSelection<JobType>();
		residentsJobSelection.add(new Pair<JobType, Float>(Project.getCurrentlyLoadedProject().getDefaultJobType(), 1.f));

		// Add the default job type to the area's worker job selection
		workersJobSelection = new TypeSelection<JobType>();
		workersJobSelection.add(new Pair<JobType, Float>(Project.getCurrentlyLoadedProject().getDefaultJobType(), 1.f));
		
		// Set the default value to true to export the area shape to SUMO
		exportPolygon = true;
	}

	/**
	 * Class constructor that creates a new traffic area as a copy of the
	 * specified traffic area
	 * 
	 * @param source
	 */
	public TrafficArea(TrafficArea source) {
		super(source);

		color = new Color(source.color.getRGB());

		population = source.population;
		workPositions = source.workPositions;

		shape = new AreaExt(source.shape);
	}

	public Rectangle2DExt getBounds() {
		return new Rectangle2DExt((Rectangle2D.Double)shape.getBounds2D());
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

		if (shape != null) {
			g.fill(shape);
		}

		super.Draw(g, zoomFactor, isDeleted, isSelected);

		g.setColor(prev);

	}

	@Override
	public TrafficDefinitionElement elementClone() {
		return new TrafficArea(this);
	}

	/**
	 * @return  the averageAdultsPerHouse
	 * @uml.property  name="averageAdultsPerHouse"
	 */
	public float getAverageAdultsPerHouse() {
		return averageAdultsPerHouse;
	}

	/**
	 * @return  the averageChildrenPerHouse
	 * @uml.property  name="averageChildrenPerHouse"
	 */
	public float getAverageChildrenPerHouse() {
		return averageChildrenPerHouse;
	}

	/**
	 * @return  the color
	 * @uml.property  name="color"
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return  the population
	 * @uml.property  name="population"
	 */
	public int getPopulation() {
		return population;
	}

	/**
	 * @return  the possibilityAdultHasJob
	 * @uml.property  name="possibilityAdultHasJob"
	 */
	public float getPossibilityAdultHasJob() {
		return possibilityAdultHasJob;
	}

	/**
	 * @return  the possibilityAdultIsDriver
	 * @uml.property  name="possibilityAdultIsDriver"
	 */
	public float getPossibilityAdultIsDriver() {
		return possibilityAdultIsDriver;
	}

	/**
	 * @return  the possibilityDriverHasCar
	 * @uml.property  name="possibilityDriverHasCar"
	 */
	public float getPossibilityDriverHasCar() {
		return possibilityDriverHasCar;
	}

	/**
	 * Returns a random point that is located inside the area
	 * 
	 * @return
	 */
	public Point2D.Double getRandomPointInArea() {
		Point2D.Double result = null;
		Random r = RandomNumberProvider.getRandom();

		do {
			// Get the area's bounds
			double areaWidth = shape.getBounds2D().getMaxX() - shape.getBounds2D().getMinX();
			double areaHeight = shape.getBounds2D().getMaxY() - shape.getBounds2D().getMinY();

			// Get a random point inside the traffic area's bounds
			double x = r.nextDouble() * areaWidth + shape.getBounds2D().getMinX();
			double y = r.nextDouble() * areaHeight + shape.getBounds2D().getMinY();

			result = new Point2D.Double(x, y);

			// If the point lies within the area
			if (shape.contains(result)) {
				return result;
			}
		} while (true);
	}

	/**
	 * @return  the residentsJobSelection
	 * @uml.property  name="residentsJobSelection"
	 */
	public TypeSelection<JobType> getResidentsJobSelection() {
		return residentsJobSelection;
	}

	/**
	 * @return  the shape
	 * @uml.property  name="shape"
	 */
	public AreaExt getShape() {
		return shape;
	}

	@Override
	public String getToolTip() {
		return "<html>" + "<b>Area: </b> " + name + "<br>" + "<b>Population:</b> " + population + "<br>" + "<b>Work positions:</b> " + workPositions + "</html>";
	}

	/**
	 * @return  the workersJobSelection
	 * @uml.property  name="workersJobSelection"
	 */
	public TypeSelection<JobType> getWorkersJobSelection() {
		return workersJobSelection;
	}

	/**
	 * @return  the workPositions
	 * @uml.property  name="workPositions"
	 */
	public int getWorkPositions() {
		return workPositions;
	}

	public void handleLocationChanged(Handle h) {
		if (h.getName().equals("location")) {
			Point2D.Double previousCenter = new Point2D.Double(shape.getBounds2D().getCenterX(), shape.getBounds2D().getCenterY());
			double x = h.getLocation().x - previousCenter.x;
			double y = h.getLocation().y - previousCenter.y;

			AffineTransform tx = new AffineTransform();
			tx.translate(x, y);

			shape.transform(tx);
		}
	}

	@Override
	public boolean Hit(Point p) {
		return shape.contains(p);
	}

	@Override
	public boolean Hit(Rectangle r) {
		return shape.intersects(r);
	}

	/**
	 * @param averageAdultsPerHouse  the averageAdultsPerHouse to set
	 * @uml.property  name="averageAdultsPerHouse"
	 */
	public void setAverageAdultsPerHouse(float averageAdultsPerHouse) {
		this.averageAdultsPerHouse = averageAdultsPerHouse;
	}

	/**
	 * @param averageChildrenPerHouse  the averageChildrenPerHouse to set
	 * @uml.property  name="averageChildrenPerHouse"
	 */
	public void setAverageChildrenPerHouse(float averageChildrenPerHouse) {
		this.averageChildrenPerHouse = averageChildrenPerHouse;
	}

	/**
	 * @param color  the color to set
	 * @uml.property  name="color"
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @param population  the population to set
	 * @uml.property  name="population"
	 */
	public void setPopulation(int population) {
		this.population = population;
	}

	/**
	 * @param possibilityAdultHasJob  the possibilityAdultHasJob to set
	 * @uml.property  name="possibilityAdultHasJob"
	 */
	public void setPossibilityAdultHasJob(float possibilityAdultHasJob) {
		this.possibilityAdultHasJob = possibilityAdultHasJob;
	}

	/**
	 * @param possibilityAdultIsDriver  the possibilityAdultIsDriver to set
	 * @uml.property  name="possibilityAdultIsDriver"
	 */
	public void setPossibilityAdultIsDriver(float possibilityAdultIsDriver) {
		this.possibilityAdultIsDriver = possibilityAdultIsDriver;
	}

	/**
	 * @param possibilityDriverHasCar  the possibilityDriverHasCar to set
	 * @uml.property  name="possibilityDriverHasCar"
	 */
	public void setPossibilityDriverHasCar(float possibilityDriverHasCar) {
		this.possibilityDriverHasCar = possibilityDriverHasCar;
	}

	/**
	 * @param residentsJobSelection  the residentsJobSelection to set
	 * @uml.property  name="residentsJobSelection"
	 */
	public void setResidentsJobSelection(TypeSelection<JobType> residentsJobSelection) {
		this.residentsJobSelection = residentsJobSelection;
	}

	/**
	 * @param workersJobSelection  the workersJobSelection to set
	 * @uml.property  name="workersJobSelection"
	 */
	public void setWorkersJobSelection(TypeSelection<JobType> workersJobSelection) {
		this.workersJobSelection = workersJobSelection;
	}

	/**
	 * @param workPositions  the workPositions to set
	 * @uml.property  name="workPositions"
	 */
	public void setWorkPositions(int workPositions) {
		this.workPositions = workPositions;
	}

	/* (non-Javadoc)
	 * @see trafficdefinition.TrafficDefinitionElement#getGeometryXML()
	 */
	@Override
	public String getGeometryXML() {
		StringBuilder polyBuilder = new StringBuilder();
		
		String colorString = new Float(color.getRed() / 255f).toString() + "," + new Float(color.getGreen() / 255f).toString() + "," + new Float(color.getBlue() / 255f).toString();

		polyBuilder.append("<poly id=\"" + "Traffic Area " + name + "\" type=\"Traffic Area\" color=\""+colorString+"\" fill=\"true\" layer=\"-3\">");
		
		polyBuilder.append(XMLHelpers.shapeToXML(shape));
		
		polyBuilder.append("</poly>\n");
		
		return polyBuilder.toString();
	}
	
	@Override
	public String toXML() {
		StringBuilder sb=new StringBuilder();
		
		sb.append("<trafficArea name=\""+name+"\" possibilityAdultIsDriver=\""+String.valueOf(possibilityAdultIsDriver)+"\" possibilityDriverHasCar=\""+String.valueOf(possibilityDriverHasCar)+"\" workingPositions=\""+String.valueOf(workPositions)+"\" possibilityAdultHasJob=\""+String.valueOf(possibilityAdultHasJob)+"\" averageChildrenPerHouse=\""+String.valueOf(averageChildrenPerHouse)+"\" population=\""+String.valueOf(population)+"\" averageAdultsPerHouse=\""+String.valueOf(averageAdultsPerHouse)+"\">\n");
		
		sb.append(tdl.XMLHelpers.jobSelectionToXML("residentsJobSelection", residentsJobSelection));
		sb.append(tdl.XMLHelpers.jobSelectionToXML("workersJobSelection", workersJobSelection));
					
		sb.append("<shape>");
		sb.append(tdl.XMLHelpers.shapeToXML(shape));
		sb.append("</shape>\n");
		
		sb.append("</trafficArea>\n");
		
		return sb.toString();
	}
}
