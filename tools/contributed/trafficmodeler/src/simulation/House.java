package simulation;

import classes.TypeSelection;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import roadnetwork.Edge;
import trafficdefinition.School;
import trafficdefinition.TrafficArea;

/**
 * Represents a virtual house.
 */
public class House {
	/**
	 * Adults living in the house
	 */
	private List<Adult> adults = new ArrayList<Adult>();

	/**
	 * Children living in the house
	 */
	private List<Child> children = new ArrayList<Child>();

	/**
	 * House address
	 */
	private Edge address;

	/**
	 * The area in which the house is located
	 */
	private TrafficArea area;

	/**
	 * The house ID
	 */
	private int id;

	/**
	 * House coordinates
	 */
	private Point2D.Double location;

	/**
	 * Class constructor
	 * 
	 * @param area
	 *            the area in which the house is located
	 * @param id
	 *            the house's id
	 */
	public House(TrafficArea area, int id) {
		super();
		this.id = id;
		this.area = area;
	}

	/**
	 * Assigns vehicles to the house's drivers based on the types of the
	 * vehicles of the area the house is in and the possibility that a driver
	 * has a car
	 * 
	 * @param possibilityDriverHasCar
	 * @param areaVehicleTypes
	 */
	public void assignVehiclesToDrivers(float possibilityDriverHasCar, TypeSelection<VehicleType> areaVehicleTypes) {
		// Vehicles belonging to house residents
		List<VehicleType> vehicles = new ArrayList<VehicleType>();

		// Calculate how many vehicles there are in the house
		int numberOfVehicles = Math.round(possibilityDriverHasCar * getNumberOfDrivers());

		// Create the vehicles
		for (int i = 0; i < numberOfVehicles; i++) {
			vehicles.add(areaVehicleTypes.getRandomType());
		}

		// Get available vehicles
		List<VehicleType> availableVehicles = new ArrayList<VehicleType>();
		availableVehicles.addAll(vehicles);

		List<Adult> availableWorkingDriverAdults = getWorkingDrivers();
		List<Adult> availableNonWorkingDriverAdults = getNonWorkingDrivers();

		// Assign vehicles first to working drivers and then to non working
		// drivers
		while (!availableVehicles.isEmpty()) {
			if (!availableWorkingDriverAdults.isEmpty()) {
				availableWorkingDriverAdults.get(0).setVehicle(availableVehicles.get(0));

				availableWorkingDriverAdults.remove(0);
			} else {
				availableNonWorkingDriverAdults.get(0).setVehicle(availableVehicles.get(0));

				availableNonWorkingDriverAdults.remove(0);
			}

			availableVehicles.remove(0);
		}
	}

	/**
	 * Creates activities for 24 hours for the residents of the virtual house
	 * 
	 * @param tripsList
	 *            the list in which to place the generated trips
	 * @param routesList
	 *            the list in which to place the generated routes
	 * @throws Exception
	 *             if anything goes wrong
	 */
	public void createActivities(SUMOInstructionList<Trip> tripsList, SUMOInstructionList<Route> routesList) throws Exception {
		String color = new Float(area.getColor().getRed() / 255f).toString() + "," + new Float(area.getColor().getGreen() / 255f).toString() + "," + new Float(area.getColor().getBlue() / 255f).toString();

		// Hashtable containing the house's children grouped by the school they
		// are going
		Hashtable<School, List<Child>> childrenBySchool = new Hashtable<School, List<Child>>();

		// List containing a list of all the schools that the children of the house are going
		List<School> schools = new ArrayList<School>();

		// Group children by school
		for (Child c : children) {
			// If the child is going to a school
			if (c.getSchool() != null) {
				if (!childrenBySchool.containsKey(c.getSchool())) {
					childrenBySchool.put(c.getSchool(), new ArrayList<Child>());
					
					schools.add(c.getSchool());
				}

				childrenBySchool.get(c.getSchool()).add(c);
			}
		}
		
		//Sort the schools list by priority
		Collections.sort(schools, new School.SchoolPriorityComparator());
		
		// Loop through all schools order by school priority
		for (School school : schools) {
			// Loop through the house's adults that own a vehicle order by employment status
			// and assign children to those who can take them to school
			for (Adult a : getAdultsWithVehicleOrderByEmploymentStatus()) {
				if (a.canTakeChildToSchool(school, 10 * 60)) {
					a.takeChildrenToSchool(childrenBySchool.get(school));

					break;
				}
			}
			
			// Loop through the house's adults that own a vehicle order by employment status
			// and assign children to those who can pick them up from school
			for (Adult a : getAdultsWithVehicleOrderByEmploymentStatus()) {
				if (a.canPickupChildFromSchool(school)) {
					a.pickupChildrenFromSchool(childrenBySchool.get(school));

					break;
				}
			}
		}

		int adultCounter = 0;

		// Create trips for all adults in the house
		for (Adult a : adults) {
			a.createTrips(color, tripsList, routesList, adultCounter++);
		}
	}

