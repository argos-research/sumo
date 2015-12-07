package ui.dialogs;

import java.awt.Frame;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import trafficdefinition.RandomTrafficDefinitionLayer;
import ui.controls.SimTimeEdit;

/**
 * Dialog for modifying the application settings
 */
public class DlgRandomLayerProperties extends JDialog {

	private static final long serialVersionUID = 1L;

	private JButton btnCancel = null;

	private JButton btnOK = null;

	private JPanel jContentPane = null;

	private RandomTrafficDefinitionLayer layer = null;

	private JLabel lblBeginTime = null;

	private JLabel lblEndingTime = null;

	private SimTimeEdit steBeginTime = null;

	private SimTimeEdit steEndTime = null;

	private JLabel lblVehicles = null;

	private JSpinner spnVehicles = null;
	
	/**
	 * @param owner
	 */
	public DlgRandomLayerProperties(Frame owner, RandomTrafficDefinitionLayer layer) {
		super(owner);
		initialize();
		
		this.layer = layer;
		
		steBeginTime.setValue(layer.getStartingTime());
		steEndTime.setValue(layer.getEndingTime());
		spnVehicles.setValue(layer.getVehiclesPerSecond());
		
		setVisible(true);
	}

	/**
	 * This method initializes btnCancel
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCancel"
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(283, 92, 73, 26));
			btnCancel.setText("Cancel");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setVisible(false);
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
			btnOK.setBounds(new Rectangle(283, 123, 73, 26));
			btnOK.setText("OK");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SaveProperties();

					setVisible(false);
				}
			});
		}
		return btnOK;
	}

	/**
	 * Assigns the modified properties to the layer
	 *o
	 */
	protected void SaveProperties() {
		layer.setStartingTime(steBeginTime.getValue());
		layer.setEndingTime(steEndTime.getValue());
		layer.setVehiclesPerSecond((Integer)spnVehicles.getValue());
	}

	/**
	 * This method initializes jContentPane
	 * @return  javax.swing.JPanel
	 * @uml.property  name="jContentPane"
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblVehicles = new JLabel();
			lblVehicles.setBounds(new Rectangle(18, 129, 173, 16));
			lblVehicles.setText("Average vehicles per second:");
			lblEndingTime = new JLabel();
			lblEndingTime.setBounds(new Rectangle(13, 71, 80, 18));
			lblEndingTime.setText("Ending time:");
			lblBeginTime = new JLabel();
			lblBeginTime.setBounds(new Rectangle(12, 12, 82, 21));
			lblBeginTime.setText("Starting time:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(lblBeginTime, null);
			jContentPane.add(lblEndingTime, null);
			jContentPane.add(getSteBeginTime(), null);
			jContentPane.add(getSteEndTime(), null);
			jContentPane.add(lblVehicles, null);
			jContentPane.add(getSpnVehicles(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(373, 191);
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Random traffic layer properties");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes steBeginTime	
	 * @return  ui.controls.SimTimeEdit
	 * @uml.property  name="steBeginTime"
	 */
	private SimTimeEdit getSteBeginTime() {
		if (steBeginTime == null) {
			steBeginTime = new SimTimeEdit();
			steBeginTime.setBounds(new Rectangle(102, 12, 166, 48));
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
			steEndTime.setBounds(new Rectangle(103, 71, 166, 46));
		}
		return steEndTime;
	}

	/**
	 * This method initializes spnVehicles	
	 * @return  javax.swing.JSpinner
	 * @uml.property  name="spnVehicles"
	 */
	private JSpinner getSpnVehicles() {
		if (spnVehicles == null) {
			spnVehicles = new JSpinner();
			spnVehicles.setBounds(new Rectangle(193, 128, 67, 20));
			spnVehicles.setModel(new SpinnerNumberModel(1,1,100000,1));
		}
		return spnVehicles;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
