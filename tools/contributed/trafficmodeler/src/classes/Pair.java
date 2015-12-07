package classes;

import java.io.Serializable;

/**
 * Class representing a pair of objects
 * @param  < A  >  The first object's type
 * @param  < B  >  The second object's type
 */
public class Pair<A,B> implements Serializable, Cloneable{
	private static final long serialVersionUID = 4949525477981128023L;

	/**
	 * The first object
	 */
	private A first;
	
	/**
	 * The second object
	 */
	private B second;
	
	/**
	 * Default constructor
	 * @param first the first object to insert into the pair
	 * @param second the second object to insert into the pair
	 */
	public Pair(A first, B second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Returns the first object
	 * @return  the first object
	 * @uml.property  name="first"
	 */
	public A getFirst() {
		return first;
	}
	
	/**
	 * Returns the second object
	 * @return  the second object
	 * @uml.property  name="second"
	 */
	public B getSecond() {
		return second;
	}
		
	/**
	 * Sets the first object
	 * @param first  the first object to insert into the pair
	 * @uml.property  name="first"
	 */
	public void setFirst(A first) {
		this.first = first;
	}

	/**
	 * Sets the second object
	 * @param second  the second object to insert into the pair
	 * @uml.property  name="second"
	 */
	public void setSecond(B second) {
		this.second = second;
	}
	
	/**
	 * Clones the pair
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Pair<A,B>(first,second);
	}
	
	
}
