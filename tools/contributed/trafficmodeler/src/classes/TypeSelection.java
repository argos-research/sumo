package classes;

import helpers.RandomNumberProvider;
import interfaces.SelectableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generic class that represents a selection of objects, each with a percentage
 * assigned to it representing the possibility that the object will be selected.
 * 
 * @param <T>
 *            The type of the objects
 */
public class TypeSelection<T extends SelectableType> extends ArrayList<Pair<T, Float>> {

	private static final long serialVersionUID = -7955432732064500764L;

	/**
	 * Default constructor
	 * 
	 */
	public TypeSelection() {
		super();
	}

	/**
	 * Constructor that accept a list of objects that will be inserted in the
	 * selection. The percentage of each object is initialized to 0.
	 * 
	 * @param types
	 */
	public TypeSelection(List<T> types) {
		super();

		for (T o : types) {
			this.add(new Pair<T, Float>(o, 0f));
		}
	}

	/**
	 * Constructor that creates a new selection as a copy of the specified
	 * selection
	 * 
	 * @param source
	 */
	public TypeSelection(TypeSelection<T> source) {
		super();

		for (Pair<T, Float> p : source) {
			add(new Pair<T, Float>(p.getFirst(), p.getSecond()));
		}
	}

	/**
	 * Adds the objects in {@link selection} that are not already in the
	 * selection.
	 * 
	 * @param selection
	 */
	public void addTypesNotInSelection(TypeSelection<T> selection) {
		boolean found;

		// Loop through all the types in selection
		for (Pair<T, Float> pair : selection) {
			found = false;

			// Try to find the type in the current collection
			for (Pair<T, Float> p : this) {
				if (pair.getFirst().equals(p.getFirst())) {
					found = true;
				}
			}

			// If the object was not found then add it to the collection
			if (!found) {
				this.add(pair);
			}
		}
	}

	/**
	 * Returns a randomly selected type based on the possibility of each object.
	 * 
	 * @return
	 */
	public T getRandomType() {
		Random r = RandomNumberProvider.getRandom();

		float randomNumber = r.nextFloat();

		float lowerBound = 0.f;
		float upperBound = 0.f;

		for (Pair<T, Float> type : this) {
			upperBound = lowerBound + type.getSecond();
			if ((randomNumber >= lowerBound) && (randomNumber <= upperBound)) {
				return type.getFirst();
			} else {
				lowerBound += type.getSecond();
			}
		}

		return null;
	}

	/**
	 * Returns the sum of the percentages of all objects.
	 * 
	 * @return
	 */
	public float getTotalPercentage() {
		Float total = 0f;

		for (Pair<T, Float> pair : this) {
			total += pair.getSecond();
		}

		return total * 100;
	}

	/**
	 * Removes the objects whose percentage is zero.
	 * 
	 */
	public void removeTypesWithZeroPercentage() {
		TypeSelection<T> selectionsToRemove = new TypeSelection<T>();

		for (Pair<T, Float> p : this) {
			if (p.getSecond() == 0) {
				selectionsToRemove.add(p);
			}
		}

		removeAll(selectionsToRemove);
	}

	/**
	 * Returns the possibility of the specified type
	 * @param type the type to search for
	 * @return the type's possibility or 0 if the type does not belong in the selection
	 */
	public Float getTypePossibility(T type){
		for(Pair<T,Float> t:this){
			if(t.getFirst()==type){
				return t.getSecond();
			}
		}
		
		return 0f;
	}
}
