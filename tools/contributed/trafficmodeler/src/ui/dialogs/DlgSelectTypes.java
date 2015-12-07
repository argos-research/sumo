package ui.dialogs;

import classes.TypeSelection;
import interfaces.SelectableType;
import java.awt.Cursor;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import ui.TypeSelectionTableModel;

/**
 * Dialog that enables the user to select possibilities for a selection of types. The sum of possibilities must be 100%.
 * @param  <T >
 */
public class DlgSelectTypes<T extends SelectableType> extends JDialog implements TableModelListener {

	private static final long serialVersionUID = 1L;

	/**
	 * The type selection to edit
	 * @uml.property  name="types"
	 * @uml.associationEnd  
	 */
	private TypeSelection<T> types;

	private int answer = JOptionPane.CANCEL_OPTION;

	private JPanel jContentPane = null;

	private JScrollPane scrlTypes = null;

	private JTable tblTypes = null;

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private JLabel lblAvailableTypes = null;

	private JLabel lblTotal = null;

	/**
	 * Default constructor
	 * 
	 * @param types
	 * @param owner
	 */
	public DlgSelectTypes(TypeSelection<T> types) {
		super();

		initialize();

		this.types = types;

		// Fill the table
		tblTypes.setModel(new TypeSelectionTableModel<T>(types));
		tblTypes.getModel().addTableModelListener(this);

		// Show the total percentage
		UpdateTotalPercentage();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(354, 217);
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.setDefaultCloseOperation(2);
		this.setModal(true);
		this.setResizable(false);
		this.setTitle("Select types");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * @return  javax.swing.JPanel
	 * @uml.property  name="jContentPane"
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblTotal = new JLabel();
			lblTotal.setBounds(new Rectangle(6, 163, 243, 19));
			lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotal.setText("");
			lblAvailableTypes = new JLabel();
			lblAvailableTypes.setBounds(new Rectangle(6, 4, 184, 17));
			lblAvailableTypes.setText("Available types:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getScrlTypes(), null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(lblAvailableTypes, null);
			jContentPane.add(lblTotal, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes scrlTypes
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrlTypes"
	 */
	private JScrollPane getScrlTypes() {
		if (scrlTypes == null) {
			scrlTypes = new JScrollPane();
			scrlTypes.setBounds(new Rectangle(7, 27, 243, 133));
			scrlTypes.setViewportView(getTblTypes());
		}
		return scrlTypes;
	}

	/**
	 * This method initializes tblTypes
	 * @return  javax.swing.JTable
	 * @uml.property  name="tblTypes"
	 */
	private JTable getTblTypes() {
		if (tblTypes == null) {
			tblTypes = new JTable();
		}
		return tblTypes;
	}

	/**
	 * This method initializes btnOK
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnOK"
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(261, 125, 80, 24));
			btnOK.setText("OK");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					OK();
				}
			});
		}
		return btnOK;
	}

	protected void OK() {
		answer = JOptionPane.OK_OPTION;
		setVisible(false);
	}

	/**
	 * This method initializes btnCancel
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCancel"
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(262, 157, 81, 21));
			btnCancel.setText("Cancel");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Cancel();
				}
			});
		}
		return btnCancel;
	}

	protected void Cancel() {
		answer = JOptionPane.CANCEL_OPTION;
		setVisible(false);
	}

	public void tableChanged(TableModelEvent e) {
		// If the user has modified the probability of a type then update the
		// total percentage
		UpdateTotalPercentage();
	}

	/**
	 * Calculates and displays the sum of the percentages of all types
	 * 
	 */
	private void UpdateTotalPercentage() {
		float totalPercentage = types.getTotalPercentage();
		lblTotal.setText("Remaining: " + String.valueOf(100 - totalPercentage) + " Total: " + String.valueOf(totalPercentage));

		// Don't enable the OK button unless the total percentage is 100%
		if (totalPercentage != 100) {
			btnOK.setEnabled(false);
		} else {
			btnOK.setEnabled(true);
		}
	}

	/**
	 * @return  the answer
	 * @uml.property  name="answer"
	 */
	public int getAnswer() {
		return answer;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
