package trafficdefinition;

public class ActivityBasedTrafficDefinitionLayer extends TrafficDefinitionLayer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor used to create a new layer as a copy of another layer
	 * @param source the layer that will be used as the source layer
	 */
	public ActivityBasedTrafficDefinitionLayer(ActivityBasedTrafficDefinitionLayer source) {
		super(source);
	}

	/**
	 * Class constructor used to create a new layer.
	 *
	 */
	public ActivityBasedTrafficDefinitionLayer() {
		super();
	}
	

	@Override
	public TrafficDefinitionLayer duplicate() {
		return new ActivityBasedTrafficDefinitionLayer(this);
	}

	/* (non-Javadoc)
	 * @see trafficdefinition.TrafficDefinitionLayer#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "(Activity-based layer)";
	}

	@Override
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbTrafficAreas = new StringBuilder();
		StringBuilder sbSchools = new StringBuilder();
		
		sb.append("<activityBasedTrafficLayer name=\""+name+"\">\n");
		
		for(TrafficDefinitionElement tde:getElements()){
			if (tde instanceof TrafficArea) {
				sbTrafficAreas.append(tde.toXML());
			}
			else if(tde instanceof School){
				sbSchools.append(tde.toXML());
			}			
		}
		
		sb.append("<trafficAreas>\n");
		sb.append(sbTrafficAreas.toString());
		sb.append("</trafficAreas>\n");
		
		sb.append("<schools>\n");
		sb.append(sbSchools.toString());
		sb.append("</schools>\n");
		
		sb.append("</activityBasedTrafficLayer>\n");
		
		return sb.toString();

	}
}
