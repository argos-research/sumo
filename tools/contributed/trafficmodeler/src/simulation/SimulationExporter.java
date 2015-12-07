package simulation;

import helpers.ApplicationSettings;
import helpers.StreamGobbler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import roadnetwork.RoadNetwork;
import trafficdefinition.ActivityBasedTrafficDefinitionLayer;
import trafficdefinition.RandomTrafficDefinitionLayer;
import trafficdefinition.TrafficDefinitionElement;
import trafficdefinition.TrafficDefinitionLayer;
import trafficdefinition.UserDefinedTrafficDefinitionLayer;
import classes.Project;

/**
 * Class responsible for exporting the simulation
 * 
 */
public class SimulationExporter {
	/**
	 * Exports the specified road network to disk
	 * 
	 * @param net
	 *            the road network to export
	 * @param path
	 *            the folder to export the network to
	 * @param name
	 *            the name of the files to create
	 * @throws Exception
	 */
	private static void ExportNet(RoadNetwork net, String path, String name, boolean speedInKMH) throws Exception {
		// Delete the user-deleted elements from the documents and save the
		// .edg.xml and .nod.xml files to disk
		FileOutputStream edgefo = new FileOutputStream(path + File.separator + name + ".edg.xml");
		FileOutputStream nodefo = new FileOutputStream(path + File.separator + name + ".nod.xml");

		net.RemoveDeletedFromXMLDocuments(nodefo, edgefo);

		edgefo.close();
		nodefo.close();

		// Run SUMO netconvert utility to convert the files to a road network
        List<String> args = new ArrayList<String>();
        args.add(ApplicationSettings.getNetConvertPath());
        
        args.add("-n");
		args.add(name + ".nod.xml");
        args.add("-e");
		args.add(name + ".edg.xml");
        args.add("-o");
		args.add(name + ".net.xml");
		if(speedInKMH){
            args.add("--speed-in-kmh");
		}
		ProcessBuilder pb = new ProcessBuilder(args);
		pb.directory(new File(path));

		Process sumo = pb.start();

		StreamGobbler gi = new StreamGobbler(sumo.getInputStream());
		StreamGobbler ge = new StreamGobbler(sumo.getErrorStream());

		gi.start();
		ge.start();

		int exitvalue = sumo.waitFor();

		gi.stop();
		ge.stop();

		List<String> gir = gi.getReadLines();
		List<String> ger = ge.getReadLines();

		for (String s : ger) {
			System.out.println(s);
		}
	}

	/**
	 * Exports polygons to an additional file for traffic definition elements
	 * whose exportPolygon property is true
	 * 
	 * @param layersToExport
	 *            the list of layers to export
	 * @throws Exception
	 *             if anything goes wrong
	 */
	private static void ExportPolygons(List<TrafficDefinitionLayer> layersToExport, String path, String name) throws Exception {
		StringBuilder polygonBuilder = new StringBuilder();

		// Loop through all elements in all selected layers
		for (TrafficDefinitionLayer layer : layersToExport) {
			for (TrafficDefinitionElement element : layer.getElements()) {
				// If the user has requested that a polygon will be exported
				if (element.getExportPolygon() && element.isEnabled()) {
					polygonBuilder.append(element.getGeometryXML());
				}
			}
		}

		// Write the polygons to the file
		BufferedWriter geomout = new BufferedWriter(new FileWriter(path + File.separator + name + ".geometry.xml"));

		geomout.write("<polys>\n");

		geomout.write(polygonBuilder.toString());

		geomout.write("</polys>\n");

		geomout.close();
	}

