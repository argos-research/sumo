package roadnetwork;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import shapes.Point2DExt;

/**
 * Class for parsing a SUMO .net.ml road network file
 * 
 */
public class RoadNetworkParser {

	/**
	 * Method that parses a SUMO road network xml file.
	 * 
	 * @param roadNetworkXMLDocument
	 *            The xml document containing the network
	 * @return The road network
	 * @throws Exception
	 *             if anything goes wrong
	 */
	public RoadNetwork ParseXMLNetFile(Document roadNetworkXMLDocument)
			throws Exception {
		// Create a new network
		RoadNetwork net = new RoadNetwork();

		Element docel = roadNetworkXMLDocument.getDocumentElement();

		// Get all edge elements
		NodeList edges = docel.getElementsByTagName("edge");

		// Parse the edge elements
		List<Edge> parsedEdges = ParseEdges(edges);

		// Parse the connections between the edges/lanes
		NodeList successors = docel.getElementsByTagName("succ");
		ParseDeprecatedSuccessors(successors, parsedEdges);
		NodeList successors2 = docel.getElementsByTagName("connection");
		ParseNewSuccessors(successors, parsedEdges);

		// Add the parsed edges to the network
		net.AddElements(parsedEdges);

		// Get all junction elements
		NodeList junctions = docel.getElementsByTagName("junction");

		// Parse the junction elements
		List<Junction> parsedJunctions = ParseJunctions(junctions);

		// Add the parsed junctions to the network
		net.AddElements(parsedJunctions);

		return net;
	}

	/**
	 * Method that creates {@link Edge} objects from the xml elements
	 * 
	 * @param xmlEdgeNodes
	 * @return a list of created edges
	 */
	private List<Edge> ParseEdges(NodeList xmlEdgeNodes) {
		List<Edge> parsedEdges = new ArrayList<Edge>();

		int totalEdges = xmlEdgeNodes.getLength();

		// Loop through all the edge xml elements
		for (int i = 0; i < totalEdges; i++) {
			Node edge = xmlEdgeNodes.item(i);

			// Get the xml attributes
			NamedNodeMap edgeAttributes = edge.getAttributes();

			// Continue only if this is a normal edge
			Node function = edgeAttributes.getNamedItem("function");

			if (function==null || function.getNodeValue().equals("normal")) {
				// Create a new edge
				Edge e = new Edge();

				// Set its properties
				e.setId(edgeAttributes.getNamedItem("id").getNodeValue());
				e.setFrom(edgeAttributes.getNamedItem("from").getNodeValue());
				e.setTo(edgeAttributes.getNamedItem("to").getNodeValue());
				Node prio = edgeAttributes.getNamedItem("priority");
				if(prio!=null) {
					e.setPriority(Integer.parseInt(prio.getNodeValue()));
				} else {
					e.setPriority(-1);
				}

				// Get the edge's lanes
				NodeList lanes = ((Element) edge).getElementsByTagName("lane");

				// Parse the lanes
				int totalLanes = lanes.getLength();

				for (int j = 0; j < totalLanes; j++) {
					Node lane = lanes.item(j);
					Lane l = new Lane();

					// Get the xml attributes
					NamedNodeMap laneAttributes = lane.getAttributes();
					if (j == 0) {
						e.setLength(Float.parseFloat(laneAttributes.getNamedItem("length").getNodeValue()));
						e.setSpeedLimit(Float.parseFloat(laneAttributes.getNamedItem("speed").getNodeValue()));
					}

					// Get the lane's id
					l.setId(laneAttributes.getNamedItem("id").getNodeValue());

					// Get the lane's coordinates
					List<Point2DExt> points = new ArrayList<Point2DExt>();

					String[] coords = laneAttributes.getNamedItem("shape").getNodeValue().split(" ");

					for (String coord : coords) {
						String[] from = coord.split(",");

						double fromx = Double.parseDouble(from[0]);
						double fromy = Double.parseDouble(from[1]);

						points.add(new Point2DExt(fromx, fromy));
					}

					l.SetShape(points);

					e.AddLane(l);
				}

				// Calculate the edge's shape
				e.CalculateShape();

				// Add the edge to the result list
				parsedEdges.add(e);
			}
		}

		return parsedEdges;
	}

