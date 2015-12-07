package ui.propertypanels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import trafficdefinition.Accident;
import ui.AffectedLanesTableModel;
import ui.controls.SimTimeEdit;

/**
 * @author  PapaleonLe01
 */
public class PnlAccidentProperties extends JPanel {

	private static final long serialVersionUID = 1L;

	private Accident currentAccident = null; // @jve:decl-index=0:

	private JLabel lblEndingTime = null;

	private JLabel lblNameTitle = null;

	private JLabel lblStartingTime = null;

	private SimTimeEdit steTimeEnding = null;

	private SimTimeEdit steTimeStarting = null;

	private JTextField txtName = null;

	private JLabel lblEdge = null;

	private JLabel lblEdgeValue = null;

	private JCheckBox chkEnabled = null;

	private JLabel lblAffectedLanes = null;

	private JScrollPane scrlLanes = null;

	private JTable tblAffectedLanes = null;

	/**
	 * This is the default constructor
	 */
	public PnlAccidentProperties() {
		super();
		initialize();
	}

	public void setAccident(Accident accident) {
		currentAccident = accident;

		txtName.setText(currentAccident.getName());
		
		lblEdgeValue.setText(currentAccident.getEdge().getId());
		
		steTimeStarting.setValue(currentAccident.getStartingTime());
		steTimeEnding.setValue(currentAccident.getEndingTime());
		
		chkEnabled.setSelected(currentAccident.isEnabled());
		
		tblAffectedLanes.setModel(new AffectedLanesTableModel(currentAccident.getAffectedLanes()));

	}

	/**
	 * This method initializes steTimeEnding
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steTimeEnding"
	 */
	private SimTimeEdit getSteTimeEnding() {
		if (steTimeEnding == null) {
			steTimeEnding = new SimTimeEdit();
			steTimeEnding.setPreferredSize(new Dimension(166, 45));
			steTimeEnding.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentAccident.setEndingTime(steTimeEnding.getValue());
				}
			});
		}
		return steTimeEnding;
	}

	/**
	 * This method initializes steTimeStarting
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steTimeStarting"
	 */
	private SimTimeEdit getSteTimeStarting() {
		if (steTimeStarting == null) {
			steTimeStarting = new SimTimeEdit();
			steTimeStarting.setPreferredSize(new Dimension(166, 45));
			steTimeStarting.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentAccident.setStartingTime(steTimeStarting.getValue());
				}
			});
		}
		return steTimeStarting;
	}

	/**
	 * This method initializes txtName
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtName"
	 */
	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					currentAccident.setName(txtName.getText());
				}
			});
		}
		return txtName;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
		gridBagConstraints111.fill = GridBagConstraints.BOTH;
		gridBagConstraints111.gridy = 6;
		gridBagConstraints111.weightx = 1.0D;
		gridBagConstraints111.weighty = 1.0;
		gridBagConstraints111.gridwidth = 2;
		gridBagConstraints111.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints111.gridx = 0;
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 0;
		gridBagConstraints9.insets = new Insets(3, 10, 0, 0);
		gridBagConstraints9.gridwidth = 2;
		gridBagConstraints9.anchor = GridBagConstraints.WEST;
		gridBagConstraints9.gridy = 5;
		lblAffectedLanes = new JLabel();
		lblAffectedLanes.setText("Affected lanes:");
		GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
		gridBagConstraints31.gridx = 1;
		gridBagConstraints31.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints31.gridy = 4;
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 1;
		gridBagConstraints21.insets = new Insets(3, 0, 3, 3);
		gridBagConstraints21.anchor = GridBagConstraints.WEST;
		gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints21.gridy = 1;
		lblEdgeValue = new JLabel();
		lblEdgeValue.setText("Edge");
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridx = 0;
		gridBagConstraints13.insets = new Insets(3, 10, 0, 5);
		gridBagConstraints13.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints13.gridy = 1;
		lblEdge = new JLabel();
		lblEdge.setText("Edge:");
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.insets = new Insets(3, 0, 3, 3);
		gridBagConstraints2.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints2.gridy = 3;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 1;
		gridBagConstraints12.anchor = GridBagConstraints.WEST;
		gridBagConstraints12.insets = new Insets(3, 0, 3, 3);
		gridBagConstraints12.gridy = 2;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.insets = new Insets(3, 10, 0, 0);
		gridBagConstraints3.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints3.gridy = 3;
		lblEndingTime = new JLabel();
		lblEndingTime.setText("Ending time:");
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.insets = new Insets(3, 10, 0, 5);
		gridBagConstraints11.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints11.gridy = 2;
		lblStartingTime = new JLabel();
		lblStartingTime.setText("Starting time:");
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		lblNameTitle = new JLabel();
		lblNameTitle.setText("Name:");
		this.setSize(272, 277);
		this.setLayout(new GridBagLayout());
		this.add(lblNameTitle, gridBagConstraints);
		this.add(getTxtName(), gridBagConstraints1);
		this.add(lblStartingTime, gridBagConstraints11);
		this.add(lblEndingTime, gridBagConstraints3);
		this.add(getSteTimeStarting(), gridBagConstraints12);
		this.add(getSteTimeEnding(), gridBagConstraints2);
		this.add(lblEdge, gridBagConstraints13);
		this.add(lblEdgeValue, gridBagConstraints21);
		this.add(getChkEnabled(), gridBagConstraints31);
		this.add(lblAffectedLanes, gridBagConstraints9);
		this.add(getScrlLanes(), gridBagConstraints111);
	}

	/**
	 * This method initializes chkEnabled	
	 * @return  javax.swing.JCheckBox
	 * @uml.property  name="chkEnabled"
	 */
	private JCheckBox getChkEnabled() {
		if (chkEnabled == null) {
			chkEnabled = new JCheckBox();
			chkEnabled.setText("Enabled");
			chkEnabled.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					currentAccident.setEnabled(chkEnabled.isSelected());
				}
			});
		}
		return chkEnabled;
	}

	/**
	 * This method initializes scrlLanes	
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrlLanes"
	 */
	private JScrollPane getScrlLanes() {
		if (scrlLanes == null) {
			scrlLanes = new JScrollPane();
			scrlLanes.setPreferredSize(new Dimension(40, 40));
			scrlLanes.setViewportView(getTblAffectedLanes());
		}
		return scrlLanes;
	}

	/**
	 * This method initializes tblAffectedLanes	
	 * @return  javax.swing.JTable
	 * @uml.property  name="tblAffectedLanes"
	 */
	private JTable getTblAffectedLanes() {
		if (tblAffectedLanes == null) {
			tblAffectedLanes = new JTable();
			tblAffectedLanes.setBounds(new Rectangle(0, 0, 28, 40));
		}
		return tblAffectedLanes;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
