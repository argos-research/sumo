package ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import trafficdefinition.TrafficDefinitionLayer;
import classes.Pair;

/**
 * Custom table model that displays a list of layers and allows the user to
 * select some layers using checkboxes
 * 
 */
public class ExportLayersTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -6606926480332673298L;

	/**
	 * The underlying table data
	 */
	List<Pair<TrafficDefinitionLayer,Boolean>> data;

	public int getColumnCount() {
		// We have 2 columns. The first column will show the checkbox and the second the layer name
		return 2;
	}

	public int getRowCount() {
		// We have as many rows as lanes
		return data.size();
	}

	public String getColumnName(int col) {
		if (col == 0) {
			return "Export";
		} else {
			return "Layer name";
		}
	}

	public ExportLayersTableModel(List<Pair<TrafficDefinitionLayer,Boolean>> data) {
		super();

		this.data = data;
	}

	public Object getValueAt(int row, int col) {
		// Return the value of a cell
		if ((col < 0) || (col > 1) || (row < 0) || (row > data.size() - 1)) {
			return null;
		} else {
			if (col == 0) {
				return data.get(row).getSecond();
			} else {
				return data.get(row).getFirst().getName();
			}
		}
	}

	public Class getColumnClass(int c) {
		// The first column shows the checkbox
		if (c == 0) {
			return Boolean.class;
		}
		// The second column shows the layer's name
		else if (c == 1) {
			return String.class;
		} else {
			return null;
		}
	}

	public boolean isCellEditable(int row, int col) {
		// Only the checkbox column is editable
		if (col == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void setValueAt(Object value, int row, int col) {
		// Set the value
		if (col == 0) {
			data.get(row).setSecond((Boolean) value);
		}

		fireTableCellUpdated(row, col);
	}
}