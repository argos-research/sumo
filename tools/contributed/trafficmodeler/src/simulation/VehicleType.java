package simulation;

import interfaces.SelectableType;
import java.io.Serializable;

/**
 * Class representing a vehicle type. The vehicle's properties affect the way the vehicle moves in the simulation
 */
public class VehicleType implements Serializable, SelectableType {
	private static final long serialVersionUID = 2721778251995318627L;

	/**
	 * The vehicle type's name
	 */
	private String name;

	/**
	 * The vehicle type's acceleration
	 */
	private float acceleration;

	/**
	 * The vehicle type's deceleration
	 */
	private float deceleration;

	/**
	 * The driver's imperfection
	 */
	private float driverImperfection;

	/**
	 * The vehicle's length
	 */
	private int length;

	/**
	 * The vehicle's maximum speed
	 */
	private int maximumSpeed;

	/**
	 * Creates the SUMO default vehicle type
	 * 
	 * @return
	 */
	public static VehicleType CreateDefaultType() {
		return new VehicleType("Default", 2.6f, 4.5f, 0.5f, 5, 70);
	}

	public VehicleType(String name, float acceleration, float deceleration, float driverImperfection, int length, int maximumSpeed) {
		super();
		this.name = name;
		this.acceleration = acceleration;
		this.deceleration = deceleration;
		this.driverImperfection = driverImperfection;
		this.length = length;
		this.maximumSpeed = maximumSpeed;
	}

	/**
	 * @return  the acceleration
	 * @uml.property  name="acceleration"
	 */
	public float getAcceleration() {
		return acceleration;
	}

	/**
	 * @param acceleration  the acceleration to set
	 * @uml.property  name="acceleration"
	 */
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * @return  the deceleration
	 * @uml.property  name="deceleration"
	 */
	public float getDeceleration() {
		return deceleration;
	}

	/**
	 * @param deceleration  the deceleration to set
	 * @uml.property  name="deceleration"
	 */
	public void setDeceleration(float deceleration) {
		this.deceleration = deceleration;
	}

	/**
	 * @return  the driverImperfection
	 * @uml.property  name="driverImperfection"
	 */
	public float getDriverImperfection() {
		return driverImperfection;
	}

	/**
	 * @param driverImperfection  the driverImperfection to set
	 * @uml.property  name="driverImperfection"
	 */
	public void setDriverImperfection(float driverImperfection) {
		this.driverImperfection = driverImperfection;
	}

	/**
	 * @return  the length
	 * @uml.property  name="length"
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length  the length to set
	 * @uml.property  name="length"
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return  the maximumSpeed
	 * @uml.property  name="maximumSpeed"
	 */
	public int getMaximumSpeed() {
		return maximumSpeed;
	}

	/**
	 * @param maximumSpeed  the maximumSpeed to set
	 * @uml.property  name="maximumSpeed"
	 */
	public void setMaximumSpeed(int maximumSpeed) {
		this.maximumSpeed = maximumSpeed;
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
		return name;
	}

	/**
	 * Returns the description of the vehicle type in SUMO xml format
	 * 
	 * @return
	 */
	public String toXML() {
		return "<vType " + "id=\"" + name + "\" " + "accel=\"" + String.valueOf(acceleration) + "\" " + "decel=\"" + String.valueOf(deceleration) + "\" " + "sigma=\"" + String.valueOf(driverImperfection) + "\" " + "length=\"" + String.valueOf(length) + "\" " + "maxSpeed=\"" + String.valueOf(maximumSpeed) + "\" " + "/>\n";
	}
}
