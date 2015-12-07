package classes;

import enums.SelectionCount;
import enums.SelectionType;
import interfaces.DrawableLayer;
import interfaces.ModifiedCollectionListener;
import interfaces.SelectionChangedListener;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a layer of elements that supports selection, modification and other features
 */
public abstract class Layer<E extends ProjectElement> implements Serializable, DrawableLayer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3687220504361334150L;

	/**
	 * List of listeners registered to the collection modified event.
	 */
	private transient List<ModifiedCollectionListener> modifiedCollectionListeners = null;

	/**
	 * List of listeners registered to the selection changed event.
	 */
	private transient List<SelectionChangedListener> selectionChangedListeners = null;

	/**
	 * List of elements contained in the layer that are not currently selected.
	 */
	protected final List<E> elements;

	/**
	 * List of elements contained in the layer that are currently selected.
	 */
	protected final List<E> selectedElements;

	/**
	 * Default constructor. Instantiates internal lists.
	 */
	public Layer() {
		elements = new ArrayList<E>();
		selectedElements = new ArrayList<E>();
	}

	/**
	 * Adds a ModifiedCollectionListener to the listener list.
	 * 
	 * @param listener
	 *            The ModifiedCollectionListener to be added
	 */
	public final void addModifiedCollectionListener(ModifiedCollectionListener listener) {
		if (modifiedCollectionListeners == null) {
			modifiedCollectionListeners = new ArrayList<ModifiedCollectionListener>();
		}

		modifiedCollectionListeners.add(listener);
	}

	/**
	 * Removes a ModifiedCollectionListener from the listener list.
	 * 
	 * @param listener
	 *            The ModifiedCollectionListener to be removed
	 */
	public final void removeModifiedCollectionListener(ModifiedCollectionListener listener) {
		if ((modifiedCollectionListeners != null) && (modifiedCollectionListeners.contains(listener))) {
			modifiedCollectionListeners.remove(listener);
		}
	}

	/**
	 * Notifies all registered ModifiedCollectionListeners that the collection
	 * has been modified
	 */
	protected final void NotifyCollectionModified() {
		for (ModifiedCollectionListener m : modifiedCollectionListeners) {
			m.CollectionModified();
		}
	}

	/**
	 * Adds a SelectionChangedListener to the listener list.
	 * 
	 * @param listener
	 *            The SelectionChangedListener to be added
	 */
	public final void addSelectionChangedListener(SelectionChangedListener listener) {
		if (selectionChangedListeners == null) {
			selectionChangedListeners = new ArrayList<SelectionChangedListener>();
		}

		selectionChangedListeners.add(listener);
	}

	/**
	 * Removes a SelectionChangedListener from the listener list.
	 * 
	 * @param listener
	 *            The SelectionChangedListener to be removed
	 */
	public final void removeSelectionChangedListener(SelectionChangedListener listener) {
		if ((selectionChangedListeners != null) && (selectionChangedListeners.contains(listener))) {
			selectionChangedListeners.remove(listener);
		}
	}

	/**
	 * Notifies all registered SelectionChangedListener that the selection has
	 * changed. Gets the selected items as a parameter.
	 * 
	 * @param selectedItems
	 *            The list of selected elements
	 */
	protected final void NotifySelectionChanged(List<? extends ProjectElement> selectedItems) {
		if (selectionChangedListeners == null) {
			return;
		}

		for (SelectionChangedListener selectionChangedListener : selectionChangedListeners) {
			// Notify each listener. Send the selected object only if one object
			// is selected.
			switch (selectedItems.size()) {
			case 0:
				selectionChangedListener.SelectionChanged(SelectionCount.None, null);
				break;
			case 1:
				selectionChangedListener.SelectionChanged(SelectionCount.Single, selectedItems.get(0));
				break;
			default:
				selectionChangedListener.SelectionChanged(SelectionCount.Multiple, null);
				break;
			}
		}
	}

	/**
	 * Notifies all registered SelectionChangedListener that the selection has
	 * changed. Uses the internal selected items list.
	 */
	protected void NotifySelectionChanged() {
		NotifySelectionChanged(selectedElements);
	}

	/**
	 * Returns all the elements contained in the collection.
	 * @return  list containing all of the collection's elements
	 * @uml.property  name="elements"
	 */
	public List<E> getElements() {
		List<E> result = new ArrayList<E>();

		result.addAll(elements);
		result.addAll(selectedElements);

		return result;
	}

	/**
	 * Selects the specified element in the collection. Clears the selection and
	 * selects only the specified element.
	 * 
	 * @param element
	 *            the element to select
	 * @return true if the element was found in the layer, false otherwise.
	 */
	public boolean SelectElement(E element) {
		elements.addAll(selectedElements);
		selectedElements.clear();

		if (elements.contains(element)) {
			selectedElements.add(element);
			elements.remove(element);

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Deselects all selected elements
	 */
	public void SelectNoneElements() {
		elements.addAll(selectedElements);
		selectedElements.clear();
	}

	/**
	 * Inverts the selection. Any previously selected elements are deselected.
	 * Any previously deselected elements are selected.
	 */
	public void SelectInverseElements() {
		List<E> temp = new ArrayList<E>();

		temp.addAll(elements);

		elements.clear();
		elements.addAll(selectedElements);

		selectedElements.clear();
		selectedElements.addAll(temp);
	}

	/**
	 * Selects all elements.
	 */
	public void SelectAllElements() {
		selectedElements.addAll(elements);
		elements.clear();
	}

	/**
	 * Adds the specified elements to the layer.
	 * 
	 * @param elementsToAdd
	 *            The list of elements to add to the layer
	 */
	public void AddElements(List<? extends E> elementsToAdd) {
		for (E element : elementsToAdd) {
			elements.add(element);
		}
	}

	/**
	 * Returns the first element containing the specified point.
	 * 
	 * @param p
	 *            The point
	 * @return An element containing the specified point, or null if no element
	 *         contains the specified point.
	 */
	public final E PointHitTest(Point2D p) {
		Point tp = new Point((int)Math.round(p.getX()), (int) Math.round(p.getY()));

		for (E e : getElements()) {
			if (e.Hit(tp)) {
				return e;
			}
		}

		return null;
	}

	/**
	 * Returns the subset of the specified elements intersecting the specified
	 * rectangle.
	 * 
	 * @param r
	 *            The rectangle to be checked for intersection.
	 * @param elementList
	 *            The list of elements to be checked.
	 * @return A list of elements intersecting the specified rectangle.
	 */
	protected List<E> RectangleHitTest(Rectangle r, List<E> elementList) {
		List<E> hitElements = new ArrayList<E>();

		for (E e : elementList) {
			if (e.Hit(r)) {
				hitElements.add(e);
			}
		}

		return hitElements;
	}

	/**
	 * Deletes the currently selected elements.
	 */
	public abstract void DeleteSelectedElements();

	/* (non-Javadoc)
	 * @see classes.Drawable#Draw(java.awt.Graphics2D, double)
	 */
	public abstract void Draw(Graphics2D g, double zoomFactor);

	/**
	 * Deselects any previously selected elements that intersect the specified
	 * rectangle.
	 * 
	 * @param r
	 *            The rectangle used for checking.
	 */
	protected final void RemoveFromSelection(Rectangle r) {
		// Find elements to deselect
		List<E> newlyDeselectedElements = RectangleHitTest(r, selectedElements);

		// Deselect elements
		if (newlyDeselectedElements.size() > 0) {
			elements.addAll(newlyDeselectedElements);
			selectedElements.removeAll(newlyDeselectedElements);
		}
	}

	/**
	 * Selects any previously deselected elements that intersect with the
	 * specified rectangle.
	 * 
	 * @param r
	 *            The rectangle used for checking.
	 */
	protected final void AddToSelection(Rectangle r) {
		List<E> newlySelectedElements = RectangleHitTest(r, elements);

		if (newlySelectedElements.size() > 0) {
			elements.removeAll(newlySelectedElements);
			selectedElements.addAll(newlySelectedElements);
		}
	}

	/**
	 * Alters the current selection based on the specified rectangle and
	 * selection type.
	 * 
	 * @param r
	 *            The rectangle used for checking
	 * @param type
	 *            The selection type.
	 */
	public abstract void AlterSelection(Rectangle r, SelectionType type);

	/**
	 * Writes the layer to disk. Overriden to clear the selection before the
	 * layer is serialized
	 * 
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		SelectNoneElements();

		out.defaultWriteObject();
	}


}
