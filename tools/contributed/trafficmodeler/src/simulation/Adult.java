package simulation;

import classes.Constants;
import helpers.MultipleTripExpander;
import helpers.RandomNumberProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import roadnetwork.Edge;
import trafficdefinition.School;

/**
 * Class representing an adult in a virtual area. This class is used when
 * creating traffic based on demographic data.
 */
public class Adult extends Person {
	/**
	 * Flag to know if the adult can drive
	 */
	private boolean canDrive;

	/**
	 * A list of the children that the adult has to pick up from school
	 */
	private List<Child> childrenToPickupFromSchool = null;

	/**
	 * A list of the children that the adult has to take to school
	 */
	private List<Child> childrenToTakeToSchool = null;

	/**
	 * Flag to know if the adult has a job
	 */
	private boolean hasJob;

	/**
	 * The adult's virtual house
	 */
	private House home = null;

	/**
	 * The adult's job type
	 */
	private JobType job;

	/**
	 * The street on which the adult's job is located
	 */
	private Edge jobLocation = null;

	/**
	 * The vehicle that the adult owns
	 */
	private VehicleType vehicle = null;

	/**
	 * Class constructor
	 * 
	 * @param house
	 *            the adult's virtual house
	 * @param possibilityAdultIsDriver
	 *            the possibility that the adult is a driver
	 * @param possibilityAdultHasJob
	 *            the possibility that the adult is working
	 */
	public Adult(House house, float possibilityAdultIsDriver, float possibilityAdultHasJob) {
		super();

		Random r = RandomNumberProvider.getRandom();

		// Set the driver property based on the possibility
		if (r.nextFloat() <= possibilityAdultIsDriver) {
			canDrive = true;
		} else {
			canDrive = false;
		}

		// Set the worker property based on the possibility
		if (r.nextFloat() <= possibilityAdultHasJob) {
			hasJob = true;
		} else {
			hasJob = false;
		}

		home = house;
	}

	/**
	 * Returns whether the adult can drive
	 * 
	 * @return
	 */
	public boolean canDrive() {
		return canDrive;
	}

