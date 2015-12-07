package classes;

import enums.TrafficDefinitionLayerType;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import roadnetwork.RoadNetwork;
import roadnetwork.RoadNetworkParser;
import simulation.JobType;
import simulation.Simulation;
import simulation.VehicleType;
import tdl.XMLHelpers;
import trafficdefinition.ActivityBasedTrafficDefinitionLayer;
import trafficdefinition.RandomTrafficDefinitionLayer;
import trafficdefinition.TrafficDefinitionLayer;
import trafficdefinition.UserDefinedTrafficDefinitionLayer;

/**
 * Class representing a simulation project. The project's most basic elements are a road network and a list of traffic definition layers.
 */
public class Project {

	/**
	 * Static field containing the currently loaded project
	 */
	private transient static Project currentlyLoadedProject = null;

	/**
	 * Static method that returns the currently loaded project.
	 * @return  the currently loaded project
	 * @uml.property  name="currentlyLoadedProject"
	 */
	public static Project getCurrentlyLoadedProject() {
		return currentlyLoadedProject;
	}

	/**
	 * The default job type
	 */
	private JobType defaultJobType = null;

	/**
	 * The default vehicle type
	 */
	private VehicleType defaultVehicleType = null;

	/**
	 * The project's file name.
	 */
	private String fileName = null;

	/**
	 * The list of job types.
	 */
	private List<JobType> jobTypes = null;

	/**
	 * The road network (map).
	 */
	private RoadNetwork roadNetwork = null;

	/**
	 * The list of traffic definition layers.
	 */
	private List<TrafficDefinitionLayer> trafficLayers = null;

	/**
	 * The list of vehicle types.
	 */
	private List<VehicleType> vehicleTypes = null;

	/**
	 * The vehicle selection for the activity-based traffic layers.
	 * @uml.property  name="activityBasedTrafficVehicleSelection"
	 * @uml.associationEnd  
	 */
	private TypeSelection<VehicleType> activityBasedTrafficVehicleSelection;

	/**
	 * The project's simulation
	 */
	private Simulation simulation = new Simulation();

	/**
	 * Constructor for opening a project from a file
	 * 
	 * @param projectPath
	 *            The path to the file containing the project
	 * @throws Exception
	 *             If an error has occured during the project opening process.
	 */
	public Project(String projectPath) throws Exception {
		// Set the project's file name
		fileName = projectPath;

		// Create the input streams to read the project from the file
		FileInputStream fileIn = new FileInputStream(fileName);
		ObjectInputStream in = new ObjectInputStream(fileIn);

		// Read the road network
		roadNetwork = (RoadNetwork) in.readObject();

		// Read the traffic definition layers
		trafficLayers = (ArrayList<TrafficDefinitionLayer>) in.readObject();

		// Read the simulation
		simulation = (Simulation) in.readObject();

		// Read the job and vehicle types
		jobTypes = (ArrayList<JobType>) in.readObject();
		vehicleTypes = (ArrayList<VehicleType>) in.readObject();
		defaultJobType = (JobType) in.readObject();
		defaultVehicleType = (VehicleType) in.readObject();

		// Read the vehicle selection for the activity-based layers
		activityBasedTrafficVehicleSelection = (TypeSelection<VehicleType>) in.readObject();

		in.close();

		// Set the static currently loaded project variable
		Project.currentlyLoadedProject = this;
	}

	/**
	 * Constructor for creating a new project.
	 * 
	 * @param projectPath
	 *            The path to the file that the project will be saved to.
	 * @param roadNetworkFile
	 *            The file containing the road network.
	 * @throws Exception
	 *             If an error has occured during the project creation process.
	 */
	public Project(String projectPath, File roadNetworkFile) throws Exception {
		// Set the static currently loaded project variable
		Project.currentlyLoadedProject = this;

		// Parse the road network xml file
		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		Document roadNetworkXMLDocument = parser.parse(roadNetworkFile);

		roadNetwork = (new RoadNetworkParser()).ParseXMLNetFile(roadNetworkXMLDocument);

		// Initialize the layers
		trafficLayers = new ArrayList<TrafficDefinitionLayer>();

		// Create the job types array and add the default type
		jobTypes = new ArrayList<JobType>();
		defaultJobType = JobType.CreateDefaultType();
		jobTypes.add(defaultJobType);

		// Create the vehicle types array and add the default type
		vehicleTypes = new ArrayList<VehicleType>();
		defaultVehicleType = VehicleType.CreateDefaultType();
		vehicleTypes.add(defaultVehicleType);

		// Add the default vehicle to the activity-based layers vehicle
		// selection
		activityBasedTrafficVehicleSelection = new TypeSelection<VehicleType>();
		activityBasedTrafficVehicleSelection.add(new Pair<VehicleType, Float>(defaultVehicleType, 1.f));

		// Create a default user defined traffic definition layer
		createNewLayer(TrafficDefinitionLayerType.UserDefined);

		// Set the project's file name
		fileName = projectPath;

		// Create the output streams to write the project to file
		FileOutputStream fileOut = new FileOutputStream(fileName);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);

