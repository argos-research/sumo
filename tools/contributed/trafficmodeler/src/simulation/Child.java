package simulation;

import enums.SchoolType;
import helpers.RandomNumberProvider;
import java.util.Random;
import trafficdefinition.School;

/**
 * Class representing a child. The child goes to a school depending on its age
 */
public class Child extends Person {
	/**
	 * THe child's age
	 */
	private int age;

	/**
	 * The child's school
	 */
	private School school;

	public Child() {
		super();

		Random r = RandomNumberProvider.getRandom();

		//Assign random age below 18
		age = r.nextInt(18);
	}

	/**
	 * @return  the school
	 * @uml.property  name="school"
	 */
	public School getSchool() {
		return school;
	}

	/**
	 * @param school  the school to set
	 * @uml.property  name="school"
	 */
	public void setSchool(School school) {
		this.school = school;
	}

	/**
	 * Returns the child's school type based on its age
	 * @return
	 */
	public SchoolType getSchoolType() {
		if (age <= 5) {
			return SchoolType.Kindergarden;
		} else if ((age >= 6) && (age <= 12)) {
			return SchoolType.Elementary;
		} else if ((age >= 13) && (age <= 15)) {
			return SchoolType.Middle;
		} else if ((age >= 16) && (age <= 18)) {
			return SchoolType.High;
		}

		return SchoolType.Kindergarden;
	}
}
