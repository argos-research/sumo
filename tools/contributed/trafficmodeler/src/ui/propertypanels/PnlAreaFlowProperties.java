package ui.propertypanels;

import classes.Project;
import classes.TypeSelection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import simulation.VehicleType;
import trafficdefinition.AreaFlow;
import ui.controls.SimTimeEdit;
import ui.dialogs.DlgSelectTypes;

/**
 * @author  PapaleonLe01
 */
public class PnlAreaFlowProperties extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnChangeColor = null;

	private JButton btnSelectVehicleTypes = null;

	private AreaFlow currentAreaFlow = null; // @jve:decl-index=0:

	private JLabel lblBeginTimeTitle = null;

	private JLabel lblColor = null;

	private JLabel lblEndTimeTitle = null;

	private JLabel lblNameTitle = null;

	private JLabel lblNumberOfVehiclesTitle = null;

	private JSpinner spnNumberOfVehicles = null;

	private SimTimeEdit steBeginTime = null;

	private SimTimeEdit steEndTime = null;

	private JTextField txtName = null;

	private JCheckBox chkExportPolygon = null;

	private JCheckBox chkEnabled = null;

	/**
	 * This is the default constructor
	 */
	public PnlAreaFlowProperties() {
		super();
		initialize();
	}

	public void setAreaFlow(AreaFlow flow) {
		currentAreaFlow = flow;

		txtName.setText(currentAreaFlow.getName());
		steBeginTime.setValue(currentAreaFlow.getBeginTime());
		steEndTime.setValue(currentAreaFlow.getEndTime());
		spnNumberOfVehicles.setValue(currentAreaFlow.getNumberOfVehicles());
		btnChangeColor.setBackground(currentAreaFlow.getColor());
		chkExportPolygon.setSelected(currentAreaFlow.getExportPolygon());
		
		chkEnabled.setSelected(currentAreaFlow.isEnabled());
	}

	/**
	 * This method initializes btnChangeColor
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnChangeColor"
	 */
	private JButton getBtnChangeColor() {
		if (btnChangeColor == null) {
			btnChangeColor = new JButton();
			btnChangeColor.setPreferredSize(new Dimension(86, 20));
			btnChangeColor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ChangeColor();
				}
			});
		}
		return btnChangeColor;
	}

	/**
	 * This method initializes btnSelectVehicleTypes
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnSelectVehicleTypes"
	 */
	private JButton getBtnSelectVehicleTypes() {
		if (btnSelectVehicleTypes == null) {
			btnSelectVehicleTypes = new JButton();
			btnSelectVehicleTypes.setText("Vehicle types...");
			btnSelectVehicleTypes.setIcon(new ImageIcon(getClass().getResource("/resources/icons/vehicletypes.png")));
			btnSelectVehicleTypes.setPreferredSize(new Dimension(150, 20));
			btnSelectVehicleTypes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectVehicleTypes();
				}
			});
		}
		return btnSelectVehicleTypes;
	}

	/**
	 * This method initializes spnNumberOfVehicles	
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnNumberOfVehicles"
	 */
	private JSpinner getSpnNumberOfVehicles() {
		if (spnNumberOfVehicles == null) {
			spnNumberOfVehicles = new JSpinner();
			spnNumberOfVehicles.setModel(new SpinnerNumberModel(1,1,1000000,1));
			spnNumberOfVehicles.setPreferredSize(new Dimension(70, 20));
			spnNumberOfVehicles.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentAreaFlow.setNumberOfVehicles((Integer)spnNumberOfVehicles.getValue());

				}
			});
		}
		return spnNumberOfVehicles;
	}

	/**
	 * This method initializes steBeginTime	
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steBeginTime"
	 */
	private SimTimeEdit getSteBeginTime() {
		if (steBeginTime == null) {
			steBeginTime = new SimTimeEdit();
			steBeginTime.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentAreaFlow.setBeginTime(steBeginTime.getValue());
				}
			});
		}
		return steBeginTime;
	}

	/**
	 * This method initializes steEndTime	
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steEndTime"
	 */
	private SimTimeEdit getSteEndTime() {
		if (steEndTime == null) {
			steEndTime = new SimTimeEdit();
			steEndTime.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentAreaFlow.setEndTime(steEndTime.getValue());
				}
			});
		}
		return steEndTime;
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
					currentAreaFlow.setName(txtName.getText());
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
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 1;
		gridBagConstraints5.anchor = GridBagConstraints.WEST;
		gridBagConstraints5.gridy = 6;
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 1;
		gridBagConstraints21.anchor = GridBagConstraints.WEST;
		gridBagConstraints21.gridy = 5;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 1;
		gridBagConstraints4.insets = new Insets(3, 0, 3, 0);
		gridBagConstraints4.gridy = 2;
		GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
		gridBagConstraints32.gridx = 1;
		gridBagConstraints32.insets = new Insets(3, 0, 3, 0);
		gridBagConstraints32.gridy = 1;
		GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
		gridBagConstraints14.gridx = 1;
		gridBagConstraints14.anchor = GridBagConstraints.WEST;
		gridBagConstraints14.gridy = 3;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.insets = new Insets(15, 2, 2, 2);
		gridBagConstraints3.fill = GridBagConstraints.NONE;
		gridBagConstraints3.gridwidth = 2;
		gridBagConstraints3.anchor = GridBagConstraints.NORTH;
		gridBagConstraints3.weighty = 1.0D;
		gridBagConstraints3.gridy = 7;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.anchor = GridBagConstraints.WEST;
		gridBagConstraints2.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints2.gridy = 4;
		lblColor = new JLabel();
		lblColor.setText("Color:");
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridx = 1;
		gridBagConstraints13.fill = GridBagConstraints.BOTH;
		gridBagConstraints13.insets = new Insets(2, 0, 2, 10);
		gridBagConstraints13.gridy = 4;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.anchor = GridBagConstraints.WEST;
		gridBagConstraints12.insets = new Insets(0, 10, 0, 5);
		gridBagConstraints12.gridy = 3;
		lblNumberOfVehiclesTitle = new JLabel();
		lblNumberOfVehiclesTitle.setText("Number of vehicles:");
		GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
		gridBagConstraints31.gridx = 0;
		gridBagConstraints31.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints31.insets = new Insets(3, 10, 0, 0);
		gridBagConstraints31.gridy = 2;
		lblEndTimeTitle = new JLabel();
		lblEndTimeTitle.setText("End time:");
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints11.insets = new Insets(3, 10, 0, 0);
		gridBagConstraints11.gridy = 1;
		lblBeginTimeTitle = new JLabel();
		lblBeginTimeTitle.setText("Begin time:");
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints.gridy = 0;
		lblNameTitle = new JLabel();
		lblNameTitle.setText("Name:");
		this.setSize(300, 254);
		this.setLayout(new GridBagLayout());
		this.add(lblNameTitle, gridBagConstraints);
		this.add(getTxtName(), gridBagConstraints1);
		this.add(lblBeginTimeTitle, gridBagConstraints11);
		this.add(lblEndTimeTitle, gridBagConstraints31);
		this.add(lblNumberOfVehiclesTitle, gridBagConstraints12);
		this.add(getBtnChangeColor(), gridBagConstraints13);
		this.add(lblColor, gridBagConstraints2);
		this.add(getBtnSelectVehicleTypes(), gridBagConstraints3);
		this.add(getSpnNumberOfVehicles(), gridBagConstraints14);
		this.add(getSteBeginTime(), gridBagConstraints32);
		this.add(getSteEndTime(), gridBagConstraints4);
		this.add(getChkExportPolygon(), gridBagConstraints21);
		this.add(getChkEnabled(), gridBagConstraints5);
	}

	protected void ChangeColor() {
		Color newColor = JColorChooser.showDialog(this, "Select flow color", currentAreaFlow.getColor());

		if (newColor != null) {
			currentAreaFlow.setColor(newColor);
			btnChangeColor.setBackground(newColor);
		}
	}

	protected void SelectVehicleTypes() {
		// Create a type selection that contains all the vehicle types of the
		// project, each with a 0 probability
		TypeSelection<VehicleType> projectVehicleTypes = new TypeSelection<VehicleType>(Project.getCurrentlyLoadedProject().getVehicleTypes());

		// Get a copy of the layer's vehicle types (so that if the user cancels, the
		// changes won't be permanent)
		TypeSelection<VehicleType> areaFlowVehicleTypes = new TypeSelection<VehicleType>(currentAreaFlow.getVehicleSelection());

		//Show the vehicle selection dialog
		areaFlowVehicleTypes.addTypesNotInSelection(projectVehicleTypes);

		DlgSelectTypes<VehicleType> dlg = new DlgSelectTypes<VehicleType>(areaFlowVehicleTypes);
		dlg.setVisible(true);

		if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
			// Remove any types that have 0 probability
			areaFlowVehicleTypes.removeTypesWithZeroPercentage();

			// Set the selected  types to the layer
			currentAreaFlow.setVehicleSelection(areaFlowVehicleTypes);
		}
	}

	/**
	 * This method initializes chkExportPolygon	
	 * @return  javax.swing.JCheckBox
	 * @uml.property  name="chkExportPolygon"
	 */
	private JCheckBox getChkExportPolygon() {
		if (chkExportPolygon == null) {
			chkExportPolygon = new JCheckBox();
			chkExportPolygon.setText("Export polygon");
			chkExportPolygon.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					currentAreaFlow.setExportPolygon(chkExportPolygon.isSelected());
				}
			});
		}
		return chkExportPolygon;
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
					currentAreaFlow.setEnabled(chkEnabled.isSelected());
				}
			});
		}
		return chkEnabled;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
