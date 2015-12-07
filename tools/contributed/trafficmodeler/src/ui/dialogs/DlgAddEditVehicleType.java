package ui.dialogs;

import java.awt.ComponentOrientation;
import java.awt.Dialog;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import simulation.VehicleType;

/**
 * Dialog that enables users to create a new (or modify an existing) vehicle type
 */
public class DlgAddEditVehicleType extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * The vehicle type being edited
	 */
	private VehicleType currentVehicleType = null; // @jve:decl-index=0:

	private int answer = JOptionPane.CANCEL_OPTION;

	private JButton btnCancel = null;

	private JButton btnOK = null;

	private JPanel jContentPane = null;

	private JLabel lblAcceleration = null;

	private JLabel lblDeceleration = null;

	private JLabel lblDriverImperfection = null;

	private JLabel lblLength = null;

	private JLabel lblMaximumSpeed = null;

	private JLabel lblName = null;

	private JTextField txtAcceleration = null;

	private JTextField txtDeceleration = null;

	private JTextField txtDriverImperfection = null;

	private JTextField txtLength = null;

	private JTextField txtMaximumSpeed = null;

	private JTextField txtName = null;

	private JLabel lblMeters = null;

	private JLabel lblMs = null;

	private JLabel lblImp = null;

	private JLabel lblMs2 = null;

	private JLabel lblMs21 = null;

	/**
	 * Constructor used to create a new vehicle type
	 * @param owner
	 */
	public DlgAddEditVehicleType(Dialog owner) {
		super(owner);
		initialize();

		currentVehicleType = null;

		setTitle("Create vehicle type");
		setVisible(true);
	}

	/**
	 * Constructor used to edit a vehicle type
	 * @param owner
	 */
	public DlgAddEditVehicleType(Dialog owner, VehicleType vehicleTypeToEdit) {
		super(owner);
		initialize();

		currentVehicleType = vehicleTypeToEdit;

		//Set control values to vehicle values
		txtName.setText(vehicleTypeToEdit.getName());
		txtAcceleration.setText(String.valueOf(vehicleTypeToEdit.getAcceleration()));
		txtDeceleration.setText(String.valueOf(vehicleTypeToEdit.getDeceleration()));
		txtDriverImperfection.setText(String.valueOf(vehicleTypeToEdit.getDriverImperfection()));
		txtLength.setText(String.valueOf(vehicleTypeToEdit.getLength()));
		txtMaximumSpeed.setText(String.valueOf(vehicleTypeToEdit.getMaximumSpeed()));

		setTitle("Edit vehicle type");

		//Disable editing of the default vehicle type
		if (vehicleTypeToEdit.getName().equals("Default")) {
			DisableDialog();
		}

		setVisible(true);
	}

	/**
	 * @return  the answer
	 * @uml.property  name="answer"
	 */
	public int getAnswer() {
		return answer;
	}

	public VehicleType getVehicleType() {
		return currentVehicleType;
	}

	/**
	 * Disables the dialog
	 *
	 */
	private void DisableDialog() {
		txtName.setEnabled(false);
		txtAcceleration.setEnabled(false);
		txtDeceleration.setEnabled(false);
		txtDriverImperfection.setEnabled(false);
		txtMaximumSpeed.setEnabled(false);
		txtLength.setEnabled(false);

		btnOK.setEnabled(false);

		setTitle("Default vehicle type");
	}

	/**
	 * This method initializes btnCancel
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCancel"
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(155, 172, 76, 21));
			btnCancel.setText("Cancel");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Cancel();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnOK
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnOK"
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(240, 172, 71, 21));
			btnOK.setText("OK");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					OK();
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes jContentPane
	 * @return  javax.swing.JPanel
	 * @uml.property  name="jContentPane"
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblMs21 = new JLabel();
			lblMs21.setBounds(new Rectangle(206, 63, 38, 16));
			lblMs21.setText("m/s2");
			lblMs2 = new JLabel();
			lblMs2.setBounds(new Rectangle(206, 36, 41, 16));
			lblMs2.setText("m/s2");
			lblImp = new JLabel();
			lblImp.setBounds(new Rectangle(206, 91, 38, 16));
			lblImp.setText("0-1");
			lblMs = new JLabel();
			lblMs.setBounds(new Rectangle(206, 117, 38, 16));
			lblMs.setText("m/s");
			lblMeters = new JLabel();
			lblMeters.setBounds(new Rectangle(206, 145, 53, 16));
			lblMeters.setText("meters");
			lblLength = new JLabel();
			lblLength.setBounds(new Rectangle(34, 144, 97, 16));
			lblLength.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lblLength.setText("Length:");
			lblDriverImperfection = new JLabel();
			lblDriverImperfection.setBounds(new Rectangle(11, 89, 121, 16));
			lblDriverImperfection.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lblDriverImperfection.setText("Driver imperfection:");
			lblDeceleration = new JLabel();
			lblDeceleration.setBounds(new Rectangle(47, 63, 85, 16));
			lblDeceleration.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lblDeceleration.setText("Deceleration:");
			lblAcceleration = new JLabel();
			lblAcceleration.setBounds(new Rectangle(49, 37, 83, 16));
			lblAcceleration.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lblAcceleration.setText("Acceleration:");
			lblMaximumSpeed = new JLabel();
			lblMaximumSpeed.setBounds(new Rectangle(23, 117, 109, 16));
			lblMaximumSpeed.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lblMaximumSpeed.setText("Maximum speed:");
			lblName = new JLabel();
			lblName.setBounds(new Rectangle(48, 9, 84, 18));
			lblName.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lblName.setText("Name:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getTxtName(), null);
			jContentPane.add(lblName, null);
			jContentPane.add(lblMaximumSpeed, null);
			jContentPane.add(lblAcceleration, null);
			jContentPane.add(lblDeceleration, null);
			jContentPane.add(lblDriverImperfection, null);
			jContentPane.add(lblLength, null);
			jContentPane.add(getTxtAcceleration(), null);
			jContentPane.add(getTxtDeceleration(), null);
			jContentPane.add(getTxtDriverImperfection(), null);
			jContentPane.add(getTxtMaximumSpeed(), null);
			jContentPane.add(getTxtLength(), null);
			jContentPane.add(lblMeters, null);
			jContentPane.add(lblMs, null);
			jContentPane.add(lblImp, null);
			jContentPane.add(lblMs2, null);
			jContentPane.add(lblMs21, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes txtAcceleration
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtAcceleration"
	 */
	private JTextField getTxtAcceleration() {
		if (txtAcceleration == null) {
			txtAcceleration = new JTextField();
			txtAcceleration.setBounds(new Rectangle(139, 35, 61, 20));
		}
		return txtAcceleration;
	}

	/**
	 * This method initializes txtDeceleration
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtDeceleration"
	 */
	private JTextField getTxtDeceleration() {
		if (txtDeceleration == null) {
			txtDeceleration = new JTextField();
			txtDeceleration.setBounds(new Rectangle(138, 62, 62, 20));
		}
		return txtDeceleration;
	}

	/**
	 * This method initializes txtDriverImperfection
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtDriverImperfection"
	 */
	private JTextField getTxtDriverImperfection() {
		if (txtDriverImperfection == null) {
			txtDriverImperfection = new JTextField();
			txtDriverImperfection.setBounds(new Rectangle(138, 89, 64, 20));
		}
		return txtDriverImperfection;
	}

	/**
	 * This method initializes txtLength
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtLength"
	 */
	private JTextField getTxtLength() {
		if (txtLength == null) {
			txtLength = new JTextField();
			txtLength.setBounds(new Rectangle(137, 143, 66, 20));
		}
		return txtLength;
	}

	/**
	 * This method initializes txtMaximumSpeed
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtMaximumSpeed"
	 */
	private JTextField getTxtMaximumSpeed() {
		if (txtMaximumSpeed == null) {
			txtMaximumSpeed = new JTextField();
			txtMaximumSpeed.setBounds(new Rectangle(138, 116, 65, 20));
		}
		return txtMaximumSpeed;
	}

	/**
	 * This method initializes txtName
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtName"
	 */
	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setBounds(new Rectangle(139, 8, 176, 20));
		}
		return txtName;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(332, 233);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Vehicle type");
		setContentPane(getJContentPane());
	}

	/**
	 * Called when the user presses the Cancel button
	 *
	 */
	protected void Cancel() {
		answer = JOptionPane.CANCEL_OPTION;

		setVisible(false);
	}

	/**
	 * Called when the user presses the OK button
	 *
	 */
	protected void OK() {
		//Perform validations
		if (txtName.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter a name");
			return;
		}
		if (txtAcceleration.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter the vehicle's acceleration rate");
			return;
		}
		if (txtDeceleration.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter the vehicle's deceleration rate");
			return;
		}
		if (txtDriverImperfection.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter the driver's imperfection");
			return;
		}
		if (txtLength.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter the vehicle's length");
			return;
		}
		if (txtMaximumSpeed.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter the vehicle's maximum speed");
			return;
		}

		//Create new vehicle type
		if (currentVehicleType == null) {
			currentVehicleType = new VehicleType(txtName.getText(), Float.parseFloat(txtAcceleration.getText()), Float.parseFloat(txtDeceleration.getText()), Float.parseFloat(txtDriverImperfection.getText()), Integer.parseInt(txtLength.getText()), Integer.parseInt(txtMaximumSpeed.getText()));
		} 
		//Modify editing vehicle type
		else {
			currentVehicleType.setName(txtName.getText());
			currentVehicleType.setAcceleration(Float.parseFloat(txtAcceleration.getText()));
			currentVehicleType.setDeceleration(Float.parseFloat(txtDeceleration.getText()));
			currentVehicleType.setDriverImperfection(Float.parseFloat(txtDriverImperfection.getText()));
			currentVehicleType.setLength(Integer.parseInt(txtLength.getText()));
			currentVehicleType.setMaximumSpeed(Integer.parseInt(txtMaximumSpeed.getText()));
		}

		answer = JOptionPane.OK_OPTION;

		setVisible(false);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
