package ui;

import classes.TypeSelection;
import interfaces.SelectableType;
import javax.swing.table.AbstractTableModel;

/**
 * Custom table model that displays a list of objects and allows the user to set a possibility for each object
 * @param  <T >
 */
public class TypeSelectionTableModel<T extends SelectableType> extends AbstractTableModel {

	private static final long serialVersionUID = -6606926480332673298L;

	/**
	 * The selection being used
	 * @uml.property  name="selection"
	 * @uml.associationEnd  
	 */
	TypeSelection<T> selection;

	public int getColumnCount() {
		//We have 2 columns. The first column will show the type and the second its probability
		return 2;
	}

	public int getRowCount() {
		//We have as many rows as types in our selection
		return selection.size();
	}

	public String getColumnName(int col) {
		if (col == 0) {
			return "Type";
		} else {
			return "Percentage %";
		}
	}

	public TypeSelectionTableModel(TypeSelection<T> initialSelection) {
		super();

		selection = initialSelection;
	}

	public Object getValueAt(int row, int col) {
		//Return the value of a cell
		if ((col < 0) || (col > 1) || (row < 0) || (row > selection.size() - 1)) {
			return null;
		} else {
			if (col == 0) {
				return selection.get(row).getFirst();
			} else {
				//Display user - friendly numbers
				return Math.round(selection.get(row).getSecond() * 100);
			}
		}
	}

	public Class getColumnClass(int c) {
		//The first column shows the type's name
		if (c == 0) {
			return String.class;
		} 
		//The second its possibility
		else if (c == 1) {
			return Integer.class;
		} else {
			return null;
		}
	}

	public boolean isCellEditable(int row, int col) {
		//Only the possibility column is editable
		if (col == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void setValueAt(Object value, int row, int col) {
		//Set the value
		if (col == 1) {
			selection.get(row).setSecond((((Integer) value).floatValue()) / 100f);
		}

		fireTableCellUpdated(row, col);
	}
}