package simulation;

import helpers.RandomNumberProvider;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import roadnetwork.Edge;
import roadnetwork.Lane;
import roadnetwork.RoadNetwork;
import trafficdefinition.Accident;
import trafficdefinition.AreaFlow;
import trafficdefinition.Flow;
import trafficdefinition.HotSpot;
import trafficdefinition.RandomTrafficDefinitionLayer;
import trafficdefinition.School;
import trafficdefinition.TrafficArea;
import trafficdefinition.TrafficDefinitionElement;
import trafficdefinition.UserDefinedTrafficDefinitionLayer;
import classes.Constants;
import classes.Pair;
import classes.TypeSelection;
import enums.SchoolType;

/**
 * Helper class that generates traffic from user defined traffic elements
 * 
 */
public class TrafficGenerator {

	/**
	 * Method that generates traffic for a user-defined traffic definition layer
	 * 
	 * @param roadNetwork
	 *            the road network for which to generate the traffic
	 * @param layer
	 *            the layer to generate traffic for
	 * @param tripBuilder
	 *            the StringBuilder to place the generated trips in
	 * @param accidentRouteBuilder
	 *            the StringBuilder to place the generated routes for accidents
	 * @throws Exception
	 */
	public static void GenerateUserDefinedLayerTraffic(RoadNetwork roadNetwork, UserDefinedTrafficDefinitionLayer layer, SUMOInstructionList<Trip> tripsList, SUMOInstructionList<Route> accidentRoutesList) throws Exception {
		// Get the layer's elements
		List<TrafficDefinitionElement> layerElements = layer.getElements();

		// Create the trips for the elements
		for (TrafficDefinitionElement t : layerElements) {
			// Take into account only enabled elements
			if (t.isEnabled()) {
				if (t instanceof Flow) {
					tripsList.addAll(GenerateFlowTraffic((Flow) t));
				} else if (t instanceof AreaFlow) {
					tripsList.addAll(GenerateAreaFlowTraffic((AreaFlow) t, roadNetwork));
				} else if (t instanceof HotSpot) {
					tripsList.addAll(GenerateHotSpotTraffic((HotSpot) t, roadNetwork));
				} else if (t instanceof Accident) {
					accidentRoutesList.addAll(GenerateAccidentTraffic((Accident) t, roadNetwork));
				}
			}
		}
	}

	/**
	 * Method that generates traffic for a random traffic definition layer
	 * 
	 * @param roadNetwork
	 *            the road network for which to generate the traffic
	 * @param layer
	 *            the layer to generate traffic for
	 * @param tripsList
	 *            the List to place the generated trips in
	 * @throws Exception
	 */
	public static void GenerateRandomLayerTraffic(RoadNetwork roadNetwork, RandomTrafficDefinitionLayer layer, SUMOInstructionList<Trip> tripsList) throws Exception {
		Random r = RandomNumberProvider.getRandom();

		// Get all the edges in the road network
		List<Edge> allEdges = new ArrayList<Edge>();
		allEdges = roadNetwork.getEdges();
		int totalNetworkEdges = allEdges.size();

		String color = "1,0,0";

		// Simple validation
		if (totalNetworkEdges == 0) {
			return;
		}

		int emissionDuration = layer.getEndingTime() - layer.getStartingTime();
		int totalVehiclesToEmit = layer.getVehiclesPerSecond() * emissionDuration;

		// For each vehicle
		for (int i = 0; i < totalVehiclesToEmit; i++) {
			// Get the vehicle's type based on the user selection
			VehicleType vehicleType = layer.getVehicleSelection().getRandomType();

			// Get a random edge in the network
			Edge randomNetworkEdge1 = allEdges.get(r.nextInt(totalNetworkEdges));

			// Get a random edge in the network
			Edge randomNetworkEdge2 = allEdges.get(r.nextInt(totalNetworkEdges));

			// Get a random time between the emission starting and ending time
			int timeToLeave = layer.getStartingTime() + r.nextInt(layer.getEndingTime() + 1 - layer.getStartingTime());

			// Add the incoming vehicle trip to the output
			tripsList.add(new Trip(timeToLeave,"<trip id=\"" + layer.getName() + "-In-" + String.valueOf(i) + "\" depart=\"" + String.valueOf(timeToLeave) + "\" from=\"" + randomNetworkEdge1.getId() + "\" to=\"" + randomNetworkEdge2.getId() + "\" color=\"" + color + "\" type=\"" + vehicleType.getName() + "\" />\n"));
		}
	}