	/**
	 * Creates the house's adults and assigns them properties
	 * 
	 * @param numberOfAdults
	 *            the number of adults to create
	 * @param possibilityAdultIsDriver
	 *            the possibility that an adult is a driver
	 * @param possibilityAdultHasJob
	 *            the possibility that an adult has a job
	 */

	public void createAdults(int numberOfAdults, float possibilityAdultIsDriver, float possibilityAdultHasJob) {
		for (int i = 0; i < numberOfAdults; i++) {
			adults.add(new Adult(this, possibilityAdultIsDriver, possibilityAdultHasJob));
		}
	}

	/**
	 * Create's the house's children
	 * 
	 * @param numberOfChildren
	 *            the number of children to create
	 */

	public void createChildren(int numberOfChildren) {
		for (int i = 0; i < numberOfChildren; i++) {
			children.add(new Child());
		}
	}

	/**
	 * @return  the children
	 * @uml.property  name="children"
	 */
	public List<Child> getChildren() {
		return children;
	}

	/**
	 * @return  the address
	 * @uml.property  name="address"
	 */
	public Edge getAddress() {
		return address;
	}

	/**
	 * @return  the location
	 * @uml.property  name="location"
	 */
	public Point2D.Double getLocation() {
		return location;
	}

	/**
	 * Returns the total number of residents in the house
	 * 
	 * @return
	 */

	public int getNumberOfResidents() {
		return adults.size() + children.size();
	}

	/**
	 * Returns the working adults of the house
	 * 
	 * @return
	 */

	public List<Adult> getWorkingAdults() {
		List<Adult> result = new ArrayList<Adult>();

		for (Adult a : adults) {
			if (a.hasJob()) {
				result.add(a);
			}
		}

		return result;
	}

	/**
	 * @param address  the address to set
	 * @uml.property  name="address"
	 */
	public void setAddress(Edge closestEdge) {
		this.address = closestEdge;
	}

	/**
	 * @param location  the location to set
	 * @uml.property  name="location"
	 */
	public void setLocation(Point2D.Double location) {
		this.location = location;
	}

	/**
	 * Returns the non-working adults of the house
	 * 
	 * @return
	 */

	private List<Adult> getNonWorkingDrivers() {
		List<Adult> nonWorkingDrivers = new ArrayList<Adult>();

		for (Adult a : adults) {
			if (a.canDrive() && (!a.hasJob())) {
				nonWorkingDrivers.add(a);
			}
		}

		return nonWorkingDrivers;
	}

	/**
	 * Returns a list of all adults in the house that own a vehicle ordered
	 * by their employment status. Unemployed are first and employed follow.
	 * @return
	 */
	private List<Adult> getAdultsWithVehicleOrderByEmploymentStatus(){
		List<Adult> result = new ArrayList<Adult>();
		
		//Add first unemployed adults
		for(Adult a:adults){
			if(!a.hasJob()&&a.getVehicle()!=null)
				result.add(a);
		}
		
		//Add employed adults
		for(Adult a:adults){
			if(a.hasJob()&&a.getVehicle()!=null)
				result.add(a);
		}
		
		return result;
	}
	
	/**
	 * Returns the total number of drivers in the hosue
	 * 
	 * @return
	 */
	private int getNumberOfDrivers() {
		int drivers = 0;

		for (Adult a : adults) {
			if (a.canDrive()) {
				drivers++;
			}
		}

		return drivers;
	}

	/**
	 * Returns the working drivers in the house
	 * 
	 * @return
	 */

	private List<Adult> getWorkingDrivers() {
		List<Adult> workingDrivers = new ArrayList<Adult>();

		for (Adult a : adults) {
			if (a.canDrive() && a.hasJob()) {
				workingDrivers.add(a);
			}
		}

		return workingDrivers;
	}

	/**
	 * @return  the id
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id;
	}

}
