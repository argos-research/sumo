package helpers;

/**
 * Helper class that converts between seconds, hours and minutes
 * 
 */
public class TimeConverter {
	/**
	 * Returns the number of seconds after 00:00
	 * @param hours
	 * @param minutes
	 * @return
	 */
	public static int toSeconds(int hours,int minutes){
		return hours * 3600 + minutes * 60;
	}

	/**
	 * Returns a user friendly string in the format HH:MM
	 * @param seconds the number of seconds after 00:00
	 * @return
	 */
	public static String toString(int seconds){
		
		int hours = seconds / 3600;
		int minutes = (seconds % 3600)/60;
		
		String hourString = String.valueOf(hours);
		
		if(hours<10){
			hourString = "0"+hourString;
		}
		
		String minuteString = String.valueOf(minutes);
		
		if(minutes<10){
			minuteString = "0" + minuteString;
		}
		
		return hourString+":"+minuteString;
	}
}
