package ui.dialogs;

import java.awt.Frame;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import simulation.JobType;

/**
 * Dialog that enables the user to add, edit and delete job types
 */
public class DlgManageJobTypes extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * List of job types
	 */
	private List<JobType> jobTypes = null;

	private JButton btnDelete = null;

	private JButton btnEdit = null;

	private JButton btnNew = null;

	private JButton btnOK = null;

	private JPanel jContentPane = null;

	private JLabel lblJobTypes = null;

	private JList lstJobTypes = null;

	private JScrollPane scrlJobTypes = null;

	/**
	 * @param owner
	 */
	public DlgManageJobTypes(Frame owner, List<JobType> jobTypes) {
		super(owner);
		initialize();

		this.jobTypes = jobTypes;

		//Fill list box with job types
		lstJobTypes.setModel(new DefaultComboBoxModel(this.jobTypes.toArray()));

		setVisible(true);
	}

	/**
	 * @return  the jobTypes
	 * @uml.property  name="jobTypes"
	 */
	public List<JobType> getJobTypes() {
		return jobTypes;
	}

	/**
	 * This method initializes btnDelete
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnDelete"
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setBounds(new Rectangle(220, 89, 99, 23));
			btnDelete.setEnabled(false);
			btnDelete.setToolTipText("Delete the currently selected job type");
			btnDelete.setText("Delete...");
			btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
			btnDelete.setIcon(new ImageIcon(getClass().getResource("/resources/icons/deletejobtype.png")));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DeleteJobType((JobType) lstJobTypes.getSelectedValue());
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnEdit
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnEdit"
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setBounds(new Rectangle(220, 59, 99, 23));
			btnEdit.setEnabled(false);
			btnEdit.setToolTipText("Edit the currently selected job type");
			btnEdit.setHorizontalAlignment(SwingConstants.LEFT);
			btnEdit.setText("Edit...");
			btnEdit.setIcon(new ImageIcon(getClass().getResource("/resources/icons/editjobtype.png")));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EditJobType((JobType) lstJobTypes.getSelectedValue());
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnNew
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnNew"
	 */
	private JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton();
			btnNew.setBounds(new Rectangle(220, 27, 99, 23));
			btnNew.setToolTipText("Create a new job type");
			btnNew.setText("New...");
			btnNew.setHorizontalAlignment(SwingConstants.LEFT);
			btnNew.setIcon(new ImageIcon(getClass().getResource("/resources/icons/addjobtype.png")));
			btnNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					NewJobType();
				}
			});
		}
		return btnNew;
	}

	/**
	 * This method initializes btnOK
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnOK"
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(new Rectangle(220, 133, 99, 23));
			btnOK.setText("OK");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					OKClicked();
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
			lblJobTypes = new JLabel();
			lblJobTypes.setBounds(new Rectangle(10, 3, 120, 21));
			lblJobTypes.setText("Job types:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getScrlJobTypes(), null);
			jContentPane.add(lblJobTypes, null);
			jContentPane.add(getBtnNew(), null);
			jContentPane.add(getBtnEdit(), null);
			jContentPane.add(getBtnDelete(), null);
			jContentPane.add(getBtnOK(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes lstJobTypes
	 * @return  javax.swing.JList
	 * @uml.property  name="lstJobTypes"
	 */
	private JList getLstJobTypes() {
		if (lstJobTypes == null) {
			lstJobTypes = new JList();
			lstJobTypes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					SelectedJobTypeChanged();
				}
			});
		}
		return lstJobTypes;
	}

	/**
	 * This method initializes scrlJobTypes
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrlJobTypes"
	 */
	private JScrollPane getScrlJobTypes() {
		if (scrlJobTypes == null) {
			scrlJobTypes = new JScrollPane();
			scrlJobTypes.setBounds(new Rectangle(9, 26, 205, 132));
			scrlJobTypes.setViewportView(getLstJobTypes());
		}
		return scrlJobTypes;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(335, 199);
		setResizable(false);
		setModal(true);
		setTitle("Manage job types");
		setContentPane(getJContentPane());
	}

	/**
	 * Deletes the specified job type
	 * @param type
	 */
	protected void DeleteJobType(JobType type) {
		//Don't let the user delete the default job type
		if (type.getName().equals("Default")) {
			JOptionPane.showMessageDialog(this, "The default job type can not be deleted");
			return;
		}

		if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the job type " + type.getName() + "?") == JOptionPane.YES_OPTION) {
			jobTypes.remove(type);
			((DefaultComboBoxModel) lstJobTypes.getModel()).removeElement(type);
			lstJobTypes.updateUI();
		}
	}

	/**
	 * Edits the specified job type
	 * @param selectedJobType
	 */
	protected void EditJobType(JobType selectedJobType) {
		DlgAddEditJobType dlg = new DlgAddEditJobType(this, selectedJobType);

		if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
			lstJobTypes.updateUI();
		}
	}

	/**
	 * Creates a new job type
	 *
	 */
	protected void NewJobType() {
		DlgAddEditJobType dlg = new DlgAddEditJobType(this);

		if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
			jobTypes.add(dlg.getJobType());
			((DefaultComboBoxModel) lstJobTypes.getModel()).addElement(dlg.getJobType());
			lstJobTypes.updateUI();
		}
	}

	protected void OKClicked() {
		setVisible(false);
	}

	/**
	 * Called when the user select a job type from the list
	 *
	 */
	protected void SelectedJobTypeChanged() {
		if (lstJobTypes.getSelectedValues().length == 0) {
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
		} else {
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
