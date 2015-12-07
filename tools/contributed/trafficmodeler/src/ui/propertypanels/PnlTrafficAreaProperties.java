package ui.propertypanels;

import classes.Project;
import classes.TypeSelection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import simulation.JobType;
import trafficdefinition.TrafficArea;
import ui.dialogs.DlgSelectTypes;

/**
 * @author  PapaleonLe01
 */
public class PnlTrafficAreaProperties extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnChangeColor = null;

	private TrafficArea currentArea = null; // @jve:decl-index=0:

	private JLabel lblColorTitle = null;

	private JLabel lblNameTitle = null;

	private JLabel lblPopulation = null;

	private JLabel lblWorkPositions = null;

	private JSpinner spnPopulation = null;

	private JSpinner spnWorkPositions = null;

	private JTextField txtName = null;

	private JButton btnResidentJobs = null;

	private JButton btnWorkerJobs = null;

	private JLabel lblAverageAdultsPerHouse = null;

	private JLabel lblAverageChildrenPerHouse = null;

	private JLabel lblPossibilityAdultIsDriver = null;

	private JLabel lblPossibilityAdultHasJob = null;

	private JSpinner spnAverageAdultsPerHouse = null;

	private JSpinner spnAverageChildrenPerHouse = null;

	private JSpinner spnPossibilityAdultIsDriver = null;

	private JSpinner spnPossibilityAdultHasJob = null;

	private JLabel lblPossibilityDriverHasCar = null;

	private JSpinner spnPossibilityDriverHasCar = null;

	private JCheckBox chkEnabled = null;

	/**
	 * This is the default constructor
	 */
	public PnlTrafficAreaProperties() {
		super();
		initialize();
	}

	public void setArea(TrafficArea area) {
		currentArea = area;

		txtName.setText(currentArea.getName());
		btnChangeColor.setBackground(currentArea.getColor());
		spnPopulation.setValue(currentArea.getPopulation());
		spnWorkPositions.setValue(currentArea.getWorkPositions());

		spnAverageAdultsPerHouse.setValue(currentArea.getAverageAdultsPerHouse());
		spnAverageChildrenPerHouse.setValue(currentArea.getAverageChildrenPerHouse());
		spnPossibilityAdultHasJob.setValue(currentArea.getPossibilityAdultHasJob());
		spnPossibilityAdultIsDriver.setValue(currentArea.getPossibilityAdultIsDriver());
		spnPossibilityDriverHasCar.setValue(currentArea.getPossibilityDriverHasCar());

		// Disable job selection buttons based on area values
		if (currentArea.getPopulation() == 0) {
			btnResidentJobs.setEnabled(false);
		} else {
			btnResidentJobs.setEnabled(true);
		}

		if (currentArea.getWorkPositions() == 0) {
			btnWorkerJobs.setEnabled(false);
		} else {
			btnWorkerJobs.setEnabled(true);
		}

		chkEnabled.setSelected(currentArea.isEnabled());
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
	 * This method initializes spnPopulation
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnPopulation"
	 */
	private JSpinner getSpnPopulation() {
		if (spnPopulation == null) {
			spnPopulation = new JSpinner();
			spnPopulation.setPreferredSize(new Dimension(70, 20));
			spnPopulation.setModel(new SpinnerNumberModel(0, 0, 1000000, 1));
			spnPopulation.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentArea.setPopulation((Integer) spnPopulation.getValue());

					// Disable or enable the button based on the value
					if ((Integer) spnPopulation.getValue() == 0) {
						btnResidentJobs.setEnabled(false);
					} else {
						btnResidentJobs.setEnabled(true);
					}
				}
			});
		}
		return spnPopulation;
	}

	/**
	 * This method initializes spnWorkPositions
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnWorkPositions"
	 */
	private JSpinner getSpnWorkPositions() {
		if (spnWorkPositions == null) {
			spnWorkPositions = new JSpinner();
			spnWorkPositions.setPreferredSize(new Dimension(70, 20));
			spnWorkPositions.setModel(new SpinnerNumberModel(0, 0, 1000000, 1));
			spnWorkPositions.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentArea.setWorkPositions((Integer) spnWorkPositions.getValue());

					// Disable or enable the button based on the value
					if ((Integer) spnWorkPositions.getValue() == 0) {
						btnWorkerJobs.setEnabled(false);
					} else {
						btnWorkerJobs.setEnabled(true);
					}
				}
			});

		}
		return spnWorkPositions;
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
					currentArea.setName(txtName.getText());
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
		GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
		gridBagConstraints81.gridx = 1;
		gridBagConstraints81.weighty = 1.0D;
		gridBagConstraints81.anchor = GridBagConstraints.WEST;
		gridBagConstraints81.gridy = 11;
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 1;
		gridBagConstraints10.anchor = GridBagConstraints.WEST;
		gridBagConstraints10.gridy = 9;
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 0;
		gridBagConstraints9.insets = new Insets(5, 10, 0, 0);
		gridBagConstraints9.anchor = GridBagConstraints.WEST;
		gridBagConstraints9.gridy = 9;
		lblPossibilityDriverHasCar = new JLabel();
		lblPossibilityDriverHasCar.setText("Possibility driver has car:");
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 1;
		gridBagConstraints8.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints8.gridy = 10;
		GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
		gridBagConstraints71.gridx = 1;
		gridBagConstraints71.anchor = GridBagConstraints.WEST;
		gridBagConstraints71.gridy = 8;
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 1;
		gridBagConstraints6.anchor = GridBagConstraints.WEST;
		gridBagConstraints6.gridy = 7;
		GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
		gridBagConstraints51.gridx = 1;
		gridBagConstraints51.anchor = GridBagConstraints.WEST;
		gridBagConstraints51.gridy = 6;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.insets = new Insets(5, 10, 0, 0);
		gridBagConstraints4.anchor = GridBagConstraints.WEST;
		gridBagConstraints4.gridy = 10;
		lblPossibilityAdultHasJob = new JLabel();
		lblPossibilityAdultHasJob.setText("Possibility adult has job:");
		GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
		gridBagConstraints32.gridx = 0;
		gridBagConstraints32.insets = new Insets(5, 10, 0, 0);
		gridBagConstraints32.anchor = GridBagConstraints.WEST;
		gridBagConstraints32.gridy = 8;
		lblPossibilityAdultIsDriver = new JLabel();
		lblPossibilityAdultIsDriver.setText("Possibility adult is driver:");
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 0;
		gridBagConstraints21.insets = new Insets(5, 10, 0, 10);
		gridBagConstraints21.anchor = GridBagConstraints.WEST;
		gridBagConstraints21.gridy = 7;
		lblAverageChildrenPerHouse = new JLabel();
		lblAverageChildrenPerHouse.setText("Average children per house:");
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridx = 0;
		gridBagConstraints13.anchor = GridBagConstraints.WEST;
		gridBagConstraints13.insets = new Insets(5, 10, 0, 0);
		gridBagConstraints13.gridy = 6;
		lblAverageAdultsPerHouse = new JLabel();
		lblAverageAdultsPerHouse.setText("Average adults per house:");
		GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
		gridBagConstraints31.gridx = 0;
		gridBagConstraints31.insets = new Insets(5, 0, 5, 10);
		gridBagConstraints31.anchor = GridBagConstraints.NORTH;
		gridBagConstraints31.fill = GridBagConstraints.NONE;
		gridBagConstraints31.gridwidth = 2;
		gridBagConstraints31.gridy = 4;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.insets = new Insets(5, 0, 5, 10);
		gridBagConstraints2.anchor = GridBagConstraints.NORTH;
		gridBagConstraints2.fill = GridBagConstraints.NONE;
		gridBagConstraints2.gridwidth = 2;
		gridBagConstraints2.gridy = 2;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 1;
		gridBagConstraints5.anchor = GridBagConstraints.WEST;
		gridBagConstraints5.gridy = 3;
		GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
		gridBagConstraints41.gridx = 1;
		gridBagConstraints41.anchor = GridBagConstraints.WEST;
		gridBagConstraints41.gridy = 1;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 1;
		gridBagConstraints12.anchor = GridBagConstraints.NORTH;
		gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints12.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints12.gridy = 5;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.insets = new Insets(0, 10, 0, 5);
		gridBagConstraints3.anchor = GridBagConstraints.WEST;
		gridBagConstraints3.gridy = 3;
		lblWorkPositions = new JLabel();
		lblWorkPositions.setText("Work positions:");
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.anchor = GridBagConstraints.WEST;
		gridBagConstraints11.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints11.gridy = 1;
		lblPopulation = new JLabel();
		lblPopulation.setText("Population:");
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 0;
		gridBagConstraints7.weighty = 0.0D;
		gridBagConstraints7.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints7.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints7.gridy = 5;
		lblColorTitle = new JLabel();
		lblColorTitle.setText("Color:");
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
		this.setSize(351, 280);
		this.setLayout(new GridBagLayout());
		this.add(lblNameTitle, gridBagConstraints);
		this.add(getTxtName(), gridBagConstraints1);
		this.add(lblColorTitle, gridBagConstraints7);
		this.add(lblPopulation, gridBagConstraints11);
		this.add(lblWorkPositions, gridBagConstraints3);
		this.add(getBtnChangeColor(), gridBagConstraints12);
		this.add(getSpnPopulation(), gridBagConstraints41);
		this.add(getSpnWorkPositions(), gridBagConstraints5);
		this.add(getBtnResidentJobs(), gridBagConstraints2);
		this.add(getBtnWorkerJobs(), gridBagConstraints31);
		this.add(lblAverageAdultsPerHouse, gridBagConstraints13);
		this.add(lblAverageChildrenPerHouse, gridBagConstraints21);
		this.add(lblPossibilityAdultIsDriver, gridBagConstraints32);
		this.add(lblPossibilityAdultHasJob, gridBagConstraints4);
		this.add(getSpnAverageAdultsPerHouse(), gridBagConstraints51);
		this.add(getSpnAverageChildrenPerHouse(), gridBagConstraints6);
		this.add(getSpnPossibilityAdultIsDriver(), gridBagConstraints71);
		this.add(getSpnPossibilityAdultHasJob(), gridBagConstraints8);
		this.add(lblPossibilityDriverHasCar, gridBagConstraints9);
		this.add(getSpnPossibilityDriverHasCar(), gridBagConstraints10);
		this.add(getChkEnabled(), gridBagConstraints81);
	}

	protected void ChangeColor() {
		Color newColor = JColorChooser.showDialog(this, "Select flow color", currentArea.getColor());

		if (newColor != null) {
			currentArea.setColor(newColor);
			btnChangeColor.setBackground(newColor);
		}
	}

	/**
	 * This method initializes btnResidentJobs
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnResidentJobs"
	 */
	private JButton getBtnResidentJobs() {
		if (btnResidentJobs == null) {
			btnResidentJobs = new JButton();
			btnResidentJobs.setText("Select resident job types...");
			btnResidentJobs.setPreferredSize(new Dimension(190, 20));
			btnResidentJobs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectResidentJobTypes();
				}
			});
		}
		return btnResidentJobs;
	}

	/**
	 * This method initializes btnWorkerJobs
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnWorkerJobs"
	 */
	private JButton getBtnWorkerJobs() {
		if (btnWorkerJobs == null) {
			btnWorkerJobs = new JButton();
			btnWorkerJobs.setPreferredSize(new Dimension(190, 20));
			btnWorkerJobs.setText("Select worker job types...");
			btnWorkerJobs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectWorkerJobTypes();
				}
			});
		}
		return btnWorkerJobs;
	}

	/**
	 * Selects the job types of the people living in this area
	 * 
	 */
	protected void SelectResidentJobTypes() {
		// Create a type selection that contains all the job types of the
		// project, each with a 0 probability
		TypeSelection<JobType> projectJobTypes = new TypeSelection<JobType>(Project.getCurrentlyLoadedProject().getJobTypes());

		// Get a copy of the area's resident job types (so that if the user
		// cancels, the
		// changes won't be permanent)
		TypeSelection<JobType> residentJobTypes = new TypeSelection<JobType>(currentArea.getResidentsJobSelection());

		// Add to the selection any types that exist in the project but not in
		// the area
		residentJobTypes.addTypesNotInSelection(projectJobTypes);

		// Show the job selection dialog
		DlgSelectTypes<JobType> dlg = new DlgSelectTypes<JobType>(residentJobTypes);
		dlg.setVisible(true);

		if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
			// Remove any types that have 0 probability
			residentJobTypes.removeTypesWithZeroPercentage();

			// Set the selected types to the area
			currentArea.setResidentsJobSelection(residentJobTypes);
		}
	}

	/**
	 * Selects the job types of the people working in this area
	 * 
	 */
	protected void SelectWorkerJobTypes() {
		// Create a type selection that contains all the job types of the
		// project, each with a 0 probability
		TypeSelection<JobType> projectJobTypes = new TypeSelection<JobType>(Project.getCurrentlyLoadedProject().getJobTypes());

		// Get a copy of the area's worker job types (so that if the user
		// cancels, the
		// changes won't be permanent)
		TypeSelection<JobType> workerJobTypes = new TypeSelection<JobType>(currentArea.getWorkersJobSelection());

		// Add to the selection any types that exist in the project but not in
		// the area
		workerJobTypes.addTypesNotInSelection(projectJobTypes);

		// Show the job selection dialog
		DlgSelectTypes<JobType> dlg = new DlgSelectTypes<JobType>(workerJobTypes);
		dlg.setVisible(true);

		if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
			// Remove any types that have 0 probability
			workerJobTypes.removeTypesWithZeroPercentage();

			// Set the selected types to the area
			currentArea.setWorkersJobSelection(workerJobTypes);
		}
	}

	/**
	 * This method initializes spnAverageAdultsPerHouse
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnAverageAdultsPerHouse"
	 */
	private JSpinner getSpnAverageAdultsPerHouse() {
		if (spnAverageAdultsPerHouse == null) {
			spnAverageAdultsPerHouse = new JSpinner();
			spnAverageAdultsPerHouse.setPreferredSize(new Dimension(70, 20));
			spnAverageAdultsPerHouse.setModel(new SpinnerNumberModel(new Float(2), new Float(1), new Float(10), new Float(0.1)));
			spnAverageAdultsPerHouse.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentArea.setAverageAdultsPerHouse((Float) spnAverageAdultsPerHouse.getValue());
				}
			});
		}
		return spnAverageAdultsPerHouse;
	}

	/**
	 * This method initializes spnAverageChildrenPerHouse
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnAverageChildrenPerHouse"
	 */
	private JSpinner getSpnAverageChildrenPerHouse() {
		if (spnAverageChildrenPerHouse == null) {
			spnAverageChildrenPerHouse = new JSpinner();
			spnAverageChildrenPerHouse.setPreferredSize(new Dimension(70, 20));
			spnAverageChildrenPerHouse.setModel(new SpinnerNumberModel(new Float(1), new Float(0), new Float(10), new Float(0.1)));
			spnAverageChildrenPerHouse.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentArea.setAverageChildrenPerHouse((Float) spnAverageChildrenPerHouse.getValue());
				}
			});
		}
		return spnAverageChildrenPerHouse;
	}

	/**
	 * This method initializes spnPossibilityAdultIsDriver
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnPossibilityAdultIsDriver"
	 */
	private JSpinner getSpnPossibilityAdultIsDriver() {
		if (spnPossibilityAdultIsDriver == null) {
			spnPossibilityAdultIsDriver = new JSpinner();
			spnPossibilityAdultIsDriver.setPreferredSize(new Dimension(70, 20));
			spnPossibilityAdultIsDriver.setModel(new SpinnerNumberModel(new Float(0.5), new Float(0), new Float(1), new Float(0.01)));
			spnPossibilityAdultIsDriver.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentArea.setPossibilityAdultIsDriver((Float) spnPossibilityAdultIsDriver.getValue());
				}
			});
		}
		return spnPossibilityAdultIsDriver;
	}

	/**
	 * This method initializes spnPossibilityAdultHasJob
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnPossibilityAdultHasJob"
	 */
	private JSpinner getSpnPossibilityAdultHasJob() {
		if (spnPossibilityAdultHasJob == null) {
			spnPossibilityAdultHasJob = new JSpinner();
			spnPossibilityAdultHasJob.setPreferredSize(new Dimension(70, 20));
			spnPossibilityAdultHasJob.setModel(new SpinnerNumberModel(new Float(0.95), new Float(0), new Float(1), new Float(0.01)));
			spnPossibilityAdultHasJob.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentArea.setPossibilityAdultHasJob((Float) spnPossibilityAdultHasJob.getValue());
				}
			});
		}
		return spnPossibilityAdultHasJob;
	}

	/**
	 * This method initializes spnPossibilityDriverHasCar
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnPossibilityDriverHasCar"
	 */
	private JSpinner getSpnPossibilityDriverHasCar() {
		if (spnPossibilityDriverHasCar == null) {
			spnPossibilityDriverHasCar = new JSpinner();
			spnPossibilityDriverHasCar.setPreferredSize(new Dimension(70, 20));
			spnPossibilityDriverHasCar.setModel(new SpinnerNumberModel(new Float(0.95), new Float(0), new Float(1), new Float(0.01)));
			spnPossibilityDriverHasCar.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentArea.setPossibilityDriverHasCar((Float) spnPossibilityDriverHasCar.getValue());
				}
			});
		}
		return spnPossibilityDriverHasCar;
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
					currentArea.setEnabled(chkEnabled.isSelected());
				}
			});
		}
		return chkEnabled;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
