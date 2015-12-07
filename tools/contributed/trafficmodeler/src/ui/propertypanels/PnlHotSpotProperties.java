package ui.propertypanels;

import classes.Project;
import classes.TypeSelection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import simulation.VehicleType;
import trafficdefinition.HotSpot;
import ui.controls.SimTimeEdit;
import ui.dialogs.DlgSelectTypes;

/**
 * @author  PapaleonLe01
 */
public class PnlHotSpotProperties extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnChangeColor = null;

	private JButton btnSelectVehicleTypes = null;

	private JCheckBox chkDirectionIn = null;

	private JCheckBox chkDirectionOut = null;

	private HotSpot currentHotSpot = null; // @jve:decl-index=0:

	private ButtonGroup IncomingTimeTypeGroup = new ButtonGroup(); // @jve:decl-index=0:

	private JLabel lblColorTitle = null;

	private JLabel lblIncomingEndTime = null;

	private JLabel lblInIcon = null;

	private JLabel lblNameTitle = null;

	private JLabel lblNumberOfVehiclesTitle = null;

	private JLabel lblOutgoingBeginTime = null;

	private JLabel lblOutgoingEndTime = null;

	private JLabel lblOutIcon = null;

	private JRadioButton rbIncomingTypeArrival = null;

	private JRadioButton rbIncomingTypeDeparture = null;

	private JSpinner spnNumberOfVehicles = null;

	private SimTimeEdit steIncomingBeginTime = null;

	private SimTimeEdit steIncomingEndTime = null;

	private SimTimeEdit steOutgoingBeginTime = null;

	private SimTimeEdit steOutgoingEndTime = null;

	private JTextField txtName = null;

	private JCheckBox chkEnabled = null;

	private JLabel lblIncomingBeginTime = null;

	/**
	 * This is the default constructor
	 */
	public PnlHotSpotProperties() {
		super();
		initialize();
	}

	public void setHotSpot(HotSpot hotSpot) {
		currentHotSpot = hotSpot;

		txtName.setText(currentHotSpot.getName());
		spnNumberOfVehicles.setValue(currentHotSpot.getNumberOfVehicles());
		btnChangeColor.setBackground(currentHotSpot.getColor());

		chkDirectionIn.setSelected(currentHotSpot.isDirectionIn());
		chkDirectionOut.setSelected(currentHotSpot.isDirectionOut());

		if (currentHotSpot.getDirectionInType() == HotSpot.DirectionInTimeType.TimeOfArrivalToDestination) {
			rbIncomingTypeArrival.setSelected(true);
			rbIncomingTypeDeparture.setSelected(false);
		} else if (currentHotSpot.getDirectionInType() == HotSpot.DirectionInTimeType.TimeOfDepartureFromSource) {
			rbIncomingTypeArrival.setSelected(false);
			rbIncomingTypeDeparture.setSelected(true);
		}

		steIncomingBeginTime.setValue(currentHotSpot.getDirectionInBeginTime());
		steIncomingEndTime.setValue(currentHotSpot.getDirectionInEndTime());

		steOutgoingBeginTime.setValue(currentHotSpot.getDirectionOutBeginTime());
		steOutgoingEndTime.setValue(currentHotSpot.getDirectionOutEndTime());
		
		chkEnabled.setSelected(currentHotSpot.isEnabled());
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
			btnChangeColor.setText("");
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
			btnSelectVehicleTypes.setPreferredSize(new Dimension(150, 20));
			btnSelectVehicleTypes.setIcon(new ImageIcon(getClass().getResource("/resources/icons/vehicletypes.png")));
			btnSelectVehicleTypes.setText("Vehicle types...");
			btnSelectVehicleTypes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectVehicleTypes();
				}
			});
		}
		return btnSelectVehicleTypes;
	}

	/**
	 * This method initializes chkDirectionIn
	 * @return  javax.swing.JCheckBox
	 * @uml.property  name="chkDirectionIn"
	 */
	private JCheckBox getChkDirectionIn() {
		if (chkDirectionIn == null) {
			chkDirectionIn = new JCheckBox();
			chkDirectionIn.setText("Incoming");
			chkDirectionIn.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						DisableEnableIncomingControls(true);

						currentHotSpot.setDirectionIn(true);
					} else {
						DisableEnableIncomingControls(false);

						currentHotSpot.setDirectionIn(false);
					}
				}
			});
		}
		return chkDirectionIn;
	}

	/**
	 * This method initializes chkDirectionOut
	 * @return  javax.swing.JCheckBox
	 * @uml.property  name="chkDirectionOut"
	 */
	private JCheckBox getChkDirectionOut() {
		if (chkDirectionOut == null) {
			chkDirectionOut = new JCheckBox();
			chkDirectionOut.setText("Outgoing");
			chkDirectionOut.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						DisableEnableOutgoingControls(true);

						currentHotSpot.setDirectionOut(true);
					} else {
						DisableEnableOutgoingControls(false);

						currentHotSpot.setDirectionOut(false);
					}
				}
			});
		}
		return chkDirectionOut;
	}

	/**
	 * This method initializes rbIncomingTypeArrival
	 * @return  javax.swing.JRadioButton
	 * @uml.property  name="rbIncomingTypeArrival"
	 */
	private JRadioButton getRbIncomingTypeArrival() {
		if (rbIncomingTypeArrival == null) {
			rbIncomingTypeArrival = new JRadioButton();
			rbIncomingTypeArrival.setText("Time of arrival to destination");
			rbIncomingTypeArrival.setEnabled(false);
			rbIncomingTypeArrival.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						currentHotSpot.setDirectionInType(HotSpot.DirectionInTimeType.TimeOfArrivalToDestination);
					} else {
						currentHotSpot.setDirectionInType(HotSpot.DirectionInTimeType.TimeOfDepartureFromSource);
					}
				}
			});
		}
		return rbIncomingTypeArrival;
	}

	/**
	 * This method initializes rbIncomingTypeDeparture
	 * @return  javax.swing.JRadioButton
	 * @uml.property  name="rbIncomingTypeDeparture"
	 */
	private JRadioButton getRbIncomingTypeDeparture() {
		if (rbIncomingTypeDeparture == null) {
			rbIncomingTypeDeparture = new JRadioButton();
			rbIncomingTypeDeparture.setText("Time of departure from source");
			rbIncomingTypeDeparture.setEnabled(false);
			rbIncomingTypeDeparture.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						currentHotSpot.setDirectionInType(HotSpot.DirectionInTimeType.TimeOfDepartureFromSource);
					} else {
						currentHotSpot.setDirectionInType(HotSpot.DirectionInTimeType.TimeOfArrivalToDestination);
					}
				}
			});
		}
		return rbIncomingTypeDeparture;
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
					currentHotSpot.setNumberOfVehicles((Integer) spnNumberOfVehicles.getValue());
				}
			});
		}
		return spnNumberOfVehicles;
	}

	/**
	 * This method initializes steIncomingBeginTime
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steIncomingBeginTime"
	 */
	private SimTimeEdit getSteIncomingBeginTime() {
		if (steIncomingBeginTime == null) {
			steIncomingBeginTime = new SimTimeEdit();
			steIncomingBeginTime.setEnabled(false);
			steIncomingBeginTime.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentHotSpot.setDirectionInBeginTime(steIncomingBeginTime.getValue());
				}
			});
		}
		return steIncomingBeginTime;
	}

	/**
	 * This method initializes steIncomingEndTime
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steIncomingEndTime"
	 */
	private SimTimeEdit getSteIncomingEndTime() {
		if (steIncomingEndTime == null) {
			steIncomingEndTime = new SimTimeEdit();
			steIncomingEndTime.setEnabled(false);
			steIncomingEndTime.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentHotSpot.setDirectionInEndTime(steIncomingEndTime.getValue());
				}
			});
		}
		return steIncomingEndTime;
	}

	/**
	 * This method initializes steOutgoingBeginTime
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steOutgoingBeginTime"
	 */
	private SimTimeEdit getSteOutgoingBeginTime() {
		if (steOutgoingBeginTime == null) {
			steOutgoingBeginTime = new SimTimeEdit();
			steOutgoingBeginTime.setEnabled(false);
			steOutgoingBeginTime.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentHotSpot.setDirectionOutBeginTime(steOutgoingBeginTime.getValue());
				}
			});
		}
		return steOutgoingBeginTime;
	}

	/**
	 * This method initializes steOutgoingEndTime
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steOutgoingEndTime"
	 */
	private SimTimeEdit getSteOutgoingEndTime() {
		if (steOutgoingEndTime == null) {
			steOutgoingEndTime = new SimTimeEdit();
			steOutgoingEndTime.setEnabled(false);
			steOutgoingEndTime.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentHotSpot.setDirectionOutEndTime(steOutgoingEndTime.getValue());
				}
			});
		}
		return steOutgoingEndTime;
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
					currentHotSpot.setName(txtName.getText());
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
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints4.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints4.gridy = 8;
		lblIncomingBeginTime = new JLabel();
		lblIncomingBeginTime.setText("Begin Time:");
		lblIncomingBeginTime.setEnabled(false);
		GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
		gridBagConstraints33.gridx = 0;
		gridBagConstraints33.fill = GridBagConstraints.BOTH;
		gridBagConstraints33.gridy = 8;
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 1;
		gridBagConstraints6.anchor = GridBagConstraints.WEST;
		gridBagConstraints6.gridy = 3;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 1;
		gridBagConstraints3.anchor = GridBagConstraints.WEST;
		gridBagConstraints3.gridy = 11;
		lblOutIcon = new JLabel();
		lblOutIcon.setText("");
		lblOutIcon.setIcon(new ImageIcon(getClass().getResource("/resources/icons/hotspotout.png")));
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 1;
		gridBagConstraints21.anchor = GridBagConstraints.WEST;
		gridBagConstraints21.gridy = 5;
		lblInIcon = new JLabel();
		lblInIcon.setText("");
		lblInIcon.setIcon(new ImageIcon(getClass().getResource("/resources/icons/hotspotin.png")));
		GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
		gridBagConstraints19.gridx = 0;
		gridBagConstraints19.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints19.insets = new Insets(5, 0, 0, 5);
		gridBagConstraints19.gridy = 13;
		lblOutgoingEndTime = new JLabel();
		lblOutgoingEndTime.setText("End time:");
		lblOutgoingEndTime.setEnabled(false);
		GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
		gridBagConstraints18.gridx = 0;
		gridBagConstraints18.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints18.insets = new Insets(5, 0, 0, 5);
		gridBagConstraints18.gridy = 12;
		lblOutgoingBeginTime = new JLabel();
		lblOutgoingBeginTime.setText("Begin time:");
		lblOutgoingBeginTime.setEnabled(false);
		GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
		gridBagConstraints17.gridx = 0;
		gridBagConstraints17.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints17.insets = new Insets(5, 0, 0, 10);
		gridBagConstraints17.gridy = 9;
		lblIncomingEndTime = new JLabel();
		lblIncomingEndTime.setText("End time:");
		lblIncomingEndTime.setEnabled(false);
		GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
		gridBagConstraints16.gridx = 0;
		gridBagConstraints16.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints16.insets = new Insets(10, 0, 0, 5);
		gridBagConstraints16.gridy = 7;
		GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
		gridBagConstraints14.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints14.gridx = 1;
		gridBagConstraints14.gridy = 13;
		gridBagConstraints14.weighty = 1.0D;
		gridBagConstraints14.insets = new Insets(5, 10, 0, 0);
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints13.gridx = 0;
		gridBagConstraints13.gridy = 13;
		gridBagConstraints13.weighty = 1.0D;
		gridBagConstraints13.insets = new Insets(5, 0, 0, 10);
		GridBagConstraints gridBagConstraints121 = new GridBagConstraints();
		gridBagConstraints121.insets = new Insets(5, 10, 2, 0);
		gridBagConstraints121.gridy = 12;
		gridBagConstraints121.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints121.gridx = 1;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.gridy = 12;
		gridBagConstraints11.insets = new Insets(5, 0, 0, 10);
		GridBagConstraints gridBagConstraints101 = new GridBagConstraints();
		gridBagConstraints101.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints101.gridx = 1;
		gridBagConstraints101.gridy = 9;
		gridBagConstraints101.insets = new Insets(5, 10, 2, 0);
		GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
		gridBagConstraints91.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints91.gridx = 0;
		gridBagConstraints91.gridy = 9;
		gridBagConstraints91.insets = new Insets(5, 0, 0, 10);
		GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
		gridBagConstraints81.insets = new Insets(10, 10, 2, 0);
		gridBagConstraints81.gridy = 8;
		gridBagConstraints81.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints81.gridx = 1;
		GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
		gridBagConstraints61.anchor = GridBagConstraints.WEST;
		gridBagConstraints61.gridx = 0;
		gridBagConstraints61.gridy = 7;
		gridBagConstraints61.gridwidth = 2;
		gridBagConstraints61.insets = new Insets(0, 20, 0, 0);
		GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
		gridBagConstraints51.anchor = GridBagConstraints.WEST;
		gridBagConstraints51.gridx = 0;
		gridBagConstraints51.gridy = 6;
		gridBagConstraints51.weightx = 0.0D;
		gridBagConstraints51.gridwidth = 2;
		gridBagConstraints51.insets = new Insets(0, 20, 0, 0);
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints2.anchor = GridBagConstraints.WEST;
		gridBagConstraints2.gridy = 11;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 0;
		gridBagConstraints12.anchor = GridBagConstraints.WEST;
		gridBagConstraints12.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints12.gridy = 5;
		GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
		gridBagConstraints32.gridx = 1;
		gridBagConstraints32.anchor = GridBagConstraints.WEST;
		gridBagConstraints32.gridy = 1;
		GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
		gridBagConstraints41.gridx = 0;
		gridBagConstraints41.insets = new Insets(10, 2, 10, 2);
		gridBagConstraints41.anchor = GridBagConstraints.NORTH;
		gridBagConstraints41.gridwidth = 2;
		gridBagConstraints41.gridy = 4;
		GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
		gridBagConstraints31.gridx = 1;
		gridBagConstraints31.insets = new Insets(2, 0, 2, 10);
		gridBagConstraints31.fill = GridBagConstraints.BOTH;
		gridBagConstraints31.gridy = 2;
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 0;
		gridBagConstraints7.anchor = GridBagConstraints.WEST;
		gridBagConstraints7.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints7.gridy = 2;
		lblColorTitle = new JLabel();
		lblColorTitle.setText("Color:");
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 0;
		gridBagConstraints5.anchor = GridBagConstraints.WEST;
		gridBagConstraints5.insets = new Insets(0, 10, 0, 5);
		gridBagConstraints5.gridy = 1;
		lblNumberOfVehiclesTitle = new JLabel();
		lblNumberOfVehiclesTitle.setText("Number of vehicles:");
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
		this.setSize(315, 433);
		this.setLayout(new GridBagLayout());
		this.add(lblNameTitle, gridBagConstraints);
		this.add(getTxtName(), gridBagConstraints1);
		this.add(lblNumberOfVehiclesTitle, gridBagConstraints5);
		this.add(lblColorTitle, gridBagConstraints7);
		this.add(getBtnChangeColor(), gridBagConstraints31);
		this.add(getBtnSelectVehicleTypes(), gridBagConstraints41);
		this.add(getSpnNumberOfVehicles(), gridBagConstraints32);
		this.add(getChkDirectionIn(), gridBagConstraints12);
		this.add(getChkDirectionOut(), gridBagConstraints2);
		this.add(getRbIncomingTypeDeparture(), gridBagConstraints51);
		this.add(getRbIncomingTypeArrival(), gridBagConstraints61);
		this.add(getSteIncomingBeginTime(), gridBagConstraints81);
		this.add(getSteIncomingEndTime(), gridBagConstraints101);
		this.add(getSteOutgoingBeginTime(), gridBagConstraints121);
		this.add(getSteOutgoingEndTime(), gridBagConstraints14);
		this.add(lblInIcon, gridBagConstraints21);
		this.add(lblOutIcon, gridBagConstraints3);
		this.add(getChkEnabled(), gridBagConstraints6);
		this.add(lblIncomingBeginTime, gridBagConstraints4);
		this.add(lblIncomingEndTime, gridBagConstraints17);
		this.add(lblOutgoingBeginTime, gridBagConstraints18);
		this.add(lblOutgoingEndTime, gridBagConstraints19);
		IncomingTimeTypeGroup.add(rbIncomingTypeArrival);
		IncomingTimeTypeGroup.add(rbIncomingTypeDeparture);
	}

	protected void ChangeColor() {
		Color newColor = JColorChooser.showDialog(this, "Select flow color", currentHotSpot.getColor());

		if (newColor != null) {
			currentHotSpot.setColor(newColor);
			btnChangeColor.setBackground(newColor);
		}
	}

	protected void DisableEnableIncomingControls(boolean b) {
		rbIncomingTypeArrival.setEnabled(b);
		rbIncomingTypeDeparture.setEnabled(b);

		lblIncomingBeginTime.setEnabled(b);
		lblIncomingEndTime.setEnabled(b);

		steIncomingBeginTime.setEnabled(b);
		steIncomingEndTime.setEnabled(b);
	}

	protected void DisableEnableOutgoingControls(boolean b) {
		lblOutgoingBeginTime.setEnabled(b);
		lblOutgoingEndTime.setEnabled(b);
		steOutgoingBeginTime.setEnabled(b);
		steOutgoingEndTime.setEnabled(b);
	}

	protected void SelectVehicleTypes() {
		// Create a type selection that contains all the vehicle types of the
		// project, each with a 0 probability
		TypeSelection<VehicleType> projectVehicleTypes = new TypeSelection<VehicleType>(Project.getCurrentlyLoadedProject().getVehicleTypes());

		// Get a copy of the layer's vehicle types (so that if the user cancels,
		// the
		// changes won't be permanent)
		TypeSelection<VehicleType> hotspotVehicleTypes = new TypeSelection<VehicleType>(currentHotSpot.getVehicleSelection());

		// Show the vehicle selection dialog
		hotspotVehicleTypes.addTypesNotInSelection(projectVehicleTypes);

		DlgSelectTypes<VehicleType> dlg = new DlgSelectTypes<VehicleType>(hotspotVehicleTypes);
		dlg.setVisible(true);

		if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
			// Remove any types that have 0 probability
			hotspotVehicleTypes.removeTypesWithZeroPercentage();

			// Set the selected types to the layer
			currentHotSpot.setVehicleSelection(hotspotVehicleTypes);
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
					currentHotSpot.setEnabled(chkEnabled.isSelected());
				}
			});
		}
		return chkEnabled;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
