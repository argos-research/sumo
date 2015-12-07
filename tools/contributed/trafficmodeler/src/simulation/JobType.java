package simulation;

import helpers.TimeConverter;
import interfaces.SelectableType;
import java.io.Serializable;

/**
 * Class representing a job type. The job type is characterized by the time starting and ending.
 */
public class JobType implements Serializable, SelectableType {
	private static final long serialVersionUID = -4158633543499860030L;

	/**
	 * The job type's name
	 */
	private String name;

	/**
	 * The job type's starting time
	 */
	private int startingTime;

	/**
	 * The job type's ending time
	 */
	private int endingTime;

	/**
	 * Creates the default job type 07:30-17:00
	 * @return
	 */
	public static JobType CreateDefaultType() {
		// Starts at 07:30, ends at 17:00
		return new JobType("Default", 27000, 61200);
	}

	/**
	 * Class constructor
	 * @param name
	 * @param startingTime
	 * @param endingTime
	 */
	public JobType(String name, int startingTime, int endingTime) {
		super();
		this.name = name;
		this.startingTime = startingTime;
		this.endingTime = endingTime;
	}

	/**
	 * @return  the endingTime
	 * @uml.property  name="endingTime"
	 */
	public int getEndingTime() {
		return endingTime;
	}

	/**
	 * @param endingTime  the endingTime to set
	 * @uml.property  name="endingTime"
	 */
	public void setEndingTime(int endingTime) {
		this.endingTime = endingTime;
	}

	/**
	 * @return  the startingTime
	 * @uml.property  name="startingTime"
	 */
	public int getStartingTime() {
		return startingTime;
	}

	/**
	 * @param startingTime  the startingTime to set
	 * @uml.property  name="startingTime"
	 */
	public void setStartingTime(int startingTime) {
		this.startingTime = startingTime;
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

	@Override
	public String toString() {
		return name + " (" + TimeConverter.toString(startingTime) + " - " + TimeConverter.toString(endingTime) + ")";
	}
}
