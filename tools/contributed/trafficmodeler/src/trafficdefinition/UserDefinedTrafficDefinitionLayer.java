package trafficdefinition;

public class UserDefinedTrafficDefinitionLayer extends TrafficDefinitionLayer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor used to create a new layer as a copy of another layer
	 * @param source the layer that will be used as the source layer
	 */
	public UserDefinedTrafficDefinitionLayer(UserDefinedTrafficDefinitionLayer source) {
		super(source);
	}

	/**
	 * Class constructor used to create a new layer.
	 *
	 */
	public UserDefinedTrafficDefinitionLayer() {
		super();
	}
	

	@Override
	public TrafficDefinitionLayer duplicate() {
		return new UserDefinedTrafficDefinitionLayer(this);
	}


	/* (non-Javadoc)
	 * @see trafficdefinition.TrafficDefinitionLayer#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "(User-defined layer)";
	}

	@Override
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbFlows = new StringBuilder();
		StringBuilder sbAreaFlows = new StringBuilder();
		StringBuilder sbHotspots = new StringBuilder();
		StringBuilder sbAccidents = new StringBuilder();		
		
		sb.append("<userDefinedTrafficLayer name=\""+name+"\" >\n");
		
		for(TrafficDefinitionElement tde:getElements()){
			if (tde instanceof Flow) {
				sbFlows.append(tde.toXML());
			}
			else if(tde instanceof HotSpot){
				sbHotspots.append(tde.toXML());
			}			
			else if(tde instanceof AreaFlow){
				sbAreaFlows.append(tde.toXML());
			}			
			else if(tde instanceof Accident){
				sbAccidents.append(tde.toXML());
			}
		}
		
		sb.append(sbFlows.toString());
		sb.append(sbAreaFlows.toString());
		sb.append(sbHotspots.toString());
		sb.append(sbAccidents.toString());
		
		sb.append("</userDefinedTrafficLayer>\n");
		
		return sb.toString();
	}
	
}
