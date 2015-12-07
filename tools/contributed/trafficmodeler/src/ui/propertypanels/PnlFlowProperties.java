package ui.propertypanels;

import classes.Project;
import classes.TypeSelection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
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
import trafficdefinition.Flow;
import ui.controls.SimTimeEdit;
import ui.dialogs.DlgSelectTypes;

/**
 * @author  PapaleonLe01
 */
public class PnlFlowProperties extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnChangeColor = null;

	private JButton btnSelectVehicleTypes = null;

	private Flow currentFlow = null; // @jve:decl-index=0:

	private JLabel lblBeginTimeTitle = null;

	private JLabel lblColorTitle = null;

	private JLabel lblEndTimeTitle = null;

	private JLabel lblFromTitle = null;

	private JLabel lblFromValue = null;

	private JLabel lblNameTitle = null;

	private JLabel lblNumberOfVehiclesTitle = null;

	private JLabel lblToTitle = null;

	private JLabel lblToValue = null;

	private JSpinner spnNumberOfVehicles = null;

	private JTextField txtName = null;

	private JCheckBox chkEnabled = null;

	private SimTimeEdit steBeginTime = null;

	private SimTimeEdit steEndTime = null;

	/**
	 * This is the default constructor
	 */
	public PnlFlowProperties() {
		super();
		initialize();
	}

	public void setFlow(Flow flow) {
		currentFlow = flow;

		txtName.setText(currentFlow.getName());
		lblFromValue.setText(currentFlow.getStart().getId());
		lblToValue.setText(currentFlow.getEnd().getId());
		steBeginTime.setValue(currentFlow.getBeginTime());
		steEndTime.setValue(currentFlow.getEndTime());
		spnNumberOfVehicles.setValue(currentFlow.getNumberOfVehicles());
		btnChangeColor.setBackground(currentFlow.getColor());
		
		chkEnabled.setSelected(currentFlow.isEnabled());
	}

	/**
	 * This method initializes btnChangeColor
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnChangeColor"
	 */
	private JButton getBtnChangeColor() {
		if (btnChangeColor == null) {
			btnChangeColor = new JButton();
			btnChangeColor.setText("");
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
			spnNumberOfVehicles.setPreferredSize(new Dimension(70, 20));
			spnNumberOfVehicles.setModel(new SpinnerNumberModel(1, 1, 1000000, 1));
			spnNumberOfVehicles.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentFlow.setNumberOfVehicles((Integer) spnNumberOfVehicles.getValue());

				}
			});
		}
		return spnNumberOfVehicles;
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
					currentFlow.setName(txtName.getText());
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
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 1;
		gridBagConstraints21.gridy = 4;
		GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
		gridBagConstraints15.gridx = 1;
		gridBagConstraints15.gridy = 3;
		GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
		gridBagConstraints14.gridx = 1;
		gridBagConstraints14.anchor = GridBagConstraints.WEST;
		gridBagConstraints14.gridy = 7;
		GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
		gridBagConstraints22.gridx = 1;
		gridBagConstraints22.anchor = GridBagConstraints.WEST;
		gridBagConstraints22.gridy = 5;
		GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
		gridBagConstraints23.gridx = 1;
		gridBagConstraints23.fill = GridBagConstraints.BOTH;
		gridBagConstraints23.insets = new Insets(2, 0, 2, 10);
		gridBagConstraints23.gridy = 6;
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridx = 0;
		gridBagConstraints13.anchor = GridBagConstraints.NORTH;
		gridBagConstraints13.insets = new Insets(15, 2, 2, 2);
		gridBagConstraints13.weighty = 1.0D;
		gridBagConstraints13.gridwidth = 2;
		gridBagConstraints13.gridy = 8;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.insets = new Insets(0, 10, 0, 5);
		gridBagConstraints12.anchor = GridBagConstraints.WEST;
		gridBagConstraints12.gridy = 5;
		lblNumberOfVehiclesTitle = new JLabel();
		lblNumberOfVehiclesTitle.setText("Number of vehicles:");
		GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
		gridBagConstraints51.gridx = 0;
		gridBagConstraints51.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints51.anchor = GridBagConstraints.WEST;
		gridBagConstraints51.gridy = 6;
		lblColorTitle = new JLabel();
		lblColorTitle.setText("Color:");
		GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
		gridBagConstraints31.gridx = 0;
		gridBagConstraints31.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints31.anchor = GridBagConstraints.WEST;
		gridBagConstraints31.gridy = 4;
		lblEndTimeTitle = new JLabel();
		lblEndTimeTitle.setText("End time:");
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints11.anchor = GridBagConstraints.WEST;
		gridBagConstraints11.gridy = 3;
		lblBeginTimeTitle = new JLabel();
		lblBeginTimeTitle.setText("Begin time:");
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 1;
		gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints5.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints5.gridy = 2;
		lblToValue = new JLabel();
		lblToValue.setText("To");
		lblToValue.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints4.anchor = GridBagConstraints.WEST;
		gridBagConstraints4.gridy = 2;
		lblToTitle = new JLabel();
		lblToTitle.setText("To:");
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 1;
		gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints3.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints3.gridy = 1;
		lblFromValue = new JLabel();
		lblFromValue.setText("From");
		lblFromValue.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		lblFromValue.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints2.anchor = GridBagConstraints.WEST;
		gridBagConstraints2.gridy = 1;
		lblFromTitle = new JLabel();
		lblFromTitle.setText("From:");
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
		this.setSize(300, 214);
		this.setLayout(new GridBagLayout());
		this.add(lblNameTitle, gridBagConstraints);
		this.add(getTxtName(), gridBagConstraints1);
		this.add(lblFromTitle, gridBagConstraints2);
		this.add(lblFromValue, gridBagConstraints3);
		this.add(lblToTitle, gridBagConstraints4);
		this.add(lblToValue, gridBagConstraints5);
		this.add(lblBeginTimeTitle, gridBagConstraints11);
		this.add(lblEndTimeTitle, gridBagConstraints31);
		this.add(lblColorTitle, gridBagConstraints51);
		this.add(lblNumberOfVehiclesTitle, gridBagConstraints12);
		this.add(getBtnSelectVehicleTypes(), gridBagConstraints13);
		this.add(getBtnChangeColor(), gridBagConstraints23);
		this.add(getSpnNumberOfVehicles(), gridBagConstraints22);
		this.add(getChkEnabled(), gridBagConstraints14);
		this.add(getSteBeginTime(), gridBagConstraints15);
		this.add(getSteEndTime(), gridBagConstraints21);
	}

	protected void ChangeColor() {
		Color newColor = JColorChooser.showDialog(this, "Select flow color", currentFlow.getColor());

		if (newColor != null) {
			currentFlow.setColor(newColor);
			btnChangeColor.setBackground(newColor);
		}
	}

	protected void SelectVehicleTypes() {
		// Create a type selection that contains all the vehicle types of the
		// project, each with a 0 probability
		TypeSelection<VehicleType> projectVehicleTypes = new TypeSelection<VehicleType>(Project.getCurrentlyLoadedProject().getVehicleTypes());

		// Get a copy of the layer's vehicle types (so that if the user cancels,
		// the
		// changes won't be permanent)
		TypeSelection<VehicleType> flowVehicleTypes = new TypeSelection<VehicleType>(currentFlow.getVehicleSelection());

		// Show the vehicle selection dialog
		flowVehicleTypes.addTypesNotInSelection(projectVehicleTypes);

		DlgSelectTypes<VehicleType> dlg = new DlgSelectTypes<VehicleType>(flowVehicleTypes);
		dlg.setVisible(true);

		if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
			// Remove any types that have 0 probability
			flowVehicleTypes.removeTypesWithZeroPercentage();

			// Set the selected types to the layer
			currentFlow.setVehicleSelection(flowVehicleTypes);
		}
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
					currentFlow.setEnabled(chkEnabled.isSelected());
				}
			});
		}
		return chkEnabled;
	}

	/**
	 * This method initializes steBeginTime	
	 * @return  ui.controls.SimTimeEdit
	 * @uml.property  name="steBeginTime"
	 */
	private SimTimeEdit getSteBeginTime() {
		if (steBeginTime == null) {
			steBeginTime = new SimTimeEdit();
			steBeginTime.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentFlow.setBeginTime(steBeginTime.getValue());
				}
			});

		}
		return steBeginTime;
	}

	/**
	 * This method initializes steEndTime	
	 * @return  ui.controls.SimTimeEdit
	 * @uml.property  name="steEndTime"
	 */
	private SimTimeEdit getSteEndTime() {
		if (steEndTime == null) {
			steEndTime = new SimTimeEdit();
			steEndTime.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentFlow.setEndTime(steEndTime.getValue());
				}
			});

		}
		return steEndTime;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