	/**
	 * Exports the simulation to disk. This method exports the
	 * .edg.xml,.nod.xml,.net.xml,.rou.xml,.rou.alt.xml,.trips.xml and .sumo.cfg
	 * files
	 * 
	 * @param project
	 *            The project to export
	 * @param layersToExport
	 *            The project's layers to export
	 */
	public static void ExportSimulation(Project project, List<TrafficDefinitionLayer> layersToExport) {

		Simulation sim = project.getSimulation();

		// Empty the target folder
		if (JOptionPane.showConfirmDialog(null, "All files in the folder " + sim.getExportPath() + " will be deleted.\nAre you sure you want to continue?", "Confirm file deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			File exportFolder = new File(sim.getExportPath());

			for (File child : exportFolder.listFiles()) {
				if (!child.delete()) {
					JOptionPane.showMessageDialog(null, "Unable to empty simulation export folder. The operation was cancelled.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		} else {
			return;
		}

		// Try to export the road network
		try {
			ExportNet(project.getRoadNetwork(), sim.getExportPath(), sim.getName(), sim.isRoadNetworkSpeedInKMH());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while exporting road network", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}

		// Export polygons for traffic definition elements
		if (sim.getExportPolygons()) {
			try {
				ExportPolygons(layersToExport, sim.getExportPath(), sim.getName());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error while exporting element geometry", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return;
			}
		}

		// Create a list that will hold all the generated trips
		SUMOInstructionList<Trip> tripsList = new SUMOInstructionList<Trip>();

		// Create a string builder that will hold all the generated routes
		SUMOInstructionList<Route> routesList = new SUMOInstructionList<Route>();

		// Create a string builder that will hold all the generated routes for
		// accidents
		SUMOInstructionList<Route> accidentRoutesList = new SUMOInstructionList<Route>();

		try {
			// List of statistical traffic definition elements (areas and schools)
			List<TrafficDefinitionElement> statisticalDefinitionElements = new ArrayList<TrafficDefinitionElement>();

			// Loop through each selected traffic definition layer
			for (TrafficDefinitionLayer selectedLayer : layersToExport) {
				if(selectedLayer instanceof UserDefinedTrafficDefinitionLayer){
					TrafficGenerator.GenerateUserDefinedLayerTraffic(project.getRoadNetwork(), (UserDefinedTrafficDefinitionLayer) selectedLayer, tripsList, accidentRoutesList);
				}
				else if (selectedLayer instanceof RandomTrafficDefinitionLayer){
					TrafficGenerator.GenerateRandomLayerTraffic(project.getRoadNetwork(),(RandomTrafficDefinitionLayer)selectedLayer, tripsList);
				}
				else if(selectedLayer instanceof ActivityBasedTrafficDefinitionLayer){
					//Group together all enabled activity based elements
					for(TrafficDefinitionElement e:selectedLayer.getElements()){
						if(e.isEnabled()){
							statisticalDefinitionElements.add(e);
						}
					}
				}
			}

			// Generate traffic for statistical elements
			TrafficGenerator.GenerateActivityBasedTraffic(project.getRoadNetwork(), project.getJobTypes(), project.getActivityBasedTrafficVehicleSelection(), statisticalDefinitionElements, tripsList, routesList);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while creating traffic for selected layers");
			e.printStackTrace();
			return;
		}

		// Check to see if any trips or routes were generated
		if (tripsList.isEmpty() && routesList.isEmpty() && accidentRoutesList.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No trips or routes were generated");
		}

		//Sort the generated lists by vehicle departure time
		Collections.sort(tripsList, new CustomComparator());
		Collections.sort(routesList, new CustomComparator());
		Collections.sort(accidentRoutesList, new CustomComparator());
		
		// Write the trips to the trip file
		if (!tripsList.isEmpty()) {
			try {
				BufferedWriter tripout = new BufferedWriter(new FileWriter(sim.getExportPath() + File.separator + sim.getName() + ".generated-trips.xml"));

				tripout.write("<trips>\n");

				// Write the project's vehicle types
				for (VehicleType vehicleType : project.getVehicleTypes()) {
					tripout.write(vehicleType.toXML());
				}

				// Write the generated trips
				tripout.write(tripsList.toString());

				tripout.write("</trips>");

				tripout.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error while writing trip file", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return;
			}
		}

		// Write the routes to the route file
		if (!routesList.isEmpty()) {
			try {
				BufferedWriter routeout = new BufferedWriter(new FileWriter(sim.getExportPath() + File.separator + sim.getName() + ".generated-routes.xml"));

				routeout.write("<routes>\n");

				// Write the project's vehicle types
				for (VehicleType vehicleType : project.getVehicleTypes()) {
					routeout.write(vehicleType.toXML());
				}

				// Write the generated routes
				routeout.write(routesList.toString());

				routeout.write("</routes>");

				routeout.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error while writing route file", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return;
			}
		}

		// Write the accident routes to the additional route file
		if (!accidentRoutesList.isEmpty()) {
			try {
				BufferedWriter accidentRouteout = new BufferedWriter(new FileWriter(sim.getExportPath() + File.separator + sim.getName() + ".accidents.xml"));

				accidentRouteout.write("<routes>\n");

				// If the vehicle types were not already written in another file
				if (routesList.isEmpty() && tripsList.isEmpty()) {
					// Write the project's vehicle types
					for (VehicleType vehicleType : project.getVehicleTypes()) {
						accidentRouteout.write(vehicleType.toXML());
					}
				}

				// Write the generated routes
				accidentRouteout.write(accidentRoutesList.toString());

				accidentRouteout.write("</routes>");

				accidentRouteout.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error while writing accident routes file", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return;
			}
		}

		if ((!tripsList.isEmpty()) || (!routesList.isEmpty())) {
			// Run dua router utility to convert the trips to routes
			List<String> args = new ArrayList<String>();

			args.add(ApplicationSettings.getDUARouterPath());

			if (!tripsList.isEmpty()) {
				args.add("-t");
                args.add(sim.getName() + ".generated-trips.xml");
			}
			if (!routesList.isEmpty()) {
				args.add("-r");
				args.add(sim.getName() + ".generated-routes.xml");
			}
			args.add("-n");
			args.add(sim.getName() + ".net.xml");
			args.add("-o");
			args.add(sim.getName() + ".rou.xml");
			args.add("--ignore-errors");

			try {
				ProcessBuilder pb = new ProcessBuilder(args);
				pb.directory(new File(sim.getExportPath()));

				Process dua = pb.start();

				StreamGobbler gi = new StreamGobbler(dua.getInputStream());
				StreamGobbler ge = new StreamGobbler(dua.getErrorStream());

				gi.start();
				ge.start();

				int exitvalue = dua.waitFor();

				gi.stop();
				ge.stop();

				List<String> gir = gi.getReadLines();
				List<String> ger = ge.getReadLines();

				for (String s : ger) {
					System.out.println(s);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error while generating routes from trips", "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return;
			}
		}

		// Export the simulation configuration file
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(sim.getExportPath() + File.separator + sim.getName() + ".sumocfg"));

			StringBuilder cfgBuilder = new StringBuilder();

			cfgBuilder.append("<configuration>\n");
			cfgBuilder.append("<input-files>\n");
			cfgBuilder.append("<net-file>" + sim.getName() + ".net.xml</net-file>\n");

			if ((!tripsList.isEmpty()) || (!routesList.isEmpty()) || (!accidentRoutesList.isEmpty())) {

				if (((!tripsList.isEmpty()) || (!routesList.isEmpty())) && (accidentRoutesList.isEmpty())) {
					cfgBuilder.append("<route-files>");
					cfgBuilder.append(sim.getName() + ".rou.xml");
					cfgBuilder.append("</route-files>\n");
				} else if ((tripsList.isEmpty()&& routesList.isEmpty()) && (!accidentRoutesList.isEmpty())) {
					cfgBuilder.append("<route-files>");
					cfgBuilder.append(sim.getName() + ".accidents.xml");
					cfgBuilder.append("</route-files>\n");
				} else {
					cfgBuilder.append("<route-files>");
					cfgBuilder.append(sim.getName() + ".rou.xml" + ";" + sim.getName() + ".accidents.xml");
					cfgBuilder.append("</route-files>\n");
				}
			}

			if (sim.getExportPolygons()) {
				cfgBuilder.append("<additional-files>" + sim.getName() + ".geometry.xml</additional-files>\n");
			}

			cfgBuilder.append("</input-files>\n");
			cfgBuilder.append("<netstate-dump>" + sim.getName() + ".netdmp.xml</netstate-dump>\n");
			cfgBuilder.append("<simulation>\n");
			cfgBuilder.append("<begin>" + sim.getBeginTime() + "</begin>\n");
			cfgBuilder.append("<end>" + sim.getEndTime() + "</end>\n");
			cfgBuilder.append("</simulation>\n");
			cfgBuilder.append("</configuration>\n");

			out.write(cfgBuilder.toString());

			out.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while writing simulation file", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
	}

}