		// Read the edges and nodes files
		File edgesFile = new File(roadNetworkFile.getAbsolutePath().replaceFirst(".net.", ".edg."));
		File nodesFile = new File(roadNetworkFile.getAbsolutePath().replaceFirst(".net.", ".nod."));

		FileInputStream fileInEdges = new FileInputStream(edgesFile);
		FileInputStream fileInNodes = new FileInputStream(nodesFile);

		byte[] edgeBytes = new byte[(int) edgesFile.length()];
		byte[] nodeBytes = new byte[(int) nodesFile.length()];

		fileInEdges.read(edgeBytes);
		fileInNodes.read(nodeBytes);

		// Save the contents of the edges and nodes files in the road network
		roadNetwork.setRoadNetworkEdgesXML(new String(edgeBytes));
		roadNetwork.setRoadNetworkNodesXML(new String(nodeBytes));

		// Write the project
		out.writeObject(roadNetwork);
		out.writeObject(trafficLayers);
		out.writeObject(simulation);
		out.writeObject(jobTypes);
		out.writeObject(vehicleTypes);
		out.writeObject(defaultJobType);
		out.writeObject(defaultVehicleType);
		out.writeObject(activityBasedTrafficVehicleSelection);

		out.close();
	}

	/**
	 * Closes the project (sets the currently loaded project to null)
	 * 
	 */
	public void Close() {
		// Set the static currently loaded project variable
		Project.currentlyLoadedProject = null;
	}

	/**
	 * Creates a new {@link TrafficDefinitionLayer} that is a copy of the source
	 * layer and adds it to the project. The project is automatically registered
	 * as a listener to be notified when the new layer is modified so that it
	 * knows when it needs to be saved (see the {@link #dirty field}).
	 * 
	 * @param source
	 *            The layer to be copied.
	 * @return The newly created layer.
	 */
	public TrafficDefinitionLayer createDuplicateLayer(TrafficDefinitionLayer source) {
		TrafficDefinitionLayer newLayer = source.duplicate();

		trafficLayers.add(newLayer);

		return newLayer;
	}

	/**
	 * Creates a new {@link TrafficDefinitionLayer} and adds it to the project.
	 * 
	 * @param type
	 *            The type of the new layer
	 * @return The newly created layer.
	 */
	public TrafficDefinitionLayer createNewLayer(TrafficDefinitionLayerType type) {
		TrafficDefinitionLayer newLayer = null;

		switch (type) {
		case UserDefined:
			newLayer = new UserDefinedTrafficDefinitionLayer();
			break;
		case ActivityBased:
			newLayer = new ActivityBasedTrafficDefinitionLayer();
			break;
		case Random:
			newLayer = new RandomTrafficDefinitionLayer();
			break;
		}

		trafficLayers.add(newLayer);

		return newLayer;
	}

	/**
	 * Removes the layer specified from the project's traffic layers collection.
	 * 
	 * @param layer
	 *            The layer to delete.
	 */
	public void DeleteLayer(TrafficDefinitionLayer layer) {
		trafficLayers.remove(layer);
	}

	/**
	 * Returns the default job type
	 * @return  the default job type
	 * @uml.property  name="defaultJobType"
	 */
	public JobType getDefaultJobType() {
		return defaultJobType;
	}

	/**
	 * Returns the default vehicle type
	 * @return  the default vehicle type
	 * @uml.property  name="defaultVehicleType"
	 */
	public VehicleType getDefaultVehicleType() {
		return defaultVehicleType;
	}

	/**
	 * Gets the project's file name
	 * @return
	 * @uml.property  name="fileName"
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Gets the project's job types
	 * @return
	 * @uml.property  name="jobTypes"
	 */
	public List<JobType> getJobTypes() {
		return jobTypes;
	}

	/**
	 * Gets the {@link TrafficDefinitionLayer} with the specified index.
	 * 
	 * @param index
	 * @return
	 */
	public TrafficDefinitionLayer getLayer(int index) {
		return trafficLayers.get(index);
	}

	/**
	 * Gets the project's road network.
	 * @return
	 * @uml.property  name="roadNetwork"
	 */
	public RoadNetwork getRoadNetwork() {
		return roadNetwork;
	}

	/**
	 * Gets the project's traffic definition layers
	 * @return
	 * @uml.property  name="trafficLayers"
	 */
	public List<TrafficDefinitionLayer> getTrafficLayers() {
		return trafficLayers;
	}

	/**
	 * Returns the project's vehicle types
	 * @return
	 * @uml.property  name="vehicleTypes"
	 */
	public List<VehicleType> getVehicleTypes() {
		return vehicleTypes;
	}

	/**
	 * Exports the traffic definition project in TDL format
	 * @param path the path to the file name to export
	 */
	public void saveTDL(String path) throws Exception {
		FileWriter fw = new FileWriter(path);
		PrintWriter pw = new PrintWriter(fw);

		pw.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		pw.println("<trafficModel name =\"" + simulation.getName() + "\" startingTime =\"" + String.valueOf(simulation.getBeginTime()) + "\" endingTime =\"" + String.valueOf(simulation.getEndTime()) + "\" xmlns=\"http://tempuri.org/TDL.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" >");

		pw.println("<jobTypes>");
		for (JobType j : jobTypes) {
			pw.println("<job name=\"" + j.getName() + "\" startingTime=\"" + String.valueOf(j.getStartingTime()) + "\" endingTime=\"" + String.valueOf(j.getEndingTime()) + "\"/>");
		}
		pw.println("</jobTypes>");

		pw.println("<vehicleTypes>");
		for (VehicleType v : vehicleTypes) {
			pw.println("<vehicle name=\"" + v.getName() + "\" acceleration=\"" + String.valueOf(v.getAcceleration()) + "\" deceleration=\"" + String.valueOf(v.getDeceleration()) + "\" length=\"" + String.valueOf(v.getLength()) + "\" maximumSpeed=\"" + String.valueOf(v.getMaximumSpeed()) + "\" driverImperfection=\"" + String.valueOf(v.getDriverImperfection()) + "\" />");
		}
		pw.println("</vehicleTypes>");

		pw.print(XMLHelpers.vehicleSelectionToXML("activityBasedTrafficVehicleSelection", activityBasedTrafficVehicleSelection));

		pw.println("<trafficLayers>");
		for (TrafficDefinitionLayer tdl : trafficLayers) {
			pw.print(tdl.toXML());
		}
		pw.println("</trafficLayers>");

		pw.println("</trafficModel>");

		pw.close();
		fw.close();
	}

	/**
	 * Saves the project to the file specified by the {@link #fileName} field
	 * 
	 * @throws Exception
	 *             If an error occurs during the project saving process.
	 */

	public void Save() throws Exception {
		// Create the output streams used for saving the project
		FileOutputStream fileOut = new FileOutputStream(fileName, false);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);

		// Write the project elements
		out.writeObject(roadNetwork);
		out.writeObject(trafficLayers);
		out.writeObject(simulation);
		out.writeObject(jobTypes);
		out.writeObject(vehicleTypes);
		out.writeObject(defaultJobType);
		out.writeObject(defaultVehicleType);
		out.writeObject(activityBasedTrafficVehicleSelection);

		out.close();
	}

	/**
	 * Saves the project to the file specified and sets the project's
	 * {@link #fileName} to the specified path.
	 * 
	 * @param path
	 *            The path to the file that the project will be saved to.
	 * @throws Exception
	 *             If an error has occured during the project saving process.
	 */

	public void SaveAs(String path) throws Exception {

		fileName = path;

		Save();
	}

	/**
	 * Sets the project's job types
	 * @param  jobTypes
	 * @uml.property  name="jobTypes"
	 */
	public void setJobTypes(List<JobType> jobTypes) {
		this.jobTypes = jobTypes;
	}

	/**
	 * Sets the project's vehicle types
	 * @param  vehicleTypes
	 * @uml.property  name="vehicleTypes"
	 */
	public void setVehicleTypes(List<VehicleType> vehicleTypes) {
		this.vehicleTypes = vehicleTypes;
	}

	/**
	 * Returns the project's simulation
	 * @return
	 * @uml.property  name="simulation"
	 */
	public Simulation getSimulation() {
		return simulation;
	}

	/**
	 * Returns the vehicle selection for the activity based layers
	 * @return
	 * @uml.property  name="activityBasedTrafficVehicleSelection"
	 */
	public TypeSelection<VehicleType> getActivityBasedTrafficVehicleSelection() {
		return activityBasedTrafficVehicleSelection;
	}

	/**
	 * Sets the vehicle selection for the activity-based layers
	 * @param  vehicleSelection
	 * @uml.property  name="activityBasedTrafficVehicleSelection"
	 */
	public void setActivityBasedTrafficVehicleSelection(TypeSelection<VehicleType> vehicleSelection) {
		this.activityBasedTrafficVehicleSelection = vehicleSelection;
	}
}
