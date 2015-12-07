package interfaces;

import classes.ProjectElement;
import enums.SelectionCount;

/**
 * Interface used by objects who want to be notified when the user changes the
 * selected elements on the current layer.
 * 
 */
public interface SelectionChangedListener {
	/**
	 * Event fired when the selection on the current layer has changed.
	 * @param count the number of selected items
	 * @param selectedItem the selected item if only one item is selected, null otherwise
	 */
	public void SelectionChanged(SelectionCount count, ProjectElement selectedItem);
}