	/**
	 * Reads in the connections between the lanes. It takes the list of succ
	 * elements of the SUMO net file and extracts the successors. These are
	 * filled in the list of each lane. The lanes are taken from the provides
	 * edge list.
	 * 
	 * @param succNodes
	 *            the XML nodes of type succ in the SUMO net file
	 * @param edges
	 *            the edges in which the lane connections are filled
	 */
	private void ParseDeprecatedSuccessors(NodeList succNodes, List<Edge> edges) {
		int numberSuccNodes = succNodes.getLength();

		for (int nodeIndex = 0; nodeIndex < numberSuccNodes; ++nodeIndex) {
			Node succNode = succNodes.item(nodeIndex);

			// Find the edge of which this node contains the successors
			NamedNodeMap succAttributes = succNode.getAttributes();
			String edgeId = succAttributes.getNamedItem("edge").getNodeValue();
			Edge edge = null;
			for (Edge e : edges) {
				if (e.getId().equals(edgeId)) {
					edge = e;
				}
			}
			if (edge == null) {
				continue;
			}

			// Find the lane of which this node contains the successors
			String laneId = succAttributes.getNamedItem("lane").getNodeValue();
			Lane lane = null;
			for (Lane l : edge.getLanes()) {
				if (l.getId().equals(laneId)) {
					lane = l;
				}
			}
			if (lane == null) {
				System.out.printf("Lane %s not found\n", laneId);
				continue;
			}

			// Fill in the successors
			NodeList childsOfSuccNode = succNode.getChildNodes();
			int numberOfChilds = childsOfSuccNode.getLength();
			for (int chidIndex = 0; chidIndex < numberOfChilds; ++chidIndex) {
				Node child = childsOfSuccNode.item(chidIndex);
				if (child.getNodeName().equals("succlane")) {
					// This is a node that contains successors
					NamedNodeMap childAttributes = child.getAttributes();
					String connectedLaneId = childAttributes.getNamedItem(
							"lane").getNodeValue();
					String connectedEdgeId = findEdgeIdToLaneId(connectedLaneId, edges);
					if (connectedEdgeId != null) {
						lane.AddConnectedEdge(connectedEdgeId);
					}
				}
			}
		}
	}
	
	private void ParseNewSuccessors(NodeList connNodes, List<Edge> edges) {
		int numberConnNodes = connNodes.getLength();

		for (int nodeIndex = 0; nodeIndex < numberConnNodes; ++nodeIndex) {
			Node succNode = connNodes.item(nodeIndex);

			// Find the edge of which this node contains the successors
			NamedNodeMap succAttributes = succNode.getAttributes();
			String fromId = succAttributes.getNamedItem("from").getNodeValue();
			Edge from = null;
			for (Edge e : edges) {
				if (e.getId().equals(fromId)) {
					from = e;
				}
			}
			if (from == null) {
				continue;
			}

			String toId = succAttributes.getNamedItem("to").getNodeValue();
			Edge to = null;
			for (Edge e : edges) {
				if (e.getId().equals(toId)) {
					to = e;
				}
			}
			if (to == null) {
				continue;
			}
			
			// Find the lane of which this node contains the successors
			String fromLaneId = succAttributes.getNamedItem("fromLane").getNodeValue();
			Lane fromLane = from.getLanes().get(Integer.parseInt(fromLaneId));
			fromLane.AddConnectedEdge(toId);
		}
	}	
	/** Returns the ID of the edge that contains the lane with the given ID.
	 * 
	 * @param laneId The ID of the lane to search for
	 * @param edges The list of edges
	 * @return the ID of the found edge or null, if not found
	 */
	private String findEdgeIdToLaneId(String laneId, List<Edge> edges) {
		for (Edge e : edges) {
			for (Lane l : e.getLanes()) {
				if (l.getId().equals(laneId)) {
					return e.getId();
				}
			}
		}
		return null;
	}

	/**
	 * Method that creates {@link Junction} objects from the xml elements
	 * 
	 * @param xmlJunctionNodes
	 * @return a list of created junctions
	 */
	private List<Junction> ParseJunctions(NodeList xmlJunctionNodes) {
		List<Junction> parsedJunctions = new ArrayList<Junction>();

		int totalJunctions = xmlJunctionNodes.getLength();

		// Loop through all the xml junction elements
		for (int i = 0; i < totalJunctions; i++) {
			Node junction = xmlJunctionNodes.item(i);

			// Get the junction's attributes
			NamedNodeMap junctionAttributes = junction.getAttributes();

			// Continue only if this is a normal junction
			Node function = junctionAttributes.getNamedItem("type");

			if (!function.getNodeValue().equals("internal")) {

				Junction j = new Junction();

				// Set the junction's properties
				j.setId(junctionAttributes.getNamedItem("id").getNodeValue());
				j.setType(junctionAttributes.getNamedItem("type").getNodeValue());

				String coordinates = junctionAttributes.getNamedItem("shape").getNodeValue();//((Element) junction).getElementsByTagName("shape").item(0).getTextContent();

				if (coordinates.equals("")) {
					j.SetShape(null);
				} else {
					String[] coords = coordinates.split(" ");
					List<Point2D.Float> points = new ArrayList<Point2D.Float>();

					if ((coords.length > 0) && (coords[0] != "")) {
						// Get the junction's shape points
						for (String coord : coords) {
							String[] from = coord.split(",");

							float fromx = Float.parseFloat(from[0]);
							float fromy = Float.parseFloat(from[1]);

							points.add(new Point2D.Float(fromx, fromy));
						}
					}

					j.SetShape(points);
				}

				parsedJunctions.add(j);
			}
		}

		return parsedJunctions;
	}
}
