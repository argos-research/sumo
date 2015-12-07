package trafficdefinition;

import classes.Pair;
import classes.Project;
import classes.TypeSelection;
import simulation.VehicleType;
import tdl.XMLHelpers;

/**
 * @author  PapaleonLe01
 */
public class RandomTrafficDefinitionLayer extends TrafficDefinitionLayer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int startingTime = 0;
	private int endingTime = 0;
	
	/**
	 * The number of vehicles per second that will be emitted
	 */
	private int vehiclesPerSecond = 0;
	
	/**
	 * The vehicle selection for the layer
	 * @uml.property  name="vehicleSelection"
	 * @uml.associationEnd  
	 */
	private TypeSelection<VehicleType> vehicleSelection;

	/**
	 * Class constructor used to create a new layer as a copy of another layer
	 * @param source the layer that will be used as the source layer
	 */
	public RandomTrafficDefinitionLayer() {
		super();
		
		//Add the default vehicle to the layer's vehicle selection
		vehicleSelection = new TypeSelection<VehicleType>();
		vehicleSelection.add(new Pair<VehicleType, Float>(Project.getCurrentlyLoadedProject().getDefaultVehicleType(),1.f));

	}

	/**
	 * Class constructor used to create a new layer.
	 *
	 */
	public RandomTrafficDefinitionLayer(RandomTrafficDefinitionLayer source) {
		super(source);
	}

	@Override
	public TrafficDefinitionLayer duplicate() {
		return new RandomTrafficDefinitionLayer(this);
	}

	/* (non-Javadoc)
	 * @see trafficdefinition.TrafficDefinitionLayer#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "(Random layer)";
	}

	/**
	 * @return  the endingTime
	 * @uml.property  name="endingTime"
	 */
	public int getEndingTime() {
		return endingTime;
	}

	/**
	 * @param endingTime  the endingTime to set
	 * @uml.property  name="endingTime"
	 */
	public void setEndingTime(int endingTime) {
		this.endingTime = endingTime;
	}
	

	/**
	 * @return  the startingTime
	 * @uml.property  name="startingTime"
	 */
	public int getStartingTime() {
		return startingTime;
	}
	

	/**
	 * @param startingTime  the startingTime to set
	 * @uml.property  name="startingTime"
	 */
	public void setStartingTime(int startingTime) {
		this.startingTime = startingTime;
	}
	

	/**
	 * @return  the vehiclesPerSecond
	 * @uml.property  name="vehiclesPerSecond"
	 */
	public int getVehiclesPerSecond() {
		return vehiclesPerSecond;
	}
	

	/**
	 * @param vehiclesPerSecond  the vehiclesPerSecond to set
	 * @uml.property  name="vehiclesPerSecond"
	 */
	public void setVehiclesPerSecond(int vehiclesPerSecond) {
		this.vehiclesPerSecond = vehiclesPerSecond;
	}

	/**
	 * Returns the vehicle selection for the layer
	 * @return
	 * @uml.property  name="vehicleSelection"
	 */
	public TypeSelection<VehicleType> getVehicleSelection() {
		return vehicleSelection;
	}

	/**
	 * Sets the vehicle selection for the layer
	 * @param  vehicleSelection
	 * @uml.property  name="vehicleSelection"
	 */
	public void setVehicleSelection(TypeSelection<VehicleType> vehicleSelection) {
		this.vehicleSelection = vehicleSelection;
	}

	@Override
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<randomTrafficLayer name=\"" + name + "\" startingTime =\"" + String.valueOf(startingTime) + "\" endingTime =\""+ String.valueOf(endingTime)+ "\" vehiclesPerSecond=\""+String.valueOf(vehiclesPerSecond)+"\">\n");
		
		sb.append(XMLHelpers.vehicleSelectionToXML("vehicleSelection", vehicleSelection));
		
		sb.append("</randomTrafficLayer>\n");
		
		return sb.toString();
	}

}
