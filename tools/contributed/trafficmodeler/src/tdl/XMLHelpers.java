package tdl;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;

import simulation.JobType;
import simulation.VehicleType;
import classes.Pair;
import classes.TypeSelection;

/**
 * Helper class for TDL serialization
 *
 */
public class XMLHelpers {
	/**
	 * Converts a {@link Shape} to TDL. 
	 * @param s
	 * @return a string containing a space separated list of the points
	 * specifiying the shape
	 */
	public static String shapeToXML(Shape s){
		StringBuilder sb = new StringBuilder();
		
		PathIterator pi = s.getPathIterator(new AffineTransform());
		
		float[] coords = new float[6];

		while (!pi.isDone()) {
			pi.currentSegment(coords);

			sb.append(String.valueOf(coords[0]) + "," + String.valueOf(coords[1]));

			pi.next();

			if (!pi.isDone()) {
				sb.append(" ");
			}
		}

		return sb.toString();
	}
	
	public static String vehicleSelectionToXML(String elementName, TypeSelection<VehicleType> vehicleSelection){
		StringBuilder sb=new StringBuilder();
		
		sb.append("<"+elementName+">\n");
		
		for(Pair<VehicleType,Float> vs:vehicleSelection){
			sb.append("<selectedVehicle vehicleType=\""+ vs.getFirst().getName()+"\" probability=\""+vs.getSecond().toString()+"\"/>\n");			
		}
		
		sb.append("</"+elementName+">\n");

		return sb.toString();
	}
	
	public static String jobSelectionToXML(String elementName, TypeSelection<JobType> jobSelection){
		StringBuilder sb=new StringBuilder();
		
		sb.append("<"+elementName+">\n");
		
		for(Pair<JobType,Float> vs:jobSelection){
			sb.append("<selectedJob jobType=\""+ vs.getFirst().getName()+"\" probability=\""+vs.getSecond().toString()+"\"/>\n");			
		}
		
		sb.append("</"+elementName+">\n");

		return sb.toString();
	}
}