	/**
	 * Method that generates traffic for activity based layers
	 * 
	 * @param roadNetwork
	 *            the underlying road network
	 * @param projectJobTypes
	 *            the project's job types
	 * @param vehicleTypes
	 *            the layer's selected vehicle types
	 * @param activityBasedTrafficDefinitionElements
	 *            list of statistical and demographic elements (traffic areas
	 *            and schools)
	 * @param tripsList
	 * @param routesList
	 * @throws Exception
	 */
	public static void GenerateActivityBasedTraffic(RoadNetwork roadNetwork, List<JobType> projectJobTypes, TypeSelection<VehicleType> vehicleTypes, List<TrafficDefinitionElement> activityBasedTrafficDefinitionElements, SUMOInstructionList<Trip> tripsList, SUMOInstructionList<Route> routesList) throws Exception {
		// Sort the traffic areas and schools into separate lists
		List<TrafficArea> areas = new ArrayList<TrafficArea>();
		List<School> schools = new ArrayList<School>();

		for (TrafficDefinitionElement t : activityBasedTrafficDefinitionElements) {
			if (t instanceof TrafficArea) {
				areas.add((TrafficArea) t);
			} else if (t instanceof School) {
				schools.add((School) t);
			}
		}

		// Assign addresses to schools and initialize capacities
		for (School school : schools) {
			school.setAvailableCapacity(school.getCapacity());
			school.setClosestEdge(roadNetwork.findClosestEdge(school.getLocation()));
		}

		// For every job type find for every area the available work positions
		// and put them in a hashtable
		Hashtable<JobType, List<Pair<TrafficArea, Integer>>> workPositionsPerJobTypePerArea = new Hashtable<JobType, List<Pair<TrafficArea, Integer>>>();

		for (JobType projectJobType : projectJobTypes) {
			// Create a list that will hold the available work positions for
			// this job type per area
			List<Pair<TrafficArea, Integer>> areaPositions = new ArrayList<Pair<TrafficArea, Integer>>();

			// Loop through all areas
			for (TrafficArea area : areas) {
				// Get the possibility for this job and this area
				float areaJobPossibility = area.getWorkersJobSelection().getTypePossibility(projectJobType);

				// If the possibility for this job for this area is larger than
				// 0 then add the area to the list.
				if (areaJobPossibility > 0) {
					areaPositions.add(new Pair<TrafficArea, Integer>(area, Math.round(areaJobPossibility * area.getWorkPositions())));
				}
			}

			// Insert the list to the hashtable
			workPositionsPerJobTypePerArea.put(projectJobType, areaPositions);
		}

		// Variable used to assign house ids
		int houseID = 0;

		// Create virtual population for each traffic area
		for (TrafficArea area : areas) {
			// Number of people to create
			int unassignedPeople = area.getPopulation();

			// A list of the virtual houses that will be created at this area
			List<House> areaHouses = new ArrayList<House>();

			Random r = RandomNumberProvider.getRandom();

			// Create area houses
			while (unassignedPeople > 0) {
				House house = new House(area, houseID++);

				// Set the house's address
				Point2D.Double houseLocation = area.getRandomPointInArea();

				house.setAddress(roadNetwork.findClosestEdge(houseLocation));
				house.setLocation(houseLocation);

				// Create house residents
				// Number of adults and children is gaussian distribution
				// mith mean as specified by user and standard deviation 0.8
				int houseAdults = Math.round((float) (r.nextGaussian() * 0.8 + area.getAverageAdultsPerHouse()));

				if (houseAdults < 1) {
					houseAdults = 1;
				}

				int houseChildren = cern.jet.random.Poisson.staticNextInt(area.getAverageChildrenPerHouse());

				house.createAdults(houseAdults, area.getPossibilityAdultIsDriver(), area.getPossibilityAdultHasJob());
				house.createChildren(houseChildren);

				house.assignVehiclesToDrivers(area.getPossibilityDriverHasCar(), vehicleTypes);

				// Find closest school for every child in the house based on the
				// child's age and the school location and capacity
				for (Child child : house.getChildren()) {
					School closestAvailableSchool = FindClosestSchoolToLocation(schools, child.getSchoolType(), house.getLocation());
					child.setSchool(closestAvailableSchool);

					if (closestAvailableSchool != null) {
						closestAvailableSchool.setAvailableCapacity(closestAvailableSchool.getAvailableCapacity() - 1);
					}
				}

				// Add the house to the area's houses
				areaHouses.add(house);

				// Update helper variable
				unassignedPeople -= house.getNumberOfResidents();
			}

			// Loop through all houses and assign jobs to all
			// working adults
			for (House house : areaHouses) {

				// For each working adult in the house
				for (Adult adult : house.getWorkingAdults()) {

					// Get the adult's job based on the user's selection
					JobType job = area.getResidentsJobSelection().getRandomType();

					List<Pair<TrafficArea, Integer>> jobAreaPositions = workPositionsPerJobTypePerArea.get(job);

					// If we have areas with available work positions
					if (jobAreaPositions.size() > 0) {
						// Get a random area
						int i = r.nextInt(jobAreaPositions.size());

						// Get a random edge in the area
						Point2D.Double jobLocation = jobAreaPositions.get(i).getFirst().getRandomPointInArea();

						// Assign the job to the adult
						adult.setJobLocation(job, roadNetwork.findClosestEdge(jobLocation));

						// If this was the last job position of this kind in
						// this area
						if (jobAreaPositions.get(i).getSecond() == 1) {
							// Remove the area - it is full
							jobAreaPositions.remove(i);
						} else {
							// Decrease the available work positions
							jobAreaPositions.get(i).setSecond(jobAreaPositions.get(i).getSecond() - 1);
						}
					}
					// If no available work positions exist then the adult is
					// unemployed
					else {
						adult.setHasJob(false);
					}
				}
			}

			// Create activities for the people in each house
			for (House house : areaHouses) {
				System.out.println(area.getName() + " " + String.valueOf(house.getId()));
				house.createActivities(tripsList, routesList);
			}
		}
	}

