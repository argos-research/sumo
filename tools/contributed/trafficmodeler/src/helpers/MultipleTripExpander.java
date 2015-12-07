package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import roadnetwork.Edge;
import simulation.Simulation;
import classes.Project;

/**
 * Helper class providing a method that joins multiple trips into one.
 * 
 */
public class MultipleTripExpander {

	/**
	 * Creates a complete route that passes through all the edges in the
	 * {@link tripEdges} list. SUMO has the limitation that it can create routes
	 * for a trip that defines the starting and ending edges only. This method
	 * calculates the routes for a trip that is defined by multiple edges.
	 * 
	 * @param tripEdges
	 *            A list of edges defining the route.
	 * @return a space separated list of the ids of the edges that define the
	 *         complete route
	 * @throws Exception
	 *             if anything goes wrong
	 */
	public static String ExpandTrips(List<Edge> tripEdges) throws Exception {
		Simulation sim = Project.getCurrentlyLoadedProject().getSimulation();

		String simulationPath = sim.getExportPath();
		String roadNetworkFile = sim.getName() + ".net.xml";

		File tripsToExpand = new File(simulationPath + File.separator + "tripstoexpand.tmp");
		File expandedTrips = new File(simulationPath + File.separator + "expandedtrips.tmp");
		File expandedTripsAlt = new File(simulationPath + File.separator + "expandedtrips.tmp.alt");

		// Create a temporary trip file
		BufferedWriter tripout = new BufferedWriter(new FileWriter(tripsToExpand));

		// Write the trips that are defined from the edges in the tripEdges
		// parameter.
		// Each consecutive pair of edges define a trip
		tripout.write("<trips>\n");

		for (int i = 1; i < tripEdges.size(); i++) {
			tripout.write("<trip " + "id=\"" + String.valueOf(i) + "\" " + "depart=\"" + String.valueOf(i) + "\" " + "from=\"" + tripEdges.get(i - 1).getId() + "\" " + "to=\"" + tripEdges.get(i).getId() + "\" />\n");
		}

		tripout.write("</trips>");

		tripout.close();

		// Run SUMO duarouter to find the routes for the trips
		List<String> args = new ArrayList<String>();

		args.add("\"" + ApplicationSettings.getDUARouterPath() + "\"");
		args.add("-t=\"tripstoexpand.tmp\"");
		args.add("-n=\"" + roadNetworkFile + "\"");
		args.add("-o=\"expandedtrips.tmp\"");
		args.add("--continue-on-unbuild");

		ProcessBuilder pb = new ProcessBuilder(args);
		pb.directory(new File(simulationPath));

		Process dua = pb.start();

		StreamGobbler gi = new StreamGobbler(dua.getInputStream());
		StreamGobbler ge = new StreamGobbler(dua.getErrorStream());

		gi.start();
		ge.start();

		int exitvalue = dua.waitFor();

		gi.stop();
		ge.stop();

		// Read the output from DUARouter
		List<String> gir = gi.getReadLines();
		List<String> ger = ge.getReadLines();

		for (String s : ger) {
			System.out.println(s);
		}

		// Read the generated routes and return the result
		List<String> allEdges = new ArrayList<String>();

		BufferedReader generatedRoutes = new BufferedReader(new FileReader(expandedTrips));
		String line = null;

		int routesFound = 0;

		// Read all lines
		while ((line = generatedRoutes.readLine()) != null) {
			// If the line is a route definition
			if (line.contains("<route>")) {
				// Get the edges of the route
				String[] routeEdges = line.substring(line.indexOf("<route>") + 7, line.indexOf("</route>")).split(" ");

				routesFound++;

				// If this is the first route then add all the edges to the
				// final list
				if (routesFound == 1) {
					for (int i = 0; i < routeEdges.length; i++) {
						allEdges.add(routeEdges[i]);
					}
				}
				// For all the rest routes add all but the first edges
				else {
					for (int i = 1; i < routeEdges.length; i++) {
						allEdges.add(routeEdges[i]);
					}
				}
			}
		}

		// Remove temporary files
		if (expandedTrips.exists()) {
			expandedTrips.delete();
		}

		if (expandedTripsAlt.exists()) {
			expandedTripsAlt.delete();
		}

		if (tripsToExpand.exists()) {
			tripsToExpand.delete();
		}

		// Connect all the edges into one string
		StringBuilder resultBuilder = new StringBuilder();

		for (String s : allEdges) {
			resultBuilder.append(s + " ");
		}

		return resultBuilder.toString();
	}
}
