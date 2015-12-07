package simulation;

import java.util.ArrayList;

public class SUMOInstructionList<T extends SUMOInstruction> extends ArrayList<SUMOInstruction> {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(SUMOInstruction si :this){
			sb.append(si.getXmlDefinition());
		}
		
		return sb.toString();
	}

	
}
