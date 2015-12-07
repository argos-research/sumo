package trafficdefinition;

import classes.Layer;
import enums.SelectionType;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.List;

/**
 * Class representing a layer that contains  {@link TrafficDefinitionElement} s. Each layer is independent of the others. The user may choose which layers to export to the simulation. The final traffic will be the set of the traffic of each layer.
 */
public abstract class TrafficDefinitionLayer extends Layer<TrafficDefinitionElement> implements Serializable {

	/**
	 * Helper variable used to give new names to new layers
	 */
	private static int increment = 1;

	private static final long serialVersionUID = 1L;

	/**
	 * The layer's name
	 */
	protected String name = null;

	/**
	 * Class constructor used to create a new layer.
	 *
	 */
	public TrafficDefinitionLayer() {
		super();

		name = "New Layer " + String.valueOf(TrafficDefinitionLayer.increment);

		// Increment the name counter
		TrafficDefinitionLayer.increment++;
	}

	/**
	 * Class constructor used to create a new layer as a copy of another layer
	 * @param source the layer that will be used as the source layer
	 */
	public TrafficDefinitionLayer(TrafficDefinitionLayer source) {
		super();

		//Copy the traffic elements
		for(TrafficDefinitionElement e:source.elements){
			TrafficDefinitionElement clone = e.elementClone();
			clone.setLayer(this);
			
			elements.add(clone);
		}
		
		for(TrafficDefinitionElement e:source.selectedElements){
			TrafficDefinitionElement clone = e.elementClone();
			clone.setLayer(this);

			selectedElements.add(e.elementClone());
		}
		
		name = "Copy of " + source.getName();
	}
	
	/**
	 * Duplicates (clones) the current traffic definition layer
	 * @return
	 */
	public abstract TrafficDefinitionLayer duplicate();
	
	/**
	 * Deletes the specified element from the layer
	 * @param t the element to delete
	 */
	public void DeleteTrafficElement(TrafficDefinitionElement t){
		elements.remove(t);
	}
	
	/**
	 * Adds the specified element to the layer
	 * @param t
	 */
	public void AddTrafficElement(TrafficDefinitionElement t) {
		elements.add(t);

		t.setLayer(this);
		
		//Select the new element
		SelectElement(t);

		//Notify registered listeners
		NotifyCollectionModified();
		NotifySelectionChanged();
	}

	@Override
	public void AlterSelection(Rectangle transformedRectangle, SelectionType type) {

		switch (type) {
		case New:
			SelectNoneElements();
			//no break
		case Add:
			super.AddToSelection(transformedRectangle);

			break;
		case Remove:
			super.RemoveFromSelection(transformedRectangle);

			break;
		}

		NotifySelectionChanged();
	}

	@Override
	public void DeleteSelectedElements() {
		//Remove the dependencies for any flows that are going to be deleted
		for (TrafficDefinitionElement t : selectedElements) {
			if (t instanceof Flow) {
				((Flow) t).ReleaseDependencies();
			}
		}

		//Just clear the list
		selectedElements.clear();

		NotifyCollectionModified();

		NotifySelectionChanged();
	}

	@Override
	public void Draw(Graphics2D g, double zoomFactor) {
		// Draw traffic definition elements
		for (TrafficDefinitionElement e : elements) {
			e.Draw(g, zoomFactor, false, false);
		}

		// Draw selected traffic definition elements
		for (TrafficDefinitionElement e : selectedElements) {
			e.Draw(g, zoomFactor, false, true);
		}
	}

	/**
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}

	public List<TrafficDefinitionElement> getSelectedElements() {
		return selectedElements;
	}

	/**
	 * Checks whether the specified element is selected
	 * @param e
	 * @return
	 */
	public boolean isElementSelected(TrafficDefinitionElement e) {
		return selectedElements.contains(e);
	}

	@Override
	public void SelectAllElements() {
		super.SelectAllElements();

		NotifySelectionChanged();
	}

	@Override
	public void SelectInverseElements() {
		super.SelectInverseElements();

		NotifySelectionChanged();
	}

	@Override
	public void SelectNoneElements() {
		super.SelectNoneElements();

		NotifySelectionChanged();
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

	public abstract String toXML();
}
