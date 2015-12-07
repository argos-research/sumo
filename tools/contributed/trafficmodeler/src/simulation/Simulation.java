package simulation;

import java.io.Serializable;

/**
 * Class representing a SUMO simulation
 */
public class Simulation implements Serializable {

	private static final long serialVersionUID = 3386857776323746836L;

	/**
	 * The path were the simulation will be exported to
	 */
	private String exportPath = "";
	
	/**
	 * The common name for all simulation files
	 */
	private String name = "sim";

	/**
	 * The simulation's begin time
	 */
	private int beginTime = 0;

	/**
	 * The simulation's end time
	 */
	private int endTime = 100;

	/**
	 * Flag to know whether to export polygons for the traffic definition
	 * elements that the user has selected to export polygons. The polygons will
	 * be exported to an additional file.
	 */
	private boolean exportPolygons = true;

	/**
	 * Net converter option roadNetworkSpeedInKMH
	 */
	private boolean roadNetworkSpeedInKMH = false;
	
	/**
	 * @return the roadNetworkSpeedInKMH
	 */
	public boolean isRoadNetworkSpeedInKMH() {
		return roadNetworkSpeedInKMH;
	}

	/**
	 * @param roadNetworkSpeedInKMH the roadNetworkSpeedInKMH to set
	 */
	public void setRoadNetworkSpeedInKMH(boolean roadNetworkSpeedInKMH) {
		this.roadNetworkSpeedInKMH = roadNetworkSpeedInKMH;
	}

	/**
	 * @return  the beginTime
	 * @uml.property  name="beginTime"
	 */
	public int getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime  the beginTime to set
	 * @uml.property  name="beginTime"
	 */
	public void setBeginTime(int beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return  the endTime
	 * @uml.property  name="endTime"
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime  the endTime to set
	 * @uml.property  name="endTime"
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return  the exportPath
	 * @uml.property  name="exportPath"
	 */
	public String getExportPath() {
		return exportPath;
	}

	/**
	 * @param exportPath  the exportPath to set
	 * @uml.property  name="exportPath"
	 */
	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	/**
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name  the name to set
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return  the exportPolygons
	 * @uml.property  name="exportPolygons"
	 */
	public boolean getExportPolygons() {
		return exportPolygons;
	}

	/**
	 * Sets whether there will be exported polygons for those traffic elements whose exportPolygon property is true
	 * @param exportPolygons  the exportPolygons to set
	 * @uml.property  name="exportPolygons"
	 */
	public void setExportPolygons(boolean exportPolygons) {
		this.exportPolygons = exportPolygons;
	}
}
