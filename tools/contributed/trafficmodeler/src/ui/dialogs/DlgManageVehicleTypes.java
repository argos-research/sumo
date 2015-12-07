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
import simulation.VehicleType;

/**
 * Dialog that enables the user to add, edit and delete vehicle types
 */
public class DlgManageVehicleTypes extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * List of vehicle types
	 */
	private List<VehicleType> vehicleTypes = null; 

	private JButton btnDelete = null;

	private JButton btnEdit = null;

	private JButton btnNew = null;

	private JButton btnOK = null;

	private JPanel jContentPane = null;

	private JLabel lblVehicleTypes = null;

	private JList lstVehicleTypes = null;

	private JScrollPane scrlVehicleTypes = null;
	
	/**
	 * @param owner
	 */
	public DlgManageVehicleTypes(Frame owner,List<VehicleType> vehicleTypes) {
		super(owner);
		initialize();
		
		this.vehicleTypes=vehicleTypes;
		
		//Fill list box with vehicle types
		lstVehicleTypes.setModel(new DefaultComboBoxModel(this.vehicleTypes.toArray()));
		
		setVisible(true);
	}

	/**
	 * @return  the vehicleTypes
	 * @uml.property  name="vehicleTypes"
	 */
	public List<VehicleType> getVehicleTypes() {
		return vehicleTypes;
	}

	/**
	 * This method initializes btnDelete	
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnDelete"
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setBounds(new Rectangle(174, 90, 101, 22));
			btnDelete.setEnabled(false);
			btnDelete.setToolTipText("Delete the currently selected vehicle type");
			btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
			btnDelete.setIcon(new ImageIcon(getClass().getResource("/resources/icons/deletevehicletype.png")));
			btnDelete.setText("Delete...");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DeleteVehicleType((VehicleType) lstVehicleTypes.getSelectedValue());
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
			btnEdit.setBounds(new Rectangle(174, 59, 101, 22));
			btnEdit.setEnabled(false);
			btnEdit.setHorizontalAlignment(SwingConstants.LEFT);
			btnEdit.setIcon(new ImageIcon(getClass().getResource("/resources/icons/editvehicletype.png")));
			btnEdit.setToolTipText("Edit the currently selected vehicle type");
			btnEdit.setText("Edit...");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EditVehicleType((VehicleType) lstVehicleTypes.getSelectedValue());
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
			btnNew.setBounds(new Rectangle(174, 26, 101, 22));
			btnNew.setHorizontalAlignment(SwingConstants.LEFT);
			btnNew.setIcon(new ImageIcon(getClass().getResource("/resources/icons/addvehicletype.png")));
			btnNew.setToolTipText("Create a new vehicle type");
			btnNew.setText("New...");
			btnNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					NewVehicleType();
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
			btnOK.setBounds(new Rectangle(174, 136, 101, 22));
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
			lblVehicleTypes = new JLabel();
			lblVehicleTypes.setBounds(new Rectangle(10, 3, 120, 21));
			lblVehicleTypes.setText("Vehicle types:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getScrlVehicleTypes(), null);
			jContentPane.add(lblVehicleTypes, null);
			jContentPane.add(getBtnNew(), null);
			jContentPane.add(getBtnEdit(), null);
			jContentPane.add(getBtnDelete(), null);
			jContentPane.add(getBtnOK(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes lstVehicleTypes	
	 * @return  javax.swing.JList
	 * @uml.property  name="lstVehicleTypes"
	 */
	private JList getLstVehicleTypes() {
		if (lstVehicleTypes == null) {
			lstVehicleTypes = new JList();
			lstVehicleTypes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					SelectedVehicleTypeChanged();
				}
			});
		}
		return lstVehicleTypes;
	}

	/**
	 * This method initializes scrlVehicleTypes	
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrlVehicleTypes"
	 */
	private JScrollPane getScrlVehicleTypes() {
		if (scrlVehicleTypes == null) {
			scrlVehicleTypes = new JScrollPane();
			scrlVehicleTypes.setBounds(new Rectangle(9, 26, 160, 134));
			scrlVehicleTypes.setViewportView(getLstVehicleTypes());
		}
		return scrlVehicleTypes;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(289, 198);
		setResizable(false);
		setModal(true);
		setTitle("Select vehicle types");
		setContentPane(getJContentPane());
	}

	/**
	 * Deletes the specified vehicle type
	 * @param type
	 */
	protected void DeleteVehicleType(VehicleType type) {
		//Don't let the user delete the default job type
		if(type.getName().equals("Default")){
			JOptionPane.showMessageDialog(this, "The default vehicle type can not be deleted");
			return;
		}
		
		if(JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the vehicle type "+type.getName()+"?")==JOptionPane.YES_OPTION){
			vehicleTypes.remove(type);
			((DefaultComboBoxModel)lstVehicleTypes.getModel()).removeElement(type);
			lstVehicleTypes.updateUI();
		}
	}

	/**
	 * Edits the specified vehicle type
	 * @param selectedJobType
	 */
	protected void EditVehicleType(VehicleType selectedVehicleType) {
		DlgAddEditVehicleType dlg = new DlgAddEditVehicleType(this,selectedVehicleType);
		
		if(dlg.getAnswer()==JOptionPane.OK_OPTION){
			lstVehicleTypes.updateUI();
		}
	}

	/**
	 * Creates a new vehicle type
	 *
	 */
	protected void NewVehicleType() {
		DlgAddEditVehicleType dlg = new DlgAddEditVehicleType(this);
		
		if(dlg.getAnswer()==JOptionPane.OK_OPTION){
			vehicleTypes.add(dlg.getVehicleType());
			((DefaultComboBoxModel)lstVehicleTypes.getModel()).addElement(dlg.getVehicleType());
			lstVehicleTypes.updateUI();
		}
	}

	protected void OKClicked() {
		setVisible(false);
	}

	protected void SelectedVehicleTypeChanged() {
		if(lstVehicleTypes.getSelectedValues().length==0){
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
		}
		else{
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);			
		}
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