	/**
	 * Generates traffic for a {@link HotSpot}
	 * 
	 * @param spot
	 *            the hotspot for which to generate traffic
	 * @param roadNetwork
	 *            the underlying road network
	 * @return list of generated trips
	 */
	private static SUMOInstructionList<Trip> GenerateHotSpotTraffic(HotSpot spot, RoadNetwork roadNetwork) {
		Random r = RandomNumberProvider.getRandom();

		SUMOInstructionList<Trip> trips = new SUMOInstructionList<Trip>();

		// Get the edges that lie within the hotspot
		List<Edge> hotspotEdges = new ArrayList<Edge>();
		hotspotEdges = roadNetwork.SelectEdges(spot.getArea());
		int totalHotspotEdges = hotspotEdges.size();

		// Get all the edges in the road network
		List<Edge> allEdges = new ArrayList<Edge>();
		allEdges = roadNetwork.getEdges();

		// Remove the hotspot edges
		for (Edge e : hotspotEdges) {
			if (allEdges.contains(e)) {
				allEdges.remove(e);
			}
		}

		int totalNetworkEdges = allEdges.size();

		String color = new Float(spot.getColor().getRed() / 255f).toString() + "," + new Float(spot.getColor().getGreen() / 255f).toString() + "," + new Float(spot.getColor().getBlue() / 255f).toString();

		// Simple validation
		if ((totalNetworkEdges == 0) || (totalHotspotEdges == 0)) {
			return null;
		}

		// For each vehicle
		for (int i = 0; i < spot.getNumberOfVehicles(); i++) {
			// Get the vehicle's type based on the user selection
			VehicleType vehicleType = spot.getVehicleSelection().getRandomType();

			// Get a random edge in the hotspot
			Edge randomHotspotEdge = hotspotEdges.get(r.nextInt(totalHotspotEdges));

			// Get a random edge in the network
			Edge randomNetworkEdge = allEdges.get(r.nextInt(totalNetworkEdges));

			// If the hotspot is incoming (vehicles from anywhere to the
			// hotspot)
			if (spot.isDirectionIn()) {
				int timeOfDepartureFromSource = 0;

				// If the time is specified as the time of departure from the
				// source
				if (spot.getDirectionInType() == HotSpot.DirectionInTimeType.TimeOfDepartureFromSource) {
					// Get a random time between the incoming begin time and end
					// time
					timeOfDepartureFromSource = spot.getDirectionInBeginTime() + r.nextInt(spot.getDirectionInEndTime() + 1 - spot.getDirectionInBeginTime());
				}
				// If the time is specified as the time of arrival to the
				// hotspot
				else if (spot.getDirectionInType() == HotSpot.DirectionInTimeType.TimeOfArrivalToDestination) {
					// Try to estimate the distance that the vehicle will need
					// to travel
					float distanceToTravel = randomHotspotEdge.DistanceFrom(randomNetworkEdge);

					// Estimate the time needed to cover this distance
					int estimatedTravelTime = (int) Math.round(distanceToTravel / Constants.averageSpeed);

					// Get a random time between the incoming begin time and end
					// time minus the time needed to travel
					timeOfDepartureFromSource = spot.getDirectionInBeginTime() + r.nextInt(spot.getDirectionInEndTime() + 1 - spot.getDirectionInBeginTime()) - estimatedTravelTime;

					// A vehicle can't start with a negative departure time
					timeOfDepartureFromSource = Math.min(timeOfDepartureFromSource, 0);
				}

				// Add the incoming vehicle trip to the output
				trips.add(new Trip(timeOfDepartureFromSource, "<trip id=\"" + spot.getName() + "-In-" + String.valueOf(i) + "\" depart=\"" + String.valueOf(timeOfDepartureFromSource) + "\" from=\"" + randomNetworkEdge.getId() + "\" to=\"" + randomHotspotEdge.getId() + "\" color=\"" + color + "\" type=\"" + vehicleType.getName() + "\" />\n"));
			}

			// If the hotspot is outgoing (vehicles from the hotspot to
			// anywhere)
			if (spot.isDirectionOut()) {
				// Get a random time between the outgoing begin time and end
				// time
				int timeToLeaveSpot = spot.getDirectionOutBeginTime() + r.nextInt(spot.getDirectionOutEndTime() + 1 - spot.getDirectionOutBeginTime());

				// Add the outgoing vehicle trip to the output
				trips.add(new Trip(timeToLeaveSpot, "<trip id=\"" + spot.getName() + "-Out-" + String.valueOf(i) + "\" depart=\"" + String.valueOf(timeToLeaveSpot) + "\" from=\"" + randomHotspotEdge.getId() + "\" to=\"" + randomNetworkEdge.getId() + "\" color=\"" + color + "\" type=\"" + vehicleType.getName() + "\" />\n"));
			}

		}

		return trips;
	}

