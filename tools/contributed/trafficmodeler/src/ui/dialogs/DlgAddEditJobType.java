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
import simulation.JobType;
import ui.controls.SimTimeEdit;

/**
 * Dialog that enables the user to create a new or modify an existing job type
 */
public class DlgAddEditJobType extends JDialog {

	/**
	 * The currently editing job type
	 */
	private JobType currentJobType = null;

	int answer = JOptionPane.CANCEL_OPTION;

	private static final long serialVersionUID = 1L;

	private JButton btnCancel = null;

	private JButton btnOK = null;

	private JPanel jContentPane = null;

	private JLabel lblEndingTime = null;

	private JLabel lblName = null;

	private JLabel lblStartingTime = null;

	private SimTimeEdit steEndingTime = null;

	private SimTimeEdit steStartingTime = null;

	private JTextField txtName = null;

	/**
	 * Constructor used to create a new job type
	 * 
	 * @param owner
	 */
	public DlgAddEditJobType(Dialog owner) {
		super(owner);
		initialize();

		currentJobType = null;

		this.setTitle("Create job type");
		this.setVisible(true);
	}

	/**
	 * Constructor used to edit an existing job type
	 * 
	 * @param owner
	 */
	public DlgAddEditJobType(Dialog owner, JobType jobTypeToEdit) {
		super(owner);
		initialize();

		currentJobType = jobTypeToEdit;

		txtName.setText(jobTypeToEdit.getName());
		steStartingTime.setValue(jobTypeToEdit.getStartingTime());
		steEndingTime.setValue(jobTypeToEdit.getEndingTime());

		this.setTitle("Edit job type");

		// Don't let the user modify the default job type
		if (jobTypeToEdit.getName().equals("Default")) {
			DisableDialog();
		}

		this.setVisible(true);
	}

	/**
	 * @return  the answer
	 * @uml.property  name="answer"
	 */
	public int getAnswer() {
		return answer;
	}

	public JobType getJobType() {
		return currentJobType;
	}

	/**
	 * Disables the dialog when the user tries to edit the default job type
	 * 
	 */
	private void DisableDialog() {
		txtName.setEnabled(false);
		steStartingTime.setEnabled(false);
		steEndingTime.setEnabled(false);

		btnOK.setEnabled(false);

		this.setTitle("Default job type");
	}

	/**
	 * This method initializes btnCancel
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCancel"
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(131, 154, 76, 21));
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
			btnOK.setBounds(new Rectangle(214, 154, 71, 21));
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
			lblEndingTime = new JLabel();
			lblEndingTime.setBounds(new Rectangle(15, 93, 84, 18));
			lblEndingTime.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lblEndingTime.setText("Ending time:");
			lblStartingTime = new JLabel();
			lblStartingTime.setBounds(new Rectangle(15, 31, 84, 18));
			lblStartingTime.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lblStartingTime.setText("Starting time:");
			lblName = new JLabel();
			lblName.setBounds(new Rectangle(15, 10, 84, 18));
			lblName.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			lblName.setText("Name:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lblName, null);
			jContentPane.add(getTxtName(), null);
			jContentPane.add(lblStartingTime, null);
			jContentPane.add(lblEndingTime, null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getSteStartingTime(), null);
			jContentPane.add(getSteEndingTime(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes steEndingTime
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steEndingTime"
	 */
	private SimTimeEdit getSteEndingTime() {
		if (steEndingTime == null) {
			steEndingTime = new SimTimeEdit();
			steEndingTime.setBounds(new Rectangle(110, 95, 174, 54));
		}
		return steEndingTime;
	}

	/**
	 * This method initializes steStartingTime
	 * @return  ui.SimTimeEdit
	 * @uml.property  name="steStartingTime"
	 */
	private SimTimeEdit getSteStartingTime() {
		if (steStartingTime == null) {
			steStartingTime = new SimTimeEdit();
			steStartingTime.setBounds(new Rectangle(109, 33, 166, 54));
		}
		return steStartingTime;
	}

	/**
	 * This method initializes txtName
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtName"
	 */
	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setBounds(new Rectangle(110, 10, 176, 20));
		}
		return txtName;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(297, 213);
		this.setResizable(false);
		this.setModal(true);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setTitle("Job type");
		this.setContentPane(getJContentPane());
	}

	/**
	 * Called when the user presses the cancel button
	 *
	 */
	protected void Cancel() {
		answer = JOptionPane.CANCEL_OPTION;

		this.setVisible(false);
	}

	/**
	 * Called when the user presses the OK button
	 *
	 */
	protected void OK() {
		//Validate
		if (txtName.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter a name");
			return;
		}

		if (steEndingTime.getValue() <= steStartingTime.getValue()) {
			JOptionPane.showMessageDialog(this, "The ending time must be later than the starting time");
			return;
		}

		//Create a new job type or edit the current job type
		if (currentJobType == null) {
			currentJobType = new JobType(txtName.getText(), steStartingTime.getValue(), steEndingTime.getValue());
		} else {
			currentJobType.setName(txtName.getText());
			currentJobType.setStartingTime(steStartingTime.getValue());
			currentJobType.setEndingTime(steEndingTime.getValue());
		}

		//Hide the dialog
		answer = JOptionPane.OK_OPTION;
		this.setVisible(false);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