	/**
	 * Checks to see if the adult can pickup the children from the specified
	 * school.
	 * 
	 * @param school
	 * @return
	 */
	public boolean canPickupChildFromSchool(School school) {
		// If the adult doesn't own a car then return false
		if (vehicle == null) {
			return false;
		}

		// If the driver is already responsible for picking up other children
		// from school return false
		if (childrenToPickupFromSchool != null) {
			return false;
		}

		// If the adult doesn't have a job then she is always available
		if (!hasJob) {
			return true;
		} else {
			// Try to estimate the distance between the school and the job
			float distanceToTravel = school.getClosestEdge().DistanceFrom(jobLocation);

			// Estimate the time needed to cover this distance
			int estimatedTravelTime = Math.round(distanceToTravel / Constants.averageSpeed);

			// If the adult can make it on time from work to school then she
			// can pickup the kid to school
			if (job.getEndingTime() + estimatedTravelTime <= school.getTimeClosing() + school.amountOfTimeChildCanWaitAfterSchool()) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Checks to see if the adult can take the children to the specified school
	 * 
	 * @param school
	 *            the school to take the children to
	 * @param amountOfTimeAdultAllowedToBeLate
	 *            the number of seconds that the adult is allowed to be late to
	 *            work
	 * @return
	 */
	public boolean canTakeChildToSchool(School school, int amountOfTimeAdultAllowedToBeLate) {
		// If the adult doesn't have a vehicle then return false
		if (vehicle == null) {
			return false;
		}

		// If the adult is already responsible for taking other children to
		// school then return false
		if (childrenToTakeToSchool != null) {
			return false;
		}

		// If the adult is not working then he can take the children to school
		if (!hasJob) {
			return true;
		} else {
			if (school.getTimeOpening() < job.getStartingTime()) {
				// Try to estimate the distance between the school and the job
				float distanceToTravel = school.getClosestEdge().DistanceFrom(jobLocation);

				// Estimate the time needed to cover this distance
				int estimatedTravelTime = Math.round(distanceToTravel / Constants.averageSpeed);

				// If the adult can make it on time from the school to work then
				// she can take the kid to school
				if (school.getTimeOpening() + estimatedTravelTime <= job.getStartingTime() + amountOfTimeAdultAllowedToBeLate) {
					return true;
				}
			}
		}

		// Else the adult can not take the children to school
		return false;
	}

	/**
	 * Creates the adult's vehicle routes for 24 hours based on the adult's
	 * properties and whether the adult will take to or pick up children from
	 * school
	 * 
	 * @param color
	 *            the color that will be used for the routes
	 * @param tripsList
	 *            List to place the generated trips into
	 * @param routesList
	 *            List to place the generated routes into
	 * @param counter
	 *            variable used to assign unique ids to trips and routes
	 * @throws Exception
	 *             if anything goes wrong
	 */
	public void createTrips(String color, SUMOInstructionList<Trip> tripsList, SUMOInstructionList<Route> routesList, int counter) throws Exception {

		// If the adult does not own a vehicle then no trips will be generated
		if (vehicle == null) {
			return;
		}

		// If the adult has a job and she has to take children to school
		if (hasJob && (childrenToTakeToSchool != null)) {
			// Get the children's school
			School school = childrenToTakeToSchool.get(0).getSchool();

			// Estimate distance between home->school and home->work
			float distanceHomeSchool = home.getAddress().DistanceFrom(school.getClosestEdge());
			float distanceHomeWork = home.getAddress().DistanceFrom(jobLocation);

			// Estimate the time needed to travel from school->home->work,
			// school->home and home->work
			int estimatedTravelTimeSchoolHomeWork = Math.round((distanceHomeSchool + distanceHomeWork) / Constants.averageSpeed);
			int estimatedTravelTimeHomeSchool = Math.round(distanceHomeSchool / Constants.averageSpeed);
			int estimatedTravelTimeHomeWork = Math.round(distanceHomeWork / Constants.averageSpeed);

			int timeBetweenSchoolOpeningAndJobStarting = job.getStartingTime() - school.getTimeOpening();

			// Calculate the time to leave the house so that the child will
			// be at school on time
			int timeOfDepartureFromHouseToSchool = Math.max(0, school.getTimeOpening() - estimatedTravelTimeHomeSchool);

			// If the adult cannot go from school back home, stay for 20 minutes
			// and then go to work, then go straight from school to work
			if ((estimatedTravelTimeSchoolHomeWork + 20 * 60) > timeBetweenSchoolOpeningAndJobStarting) {
				// Create route for home->school->work
				List<Edge> tripEdges = new ArrayList<Edge>();

				tripEdges.add(home.getAddress());
				tripEdges.add(school.getClosestEdge());
				tripEdges.add(jobLocation);

				String route = MultipleTripExpander.ExpandTrips(tripEdges);

				// Write the route
				routesList.add(new Route(timeOfDepartureFromHouseToSchool, "<vehicle id=\"home-school-work-" + String.valueOf(home.getId()) + "-" + String.valueOf(counter) + "\" type=\"" + vehicle.getName() + "\" depart=\"" + String.valueOf(timeOfDepartureFromHouseToSchool) + "\" color=\"" + color + "\">" + "<route color=\"" + color + "\">" + route + "</route>" + "</vehicle>"));
			}
			// If there is enough time go back home and later go to work
			else {
				// Create route for home->school->home
				List<Edge> tripEdges = new ArrayList<Edge>();

				tripEdges.add(home.getAddress());
				tripEdges.add(school.getClosestEdge());
				tripEdges.add(home.getAddress());

				String route = MultipleTripExpander.ExpandTrips(tripEdges);

				// Write the route
				routesList.add(new Route(timeOfDepartureFromHouseToSchool, "<vehicle id=\"home-school-home-" + String.valueOf(home.getId()) + "-" + String.valueOf(counter) + "\" type=\"" + vehicle.getName() + "\" depart=\"" + String.valueOf(timeOfDepartureFromHouseToSchool) + "\" color=\"" + color + "\">" + "<route color=\"" + color + "\">" + route + "</route>" + "</vehicle>"));

				// Estimate what time the adult has to leave home to be at work
				// on time
				int timeOfDepartureFromHouseToWork = Math.max(0, job.getStartingTime() - estimatedTravelTimeHomeWork);

				// Write the trip home->work
				tripsList.add(new Trip(timeOfDepartureFromHouseToWork,"<trip id=\"house-work-" + String.valueOf(home.getId()) + "-" + String.valueOf(counter) + "\" depart=\"" + String.valueOf(timeOfDepartureFromHouseToWork) + "\" from=\"" + home.getAddress().getId() + "\" to=\"" + jobLocation.getId() + "\" color=\"" + color + "\" type=\"" + vehicle.getName() + "\" />\n"));
			}
		}
		// If the adult has a job but doesn't have to take children to school
		else if (hasJob && (childrenToTakeToSchool == null)) {
			// Just go to work

			// Try to estimate the distance between home and work
			float distanceToTravel = home.getAddress().DistanceFrom(jobLocation);

			// Estimate the time needed to cover this distance
			int estimatedTravelTime = Math.round(distanceToTravel / Constants.averageSpeed);

			// Calculate the time to leave the house so that the adult will be
			// at work on time
			int timeOfDepartureFromHouse = Math.max(0, job.getStartingTime() - estimatedTravelTime);

			// Create route from house to work
			tripsList.add(new Trip(timeOfDepartureFromHouse,"<trip id=\"house-work-" + String.valueOf(home.getId()) + "-" + String.valueOf(counter) + "\" depart=\"" + String.valueOf(timeOfDepartureFromHouse) + "\" from=\"" + home.getAddress().getId() + "\" to=\"" + jobLocation.getId() + "\" color=\"" + color + "\" type=\"" + vehicle.getName() + "\" />\n"));
		}
		// If the adult is unemployeed and has to take children to school
		else if (!hasJob && (childrenToTakeToSchool != null)) {
			// Get the children's school
			School school = childrenToTakeToSchool.get(0).getSchool();

			// Try to estimate the distance that the vehicle will need to
			// travel
			float distanceHomeSchool = home.getAddress().DistanceFrom(school.getClosestEdge());

			// Estimate the time needed to go from school to home
			int estimatedTravelTimeHomeSchool = Math.round(distanceHomeSchool / Constants.averageSpeed);

			// Calculate the time to leave the house
			int timeOfDepartureFromHouse = Math.max(0, school.getTimeOpening() - estimatedTravelTimeHomeSchool);

			// Go from home to school and back
			List<Edge> tripEdges = new ArrayList<Edge>();

			tripEdges.add(home.getAddress());
			tripEdges.add(school.getClosestEdge());
			tripEdges.add(home.getAddress());

			String route = MultipleTripExpander.ExpandTrips(tripEdges);

			// Write the route
			routesList.add(new Route(timeOfDepartureFromHouse,"<vehicle id=\"home-school-home-" + String.valueOf(home.getId()) + "-" + String.valueOf(counter) + "\" type=\"" + vehicle.getName() + "\" depart=\"" + String.valueOf(timeOfDepartureFromHouse) + "\" color=\"" + color + "\">"+"<route color=\"" + color + "\">" + route + "</route>"+"</vehicle>"));
		}

		// If the adult has a job and she doesn't have to pick up kids from
		// school
		if (hasJob && (childrenToPickupFromSchool == null)) {
			// Create route from work to home
			tripsList.add(new Trip(job.getEndingTime(),"<trip id=\"work-house-" + String.valueOf(home.getId()) + "-" + String.valueOf(counter) + "\" depart=\"" + String.valueOf(job.getEndingTime()) + "\" from=\"" + jobLocation.getId() + "\" to=\"" + home.getAddress().getId() + "\" color=\"" + color + "\" type=\"" + vehicle.getName() + "\" />\n"));
		}
		// If the adult has a job and she has to pickup kids from school
		else if (hasJob && (childrenToPickupFromSchool != null)) {
			// Get the children's school
			School school = childrenToPickupFromSchool.get(0).getSchool();

			// Estimate distance between home-school and school-work
			float distanceHomeSchool = home.getAddress().DistanceFrom(school.getClosestEdge());
			float distanceHomeWork = home.getAddress().DistanceFrom(jobLocation);

			// Estimate the time needed to go from work to school and then from
			// school to home
			int estimatedTravelTimeWorkSchoolHome = Math.round((distanceHomeSchool + distanceHomeWork) / Constants.averageSpeed);

			int timeBetweenJobEndingAndSchoolClosing = school.getTimeClosing() - job.getEndingTime();

			// If the adult doesn't have enough time after work to go home for
			// 20 minutes
			if ((estimatedTravelTimeWorkSchoolHome + 20 * 60) > timeBetweenJobEndingAndSchoolClosing) {
				// Go straight from work to school and then home
				List<Edge> tripEdges = new ArrayList<Edge>();

				tripEdges.add(jobLocation);
				tripEdges.add(school.getClosestEdge());
				tripEdges.add(home.getAddress());

				// Build the route
				String route = MultipleTripExpander.ExpandTrips(tripEdges);

				routesList.add(new Route(job.getEndingTime(),"<vehicle id=\"work-school-home-" + String.valueOf(home.getId()) + "-" + String.valueOf(counter) + "\" type=\"" + vehicle.getName() + "\" depart=\"" + String.valueOf(job.getEndingTime()) + "\" color=\"" + color + "\">"+"<route color=\"" + color + "\">" + route + "</route>"+"</vehicle>"));
			}
			// If the adult has enough time to go home after work
			else {
				// Go from work to home and from home to school and from school
				// to home
				// Create route from work to home
				tripsList.add(new Trip(job.getEndingTime(),"<trip id=\"work-house-" + String.valueOf(home.getId()) + "-" + String.valueOf(counter) + "\" depart=\"" + String.valueOf(job.getEndingTime()) + "\" from=\"" + jobLocation.getId() + "\" to=\"" + home.getAddress().getId() + "\" color=\"" + color + "\" type=\"" + vehicle.getName() + "\" />\n"));

				// Estimate the time needed to cover the distance from home to
				// school
				int estimatedTravelTime = (int) Math.round(distanceHomeSchool / Constants.averageSpeed);

				// Calculate the time to leave the house so that the adult will
				// be at school on time
				int timeOfDepartureFromHouse = Math.max(0, school.getTimeClosing() - estimatedTravelTime);

				// Build the route
				List<Edge> tripEdges = new ArrayList<Edge>();

				tripEdges.add(home.getAddress());
				tripEdges.add(school.getClosestEdge());
				tripEdges.add(home.getAddress());

				String route = MultipleTripExpander.ExpandTrips(tripEdges);

				routesList.add(new Route(timeOfDepartureFromHouse,"<vehicle id=\"home-school-home-" + String.valueOf(home.getId()) + "-" + String.valueOf(counter) + "\" type=\"" + vehicle.getName() + "\" depart=\"" + String.valueOf(timeOfDepartureFromHouse) + "\" color=\"" + color + "\">"+"<route color=\"" + color + "\">" + route + "</route>"+"</vehicle>"));
			}

		}
		// If the adult has no job and has children to pick up
		else if (!hasJob && (childrenToPickupFromSchool != null)) {
			School school = childrenToPickupFromSchool.get(0).getSchool();

			// Try to estimate the distance between home and school
			float distanceToTravel = home.getAddress().DistanceFrom(school.getClosestEdge());

			// Estimate the time needed to cover this distance
			int estimatedTravelTime = (int) Math.round(distanceToTravel / Constants.averageSpeed);

			// Calculate the time to leave the house so that the adult will be
			// at school on time
			int timeOfDepartureFromHouse = Math.max(0, school.getTimeClosing() - estimatedTravelTime);

			// Create route from house to school and back
			List<Edge> tripEdges = new ArrayList<Edge>();

			tripEdges.add(home.getAddress());
			tripEdges.add(school.getClosestEdge());
			tripEdges.add(home.getAddress());

			String route = MultipleTripExpander.ExpandTrips(tripEdges);
			routesList.add(new Route(timeOfDepartureFromHouse, "<vehicle id=\"home-school-home-" + String.valueOf(home.getId()) + "-" + String.valueOf(counter) + "\" type=\"" + vehicle.getName() + "\" depart=\"" + String.valueOf(timeOfDepartureFromHouse) + "\" color=\"" + color + "\">"+"<route color=\"" + color + "\">" + route + "</route>"+"</vehicle>"));
		}
	}

	/**
	 * @return the job
	 * @uml.property name="job"
	 */
	public JobType getJob() {
		return job;
	}

	/**
	 * @return the jobLocation
	 * @uml.property name="jobLocation"
	 */
	public Edge getJobLocation() {
		return jobLocation;
	}

	public boolean hasJob() {
		return hasJob;
	}

	/**
	 * Assigns the specified children to the adult so that she will take them to
	 * school
	 * 
	 * @param children
	 */
	public void pickupChildrenFromSchool(List<Child> children) {
		childrenToPickupFromSchool = children;
	}

	/**
	 * @param hasJob
	 *            the hasJob to set
	 * @uml.property name="hasJob"
	 */
	public void setHasJob(boolean hasJob) {
		this.hasJob = hasJob;
	}

	/**
	 * Sets the specified job to the adult
	 * 
	 * @param job
	 *            the adult's job type
	 * @param jobLocation
	 *            the job location
	 */
	public void setJobLocation(JobType job, Edge jobLocation) {
		this.jobLocation = jobLocation;
		this.job = job;
	}

	/**
	 * @param vehicle
	 *            the vehicle to set
	 * @uml.property name="vehicle"
	 */
	public void setVehicle(VehicleType vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * Assigns the specified children to the adult so that she will pick them up
	 * from school
	 * 
	 * @param children
	 */
	public void takeChildrenToSchool(List<Child> children) {
		childrenToTakeToSchool = children;
	}

	/**
	 * @return the vehicle
	 * @uml.property name="vehicle"
	 */
	public VehicleType getVehicle() {
		return vehicle;
	}
}