	/**
	 * Generates traffic for a {@link Flow}
	 * 
	 * @param flow
	 *            the flow for which to generate traffic
	 * @return list of generated trips
	 */
	private static SUMOInstructionList<Trip> GenerateFlowTraffic(Flow flow) {
		Random r = RandomNumberProvider.getRandom();

		SUMOInstructionList<Trip> trips = new SUMOInstructionList<Trip>();

		// For each vehicle
		for (int i = 0; i < flow.getNumberOfVehicles(); i++) {

			// Get a random time between the begin time and end time
			int departureTime = flow.getBeginTime() + r.nextInt(flow.getEndTime() + 1 - flow.getBeginTime());

			String color = new Float(flow.getColor().getRed() / 255f).toString() + "," + new Float(flow.getColor().getGreen() / 255f).toString() + "," + new Float(flow.getColor().getBlue() / 255f).toString();

			// Create the trip
			trips.add(new Trip(departureTime, "<trip id=\"" + flow.getName() + "-" + String.valueOf(i) + "\" depart=\"" + String.valueOf(departureTime) + "\" from=\"" + flow.getStart().getId() + "\" to=\"" + flow.getEnd().getId() + "\" color=\"" + color + "\" type=\"" + flow.getVehicleSelection().getRandomType().getName() + "\" />\n"));
		}

		return trips;
	}

	/**
	 * Generates traffic for an {@link Accident}
	 * 
	 * @param accident
	 *            the accident for which to generate traffic
	 * @param roadNetwork
	 *            the road network
	 * @return list of generated routes
	 * @throws Exception
	 */
	private static SUMOInstructionList<Route> GenerateAccidentTraffic(Accident accident, RoadNetwork roadNetwork) throws Exception {
		// Random r = RandomNumberProvider.getRandom();

		// List<Edge> networkEdges = roadNetwork.getEdges();

		SUMOInstructionList<Route> routes = new SUMOInstructionList<Route>();

		// For each affected lane
		for (Pair<Lane, Boolean> affectedLane : accident.getAffectedLanes()) {
			if (affectedLane.getSecond()) {
				/*
				 * // Create a route for a vehicle starting at the edge that the //
				 * accident happens and ending at a random network edge passing //
				 * first through an edge connected to the lane List<Edge>
				 * routeEdges = new ArrayList<Edge>();
				 * 
				 * routeEdges.add(accident.getEdge());
				 * 
				 * if (affectedLane.getFirst().getConnectedEdges().size() > 0) {
				 * Edge connectedEdge =
				 * roadNetwork.findEdge(affectedLane.getFirst().getConnectedEdges().get(0));
				 * 
				 * if (connectedEdge != null) { routeEdges.add(connectedEdge); } }
				 * 
				 * routeEdges.add(networkEdges.get(r.nextInt(networkEdges.size()))); //
				 * Expand the route for the trip String route =
				 * MultipleTripExpander.ExpandTrips(routeEdges);
				 */

				// Create a route for a vehicle starting at the edge that the
				// accident happens and ending at an edge connected to the lane
				String route = accident.getEdge().getId();

				if (affectedLane.getFirst().getConnectedEdges().size() > 0) {
					Edge connectedEdge = roadNetwork.findEdge(affectedLane.getFirst().getConnectedEdges().get(0));

					if (connectedEdge != null) {
						route += " " + connectedEdge.getId();
					}
				}

				routes.add(new Route(accident.getStartingTime(), "<vehicle id=\"Accident-" + accident.getName() + "-" + affectedLane.getFirst().getId() + "\" type=\"Default\" depart=\"" + String.valueOf(accident.getStartingTime()) + "\" color=\"1,0,0\">\n" + "     <route color=\"1,0,0\">" + route + "</route>\n" + "     <stop lane=\"" + affectedLane.getFirst().getId() + "\" pos=\"" + accident.getEdgeRelativeLocation() + "\" duration=\"" + String.valueOf(accident.getEndingTime() - accident.getStartingTime()) + "\"/>\n" + "</vehicle>\n"));
			}
		}

		return routes;
	}

