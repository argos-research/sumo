package ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import roadnetwork.Lane;
import classes.Pair;

/**
 * Custom table model that displays a list of lanes and allows the user to
 * select some lanes using checkboxes
 * 
 */
public class AffectedLanesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -6606926480332673298L;

	/**
	 * The underlying table data
	 */
	List<Pair<Lane,Boolean>> data;

	public int getColumnCount() {
		// We have 2 columns. The first column will show the lane and the second
		// the checkbox
		return 2;
	}

	public int getRowCount() {
		// We have as many rows as lanes
		return data.size();
	}

	public String getColumnName(int col) {
		if (col == 0) {
			return "Lane";
		} else {
			return "Affected";
		}
	}

	public AffectedLanesTableModel(List<Pair<Lane,Boolean>> data) {
		super();

		this.data = data;
	}

	public Object getValueAt(int row, int col) {
		// Return the value of a cell
		if ((col < 0) || (col > 1) || (row < 0) || (row > data.size() - 1)) {
			return null;
		} else {
			if (col == 0) {
				return data.get(row).getFirst().getId();
			} else {
				return data.get(row).getSecond();
			}
		}
	}

	public Class getColumnClass(int c) {
		// The first column shows the lane's id
		if (c == 0) {
			return String.class;
		}
		// The second is a checkbox
		else if (c == 1) {
			return Boolean.class;
		} else {
			return null;
		}
	}

	public boolean isCellEditable(int row, int col) {
		// Only the speed limit column is editable
		if (col == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void setValueAt(Object value, int row, int col) {
		// Set the value
		if (col == 1) {
			data.get(row).setSecond((Boolean) value);
		}

		fireTableCellUpdated(row, col);
	}
}