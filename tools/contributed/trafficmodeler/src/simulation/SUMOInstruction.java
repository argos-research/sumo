package simulation;

/**
 * Class representing a SUMO instruction (trip or route)
 * 
 */
public class SUMOInstruction {

	private int departureTime;
	private String xmlDefinition;
	
	public SUMOInstruction(int departureTime, String xmlDefinition){
		this.departureTime = departureTime;
		this.xmlDefinition = xmlDefinition;
	}
	
	/**
	 * @return the departureTime
	 */
	public int getDepartureTime() {
		return departureTime;
	}

	/**
	 * @return the xmlDefinition
	 */
	public String getXmlDefinition() {
		return xmlDefinition;
	}
}