package roadnetwork;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import shapes.Point2DExt;

/**
 * Class representing a street lane
 */
public class Lane implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * The lane's id
	 */
	private String id;

	/**
	 * The list of points defining the lane's center geometry
	 */
	private List<Point2DExt> points;
	
	/**
	 * List of edges that are connected to this lane
	 */
	private List<String> connectedEdges = new ArrayList<String>();
	
	/**
	 * @return  the id
	 * @uml.property  name="id"
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the point with the specified index
	 * @param index
	 * @return
	 */
	public Point2D.Double GetPoint(int index) {
		return points.get(index);
	}

	/**
	 * Returns the total number of points that define the lane 
	 * @return
	 */
	public int GetTotalPoints() {
		return points.size();
	}

	/**
	 * @param id  the id to set
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the points of the lane
	 * @param shapepoints
	 */
	public void SetShape(List<Point2DExt> shapepoints) {
		points = shapepoints;
	}
	
	/**
	 * Adds the specified edge to the list of edges connected to this lane
	 * @param edgeID
	 */
	public void AddConnectedEdge(String edgeID){
		connectedEdges.add(edgeID);
	}

	/**
	 * Returns the list of edges that are connected to this lane
	 * @return
	 * @uml.property  name="connectedEdges"
	 */
	public List<String> getConnectedEdges(){
		return connectedEdges;
	}
}