	/**
	 * Generates traffic for an {@link AreaFlow}
	 * 
	 * @param areaFlow
	 *            the area flow for which to generate traffic
	 * @param roadNetwork
	 *            the underlying road network
	 * @return list of generated trips
	 */
	private static SUMOInstructionList<Trip> GenerateAreaFlowTraffic(AreaFlow areaFlow, RoadNetwork roadNetwork) {
		Random r = RandomNumberProvider.getRandom();

		SUMOInstructionList<Trip> trips = new SUMOInstructionList<Trip>();

		// Get the edges belonging in the start area
		List<Edge> fromAreaEdges = new ArrayList<Edge>();
		fromAreaEdges = roadNetwork.SelectEdges(areaFlow.getStartArea());
		int totalFromAreaEdges = fromAreaEdges.size();

		// Get the edges belonging in the end area
		List<Edge> toAreaEdges = new ArrayList<Edge>();
		toAreaEdges = roadNetwork.SelectEdges(areaFlow.getEndArea());
		int totalToAreaEdges = toAreaEdges.size();

		// Simple validation
		if ((totalToAreaEdges == 0) || (totalFromAreaEdges == 0)) {
			return null;
		}

		// For each vehicle
		for (int i = 0; i < areaFlow.getNumberOfVehicles(); i++) {
			// Get a random source and destination edge
			Edge randomFromEdge = fromAreaEdges.get(r.nextInt(totalFromAreaEdges));
			Edge randomToEdge = toAreaEdges.get(r.nextInt(totalToAreaEdges));

			// Get a random time between the begin time and end time
			int departureTime = areaFlow.getBeginTime() + r.nextInt(areaFlow.getEndTime() + 1 - areaFlow.getBeginTime());

			String color = new Float(areaFlow.getColor().getRed() / 255f).toString() + "," + new Float(areaFlow.getColor().getGreen() / 255f).toString() + "," + new Float(areaFlow.getColor().getBlue() / 255f).toString();

			// Create the trip
			trips.add(new Trip(departureTime, "<trip id=\"" + areaFlow.getName() + "-" + String.valueOf(i) + "\" depart=\"" + String.valueOf(departureTime) + "\" from=\"" + randomFromEdge.getId() + "\" to=\"" + randomToEdge.getId() + "\" color=\"" + color + "\" type=\"" + areaFlow.getVehicleSelection().getRandomType().getName() + "\" />\n"));
		}

		return trips;
	}

	/**
	 * Finds the closest school of the specified type to a location
	 * 
	 * @param schools
	 *            the set of schools to search
	 * @param type
	 *            the type of the school to find
	 * @param location
	 *            the location to search near for
	 * @return the closest school near the specified location
	 */
	private static School FindClosestSchoolToLocation(List<School> schools, SchoolType type, Point2D.Double location) {
		School closestSchool = null;
		double minimumSchoolDistance = Float.MAX_VALUE;

		// Loop through all schools
		for (School school : schools) {
			if ((school.getSchoolType() == type) && (school.getAvailableCapacity() > 0)) {
				// Get the distance between the school and the location
				double schoolDistance = school.getLocation().distance(location);

				// If it is smaller than all previous distances
				if (schoolDistance < minimumSchoolDistance) {
					minimumSchoolDistance = schoolDistance;
					closestSchool = school;
				}
			}
		}

		return closestSchool;
	}
}
