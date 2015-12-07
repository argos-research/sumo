package simulation;

import java.util.Comparator;

public class CustomComparator implements Comparator<SUMOInstruction> {

	public int compare(SUMOInstruction arg0, SUMOInstruction arg1) {
		return arg0.getDepartureTime()-arg1.getDepartureTime();
	}

}
