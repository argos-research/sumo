package roadnetwork;

import classes.Layer;
import enums.SelectionType;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import shapes.Rectangle2DExt;
import trafficdefinition.TrafficDefinitionElement;

/**
 * Class representing a road network. The network consists of edges and
 * junctions.
 */
public class RoadNetwork extends Layer<RoadNetworkElement> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * List of elements that have been deleted by the user. They are placed here
	 * until the user permanently deletes them by using the 'Purge Deleted'
	 * option.
	 */
	private List<RoadNetworkElement> deletedMapElements;

	/**
	 * List containing the selected elements that have been deleted by the user.
	 */
	private List<RoadNetworkElement> selectedDeletedMapElements;

	/**
	 * String containing the contents of the *.edg.xml file. It is stored here
	 * to be used when creating a road network after the user has deleted some
	 * of its elements.
	 */
	private String roadNetworkEdgesXML = "";

	/**
	 * String containing the contents of the *.nod.xml file. It is stored here
	 * to be used when creating a road network after the user has deleted some
	 * of its elements.
	 */
	private String roadNetworkNodesXML = "";

	/**
	 * Flag to define whether the deleted elements should be drawn or not.
	 */
	private transient boolean showDeleted = false;

	/**
	 * The road network's bounds.
	 */
	Rectangle2DExt bounds = null;

	/**
	 * Default constructor.
	 * 
	 */
	public RoadNetwork() {
		super();

		deletedMapElements = new ArrayList<RoadNetworkElement>();
		selectedDeletedMapElements = new ArrayList<RoadNetworkElement>();
	}

	@Override
	public void AlterSelection(Rectangle transformedRectangle, SelectionType type) {

		switch (type) {
		// If we have a new selection clear the previous selection
		case New:
			SelectNoneElements();
			// No break!
		case Add:
			super.AddToSelection(transformedRectangle);

			// Select the deleted elements
			if (showDeleted) {
				List<RoadNetworkElement> newlySelectedElements = super.RectangleHitTest(transformedRectangle, deletedMapElements);

				if (newlySelectedElements.size() > 0) {
					deletedMapElements.removeAll(newlySelectedElements);
					selectedDeletedMapElements.addAll(newlySelectedElements);
				}
			}

			break;
		case Remove:
			super.RemoveFromSelection(transformedRectangle);

			// Deselect the deleted elements
			if (showDeleted) {
				List<RoadNetworkElement> newlyDeselectedElements = super.RectangleHitTest(transformedRectangle, selectedDeletedMapElements);

				if (newlyDeselectedElements.size() > 0) {
					deletedMapElements.addAll(newlyDeselectedElements);
					selectedDeletedMapElements.removeAll(newlyDeselectedElements);
				}
			}

			break;
		}

		// Notify any listeners that the selection has changed
		NotifySelectionChanged();
	}

	@Override
	public void DeleteSelectedElements() {
		List<Edge> edgesNeeded = new ArrayList<Edge>();
		List<Junction> junctionsNeeded = new ArrayList<Junction>();

		// Find in selection any edges that have dependent traffic elements
		// defined on them
		for (RoadNetworkElement e : selectedElements) {
			if (e instanceof Edge) {
				if (((Edge) e).HasDependentTrafficElements()) {
					edgesNeeded.add((Edge) e);
				}
			}
		}
		
		// For each edge, ask the user what to do
		for (Edge e : edgesNeeded) {
			String s = "";
		
			// Get the edge's dependencies
			List<TrafficDefinitionElement> edgeDependencies = e.getDependentTrafficElements();
		
			// Build a string with the edge's dependencies
			for(TrafficDefinitionElement tde: edgeDependencies){
				s+="Name: " + tde.getName()+" Layer: "+tde.getLayer().getName()+"\n";
			}			
			
			// Ask the user if she wants to delete the dependencies or not
			// delete the edge
			if(JOptionPane.showConfirmDialog(null, "You have selected to delete edge " + e.id + "\nThe following traffic elements depend on this edge:\n"+s+"Should the edge be deleted anyway?")==JOptionPane.YES_OPTION){
				// Delete dependencies
				for(TrafficDefinitionElement tde: edgeDependencies){
					e.RemoveDependentTrafficElement(tde);
					tde.getLayer().DeleteTrafficElement(tde);
				}
			}
			else{
				// Remove the edge from the selection
				elements.add(e);
				selectedElements.remove(e);
			}
		}

		// Find in selection any junctions that are needed
		// by edges that are left undeleted
		for (RoadNetworkElement j : selectedElements) {
			if (j instanceof Junction) {
				for (RoadNetworkElement e : elements) {
					if (e instanceof Edge) {
						Edge edge = (Edge) e;
						Junction junction = (Junction) j;
						if ((edge.getFrom().equals(junction.getId())) || (edge.getTo().equals(junction.getId()))) {
							junctionsNeeded.add(junction);
						}
					}
				}
			}
		}

		// Remove from selection the needed junctions
		for (Junction j : junctionsNeeded) {
			elements.add(j);
			selectedElements.remove(j);
		}

		deletedMapElements.addAll(selectedElements);

		selectedElements.clear();

		// Recalculate the road network's bounds
		ReCalculateBounds();

		// Notify any registered listeners that the road network has been
		// modified
		NotifyCollectionModified();

		// Notify any registered listeners that the selection has changed
		NotifySelectionChanged();
	}

	@Override
	public void Draw(Graphics2D g, double zoomFactor) {
		// Draw elements
		for (RoadNetworkElement e : elements) {
			try {
				e.Draw(g, zoomFactor, false, false);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// Draw selected elements
		for (RoadNetworkElement e : selectedElements) {
			e.Draw(g, zoomFactor, false, true);
		}

		// Draw deleted elements
		if (showDeleted) {
			for (RoadNetworkElement e : deletedMapElements) {
				e.Draw(g, zoomFactor, true, false);
			}

			// Draw selected deleted elements
			for (RoadNetworkElement e : selectedDeletedMapElements) {
				e.Draw(g, zoomFactor, true, true);
			}
		}
	}

	/**
	 * Finds the closest (non-deleted) edge to the specified point
	 * 
	 * @param point
	 * @return the closest edge to the specified point, or null if the network
	 *         has no edges
	 */
	public Edge findClosestEdge(Point2D.Double point) {
		Edge closestEdge = null;
		double minimumDistance = Float.MAX_VALUE;

		// Loop all edges in the road network
		for (Edge e : getEdges()) {
			double distance = e.DistanceFrom(point);

			if (distance < minimumDistance) {
				minimumDistance = distance;
				closestEdge = e;
			}
		}

		return closestEdge;
	}

	/**
	 * Returns the road network's bounds (minimum and maximum dimensions)
	 * 
	 * @return
	 * @uml.property name="bounds"
	 */
	public Rectangle2DExt getBounds() {
		if (bounds == null) {
			ReCalculateBounds();
		}

		return bounds;
	}

	/**
	 * Returns all the deleted elements (selected or not)
	 * 
	 * @return
	 */
	public List<RoadNetworkElement> getDeletedElements() {
		List<RoadNetworkElement> result = new ArrayList<RoadNetworkElement>();

		result.addAll(deletedMapElements);
		result.addAll(selectedDeletedMapElements);

		return result;
	}

	/**
	 * Returns all (non deleted) edges of the network
	 * 
	 * @return
	 */
	public List<Edge> getEdges() {
		List<Edge> result = new ArrayList<Edge>();

		for (RoadNetworkElement e : elements) {
			if (e instanceof Edge) {
				result.add((Edge) e);
			}
		}

		for (RoadNetworkElement e : selectedElements) {
			if (e instanceof Edge) {
				result.add((Edge) e);
			}
		}

		return result;
	}

	/**
	 * Returns all (non deleted) junctions of the network
	 * 
	 * @return
	 */
	public List<Junction> getJunctions() {
		List<Junction> result = new ArrayList<Junction>();

		for (RoadNetworkElement e : elements) {
			if (e instanceof Junction) {
				result.add((Junction) e);
			}
		}

		for (RoadNetworkElement e : selectedElements) {
			if (e instanceof Junction) {
				result.add((Junction) e);
			}
		}

		return result;
	}

	@Override
	public List<RoadNetworkElement> getElements() {
		List<RoadNetworkElement> result = super.getElements();

		if (showDeleted) {
			result.addAll(deletedMapElements);
			result.addAll(selectedDeletedMapElements);
		}

		return result;
	}

	/**
	 * @return the roadNetworkEdgesXML
	 * @uml.property name="roadNetworkEdgesXML"
	 */
	public String getRoadNetworkEdgesXML() {
		return roadNetworkEdgesXML;
	}

	/**
	 * @return the roadNetworkNodesXML
	 * @uml.property name="roadNetworkNodesXML"
	 */
	public String getRoadNetworkNodesXML() {
		return roadNetworkNodesXML;
	}

	@Override
	public void NotifySelectionChanged() {
		// If deleted elements are hidden then simply call the super class
		if (!showDeleted) {
			super.NotifySelectionChanged();
		}
		// Else take into account the selected deleted elements
		else {
			List<RoadNetworkElement> allSelectedElements = new ArrayList<RoadNetworkElement>();

			allSelectedElements.addAll(selectedElements);
			allSelectedElements.addAll(selectedDeletedMapElements);

			NotifySelectionChanged(allSelectedElements);
		}
	}

	/**
	 * Permanently deletes any deleted elements.
	 * 
	 */
	public void PurgeDeleted() {

		ByteArrayOutputStream edgesOutputStream = new ByteArrayOutputStream();
		ByteArrayOutputStream nodesOutputStream = new ByteArrayOutputStream();

		// Remove deleted elements from edg.xml and nod.xml documents
		try {
			RemoveDeletedFromXMLDocuments(nodesOutputStream, edgesOutputStream);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while purging deleted elements", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		roadNetworkEdgesXML = edgesOutputStream.toString();
		roadNetworkNodesXML = nodesOutputStream.toString();

		// Clear the deleted elements from the lists
		deletedMapElements.clear();
		selectedDeletedMapElements.clear();

		// Recalculate the network's bounds
		ReCalculateBounds();

		// Notify any registered listeners that the network is modified
		NotifyCollectionModified();

		// Notify any registered listeners that the selection has changed
		NotifySelectionChanged();
	}

	/**
	 * Alters the xml documents contained in roadNetworkEdgesXMLDocument and
	 * roadNetworkNodesXMLDocument to remove tags corresponding to elements that
	 * have been deleted and places the resulting xml documents to the output
	 * streams.
	 * 
	 * @param nodeOutputStream
	 *            Stream that will contain the edg.xml document after the
	 *            deleted elements have been removed
	 * @param edgeOutputStream
	 *            Stream that will contain the nod.xml document after the
	 *            deleted elements have been removed
	 * @throws Exception
	 *             if anything goes wrong
	 */
	public void RemoveDeletedFromXMLDocuments(OutputStream nodeOutputStream, OutputStream edgeOutputStream) throws Exception {
		// Parse the edges and nodes XML documents
		DocumentBuilder parser;

		Document roadNetworkEdgesXMLDocument = null, roadNetworkNodesXMLDocument = null;

		parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();

		roadNetworkEdgesXMLDocument = parser.parse(new InputSource(new StringReader(roadNetworkEdgesXML)));
		roadNetworkNodesXMLDocument = parser.parse(new InputSource(new StringReader(roadNetworkNodesXML)));

		// Delete the user-deleted elements from the documents
		List<RoadNetworkElement> deletedElements = getDeletedElements();

		Hashtable<String,String> deletedEdges = new Hashtable<String,String>();
		Hashtable<String,String> deletedJunctions = new Hashtable<String,String>();

		for (RoadNetworkElement e : deletedElements) {
			if (e instanceof Edge) {
				deletedEdges.put(e.getId(), "");
			}
			else if(e instanceof Node){
				deletedJunctions.put(e.getId(), "");
			}
		}

		NodeList edges = roadNetworkEdgesXMLDocument.getDocumentElement().getElementsByTagName("edge");
		NodeList junctions = roadNetworkNodesXMLDocument.getDocumentElement().getElementsByTagName("node");

		int edgesLength = edges.getLength();
		int junctionsLength = junctions.getLength();

		// Find the xml elements that need to be deleted from the documents
		List<Node> edgesToDelete = new ArrayList<Node>();
		List<Node> junctionsToDelete = new ArrayList<Node>();

		for (int i = 0; i < edgesLength; i++) {
			Node edge = edges.item(i);
			String edgeNodeID = edge.getAttributes().getNamedItem("id").getNodeValue();
			
			if(deletedEdges.containsKey(edgeNodeID)){
				edgesToDelete.add(edge);
			}
		}
	
		for (int i = 0; i < junctionsLength; i++) {
			Node junction = junctions.item(i);
			String junctionNodeID = junction.getAttributes().getNamedItem("id").getNodeValue();

			if(deletedJunctions.containsKey(junctionNodeID)){
				junctionsToDelete.add(junction);
			}
		}

		// Remove the elements from the xml documents
		for (Node n : edgesToDelete) {
			roadNetworkEdgesXMLDocument.getDocumentElement().removeChild(n);
		}

		for (Node j : junctionsToDelete) {
			roadNetworkNodesXMLDocument.getDocumentElement().removeChild(j);
		}

		// Save the modified XML documents to the output streams
		TransformerFactory tranFactory = TransformerFactory.newInstance();
		Transformer transformer = tranFactory.newTransformer();

		Source edgeSource = new DOMSource(roadNetworkEdgesXMLDocument);
		Result edgeDestination = new StreamResult(edgeOutputStream);

		Source junctionSource = new DOMSource(roadNetworkNodesXMLDocument);
		Result junctionDestination = new StreamResult(nodeOutputStream);

		transformer.transform(edgeSource, edgeDestination);
		transformer.transform(junctionSource, junctionDestination);
	}

	@Override
	public void SelectAllElements() {
		super.SelectAllElements();

		// Also select the deleted elements if they are not hidden
		if (showDeleted) {
			selectedDeletedMapElements.addAll(deletedMapElements);
			deletedMapElements.clear();
		}

		NotifySelectionChanged();
	}

	/**
	 * Finds all (non-deleted) edges that intersect the given shape.
	 * 
	 * @param s
	 * @return list of edges that intersect the specified shape
	 */
	public List<Edge> SelectEdges(Shape s) {
		List<Edge> hitEdges = new ArrayList<Edge>();

		for (Edge e : getEdges()) {
			if (e.Overlaps(s)) {
				hitEdges.add(e);
			}
		}

		return hitEdges;
	}

	@Override
	public boolean SelectElement(RoadNetworkElement element) {
		if (super.SelectElement(element)) {
			return true;
		} else {
			// TODO is there a bug here should be done only on show deleted?
			deletedMapElements.addAll(selectedDeletedMapElements);
			selectedDeletedMapElements.clear();

			if (deletedMapElements.contains(element)) {
				selectedDeletedMapElements.add((RoadNetworkElement) element);
				deletedMapElements.remove(element);

				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public void SelectInverseElements() {
		super.SelectInverseElements();

		if (showDeleted) {
			List<RoadNetworkElement> temp = new ArrayList<RoadNetworkElement>();

			temp.addAll(deletedMapElements);

			deletedMapElements.clear();
			deletedMapElements.addAll(selectedDeletedMapElements);

			selectedDeletedMapElements.clear();
			selectedDeletedMapElements.addAll(temp);
		}

		NotifySelectionChanged();
	}

	@Override
	public void SelectNoneElements() {
		super.SelectNoneElements();

		deletedMapElements.addAll(selectedDeletedMapElements);
		selectedDeletedMapElements.clear();

		NotifySelectionChanged();
	}

	/**
	 * @param roadNetworkEdgesXML
	 *            the roadNetworkEdgesXML to set
	 * @uml.property name="roadNetworkEdgesXML"
	 */
	public void setRoadNetworkEdgesXML(String roadNetworkEdgesXML) {
		this.roadNetworkEdgesXML = roadNetworkEdgesXML;
	}

	/**
	 * @param roadNetworkNodesXML
	 *            the roadNetworkNodesXML to set
	 * @uml.property name="roadNetworkNodesXML"
	 */
	public void setRoadNetworkNodesXML(String roadNetworkNodesXML) {
		this.roadNetworkNodesXML = roadNetworkNodesXML;
	}

	/**
	 * @param showDeleted
	 *            the showDeleted to set
	 * @uml.property name="showDeleted"
	 */
	public void setShowDeleted(boolean showDeleted) {
		this.showDeleted = showDeleted;

		ReCalculateBounds();

		// Clear the selection
		if (!showDeleted) {
			deletedMapElements.addAll(selectedDeletedMapElements);
			selectedDeletedMapElements.clear();
		}

		NotifyCollectionModified();
		NotifySelectionChanged();
	}

	@Override
	public String toString() {
		return "Road Network";
	}

	/**
	 * Searches the road network to find a non-deleted edge with the specified
	 * id.
	 * 
	 * @param edgeID
	 * @return The edge with the specified ID, or null if not found
	 */
	public Edge findEdge(String edgeID) {
		for (Edge e : getEdges()) {
			if (e.getId().equals(edgeID)) {
				return e;
			}
		}

		return null;
	}

	/**
	 * Searches the road network to find a non-deleted junction with the
	 * specified id.
	 * 
	 * @param junctionID
	 * @return The junction with the specified ID, or null if not found
	 */
	public Junction findJunction(String junctionID) {
		for (Junction j : getJunctions()) {
			if (j.getId().equals(junctionID)) {
				return j;
			}
		}

		return null;
	}

	/**
	 * Undeletes the selected deleted elements. If an edge is being undeleted,
	 * its starting anfd ending junctions are also undeleted.
	 * 
	 */
	public void UndeleteSelectedMapElements() {
		// Junctions that need to be undeleted
		List<Junction> junctionsNeeded = new ArrayList<Junction>();

		// Find the junctions that are needed by edges that have been deleted
		for (RoadNetworkElement r : selectedDeletedMapElements) {
			if (r instanceof Edge) {
				Edge edge = (Edge) r;

				for (RoadNetworkElement j : deletedMapElements) {
					if (j instanceof Junction) {
						Junction junction = (Junction) j;
						if ((edge.getFrom().equals(junction.getId())) || (edge.getTo().equals(junction.getId()))) {
							junctionsNeeded.add(junction);
						}
					}
				}
			}
		}

		// Undelete the needed junctions
		for (Junction j : junctionsNeeded) {
			selectedElements.add(j);
			deletedMapElements.remove(j);
		}

		// Undelete selected deleted elements
		selectedElements.addAll(selectedDeletedMapElements);

		selectedDeletedMapElements.clear();

		ReCalculateBounds();

		NotifyCollectionModified();

		NotifySelectionChanged();
	}

	/**
	 * Recalculates the road network bounds. This method is called whenever the
	 * user deletes or undeletes some elements, or shows or hides the deleted
	 * elements.
	 * 
	 */
	private void ReCalculateBounds() {
		// Get all the visible elements
		List<RoadNetworkElement> allElements = getElements();

		// Find the bounds of all elements
		bounds = allElements.get(0).getBounds();

		for (RoadNetworkElement e : allElements) {
			bounds.add(e.getBounds());
		}

		// Add a border of 10% around the network
		double maxdim = Math.max(bounds.width, bounds.height);
		double widthenlargement = maxdim * 0.1;

		bounds.x -= maxdim * 0.05;
		bounds.y -= maxdim * 0.05;

		bounds.width += widthenlargement;
		bounds.height += widthenlargement;
	}
}