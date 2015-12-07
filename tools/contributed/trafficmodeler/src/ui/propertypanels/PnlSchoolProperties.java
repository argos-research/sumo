package ui.propertypanels;

import enums.SchoolType;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import trafficdefinition.School;
import ui.controls.SimTimeEdit;

/**
 * @author  PapaleonLe01
 */
public class PnlSchoolProperties extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox cmbType = null;

	private School currentSchool = null; // @jve:decl-index=0:

	private JLabel lblClosingTime = null;

	private JLabel lblNameTitle = null;

	private JLabel lblOpeningTime = null;

	private JLabel lblType = null;

	private SimTimeEdit steTimeClosing = null;

	private SimTimeEdit steTimeOpening = null;

	private JTextField txtName = null;

	private JCheckBox chkExportPolygon = null;

	private JCheckBox chkEnabled = null;

	private JLabel lblCapacity = null;

	private JSpinner spnCapacity = null;

	/**
	 * This is the default constructor
	 */
	public PnlSchoolProperties() {
		super();
		initialize();
	}

	public void setSchool(School school) {
		currentSchool = school;

		txtName.setText(currentSchool.getName());
		steTimeOpening.setValue(currentSchool.getTimeOpening());
		steTimeClosing.setValue(currentSchool.getTimeClosing());

		cmbType.setSelectedItem(currentSchool.getSchoolType());
		
		spnCapacity.setValue(currentSchool.getCapacity());
		
		chkExportPolygon.setSelected(currentSchool.getExportPolygon());
		chkEnabled.setSelected(currentSchool.isEnabled());
	}

	/**
	 * This method initializes cmbType
	 * @return  javax.swing.JComboBox
	 * @uml.property  name="cmbType"
	 */
	private JComboBox getCmbType() {
		if (cmbType == null) {
			cmbType = new JComboBox();
			cmbType.setPreferredSize(new Dimension(31, 20));
			cmbType.setModel(new DefaultComboBoxModel(SchoolType.values()));
			cmbType.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentSchool.setSchoolType((SchoolType) cmbType.getSelectedItem());
				}
			});
		}
		return cmbType;
	}

	/**
	 * This method initializes steTimeClosing
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steTimeClosing"
	 */
	private SimTimeEdit getSteTimeClosing() {
		if (steTimeClosing == null) {
			steTimeClosing = new SimTimeEdit();
			steTimeClosing.setPreferredSize(new Dimension(166, 45));
			steTimeClosing.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentSchool.setTimeClosing(steTimeClosing.getValue());
				}
			});
		}
		return steTimeClosing;
	}

	/**
	 * This method initializes steTimeOpening
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steTimeOpening"
	 */
	private SimTimeEdit getSteTimeOpening() {
		if (steTimeOpening == null) {
			steTimeOpening = new SimTimeEdit();
			steTimeOpening.setPreferredSize(new Dimension(166, 45));
			steTimeOpening.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentSchool.setTimeOpening(steTimeOpening.getValue());
				}
			});
		}
		return steTimeOpening;
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
					currentSchool.setName(txtName.getText());
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
		GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
		gridBagConstraints31.gridx = 1;
		gridBagConstraints31.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints31.fill = GridBagConstraints.NONE;
		gridBagConstraints31.insets = new Insets(5, 0, 0, 0);
		gridBagConstraints31.gridy = 4;
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 0;
		gridBagConstraints21.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints21.insets = new Insets(5, 10, 0, 0);
		gridBagConstraints21.weighty = 1.0D;
		gridBagConstraints21.gridy = 4;
		lblCapacity = new JLabel();
		lblCapacity.setText("Capacity:");
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 1;
		gridBagConstraints7.anchor = GridBagConstraints.WEST;
		gridBagConstraints7.gridy = 5;
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridx = 1;
		gridBagConstraints13.weighty = 1.0D;
		gridBagConstraints13.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints13.gridy = 6;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.insets = new Insets(3, 0, 3, 3);
		gridBagConstraints2.anchor = GridBagConstraints.WEST;
		gridBagConstraints2.gridy = 2;
		GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBagConstraints12.gridx = 1;
		gridBagConstraints12.anchor = GridBagConstraints.WEST;
		gridBagConstraints12.insets = new Insets(3, 0, 3, 3);
		gridBagConstraints12.gridy = 1;
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints6.gridy = 3;
		gridBagConstraints6.weightx = 1.0;
		gridBagConstraints6.anchor = GridBagConstraints.NORTH;
		gridBagConstraints6.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints6.gridx = 1;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 0;
		gridBagConstraints5.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints5.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints5.gridy = 3;
		lblType = new JLabel();
		lblType.setText("Type:");
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.insets = new Insets(3, 10, 0, 0);
		gridBagConstraints3.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints3.gridy = 2;
		lblClosingTime = new JLabel();
		lblClosingTime.setText("ClosingTime:");
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.insets = new Insets(3, 10, 0, 5);
		gridBagConstraints11.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints11.gridy = 1;
		lblOpeningTime = new JLabel();
		lblOpeningTime.setText("Opening time:");
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
		this.setSize(272, 200);
		this.setLayout(new GridBagLayout());
		this.add(lblNameTitle, gridBagConstraints);
		this.add(getTxtName(), gridBagConstraints1);
		this.add(lblOpeningTime, gridBagConstraints11);
		this.add(lblClosingTime, gridBagConstraints3);
		this.add(lblType, gridBagConstraints5);
		this.add(getCmbType(), gridBagConstraints6);
		this.add(getSteTimeOpening(), gridBagConstraints12);
		this.add(getSteTimeClosing(), gridBagConstraints2);
		this.add(getChkExportPolygon(), gridBagConstraints13);
		this.add(getChkEnabled(), gridBagConstraints7);
		this.add(lblCapacity, gridBagConstraints21);
		this.add(getSpnCapacity(), gridBagConstraints31);
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
					currentSchool.setExportPolygon(chkExportPolygon.isSelected());
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
					currentSchool.setEnabled(chkEnabled.isSelected());
				}
			});
		}
		return chkEnabled;
	}

	/**
	 * This method initializes spnCapacity	
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnCapacity"
	 */
	private JSpinner getSpnCapacity() {
		if (spnCapacity == null) {
			spnCapacity = new JSpinner();
			spnCapacity.setPreferredSize(new Dimension(50, 20));
			spnCapacity.setModel(new SpinnerNumberModel(1,1,10000,1));
			spnCapacity.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					currentSchool.setCapacity((Integer) spnCapacity.getValue());
				}
			});

		}
		return spnCapacity;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
