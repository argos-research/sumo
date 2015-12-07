package ui.dialogs;

import classes.Pair;
import classes.Project;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import simulation.Simulation;
import trafficdefinition.TrafficDefinitionLayer;
import ui.ExportLayersTableModel;
import ui.controls.SimTimeEdit;

/**
 * Dialog used to export simulation
 */
public class DlgExportSimulation extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * The list of the project's traffic definition layers.Each layer is
	 * associated with a boolean value which shows whether the user has selected
	 * to export the layer or not
	 */
	private List<Pair<TrafficDefinitionLayer,Boolean>> layersToExport = new ArrayList<Pair<TrafficDefinitionLayer,Boolean>>();

	/**
	 * The simulation object being exported
	 */
	private Simulation simulation;

	private int answer = JOptionPane.CANCEL_OPTION;

	private JButton btnBrowse = null;

	private JButton btnCancel = null;

	private JButton btnExport = null;

	private JPanel jContentPane = null;

	private JLabel lblExportPath = null;

	private JLabel lblLayersToExport = null;

	private JTextField txtExportPath = null;

	private JLabel lblName = null;

	private JTextField txtSimulationName = null;

	private SimTimeEdit steBeginTime = null;

	private SimTimeEdit steEndTime = null;

	private JPanel pnlEndTime = null;

	private JPanel pnlBeginTime = null;

	private JCheckBox chkExportPolygons = null;

	private JScrollPane scrlLayersToExport = null;

	private JTable tblLayersToExport = null;

	private JCheckBox chkRoadNetworkSpeedInKMH = null;

	/**
	 * @param owner
	 */
	public DlgExportSimulation(Frame owner, Project project) {
		super(owner);
		initialize();

		this.simulation = project.getSimulation();

		// Set control values based on simulation values
		txtExportPath.setText(simulation.getExportPath());
		txtSimulationName.setText(simulation.getName());
		steBeginTime.setValue(simulation.getBeginTime());
		steEndTime.setValue(simulation.getEndTime());
		chkExportPolygons.setSelected(simulation.getExportPolygons());
		chkRoadNetworkSpeedInKMH.setSelected(simulation.isRoadNetworkSpeedInKMH());
		
		// Fill the table with the project's layers
		for(TrafficDefinitionLayer layer:project.getTrafficLayers()){
			this.layersToExport.add(new Pair<TrafficDefinitionLayer,Boolean>(layer,true));
		}
		
		tblLayersToExport.setModel(new ExportLayersTableModel(layersToExport));

	}

	/**
	 * @return  the answer
	 * @uml.property  name="answer"
	 */
	public int getAnswer() {
		return answer;
	}

	/**
	 * Returns the list of selected layers to export
	 * 
	 * @return
	 */
	public List<TrafficDefinitionLayer> getSelectedLayers() {
		List<TrafficDefinitionLayer> selectedLayers = new ArrayList<TrafficDefinitionLayer>();
		
		for(Pair<TrafficDefinitionLayer, Boolean> l:layersToExport){
			if(l.getSecond()){
				selectedLayers.add(l.getFirst());
			}
		}
		
		return selectedLayers;
	}

	/**
	 * This method initializes btnBrowse
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnBrowse"
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setBounds(new Rectangle(342, 35, 93, 20));
			btnBrowse.setText("Browse...");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BrowseForExportPath();
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * This method initializes btnCancel
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCancel"
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new Rectangle(281, 290, 73, 24));
			btnCancel.setText("Cancel");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CancelExport();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes btnExport
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnExport"
	 */
	private JButton getBtnExport() {
		if (btnExport == null) {
			btnExport = new JButton();
			btnExport.setBounds(new Rectangle(361, 290, 74, 24));
			btnExport.setText("Export");
			btnExport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Export();
				}
			});
		}
		return btnExport;
	}

	/**
	 * This method initializes jContentPane
	 * @return  javax.swing.JPanel
	 * @uml.property  name="jContentPane"
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblName = new JLabel();
			lblName.setBounds(new Rectangle(9, 63, 112, 23));
			lblName.setText("Simulation name:");
			lblLayersToExport = new JLabel();
			lblLayersToExport.setBounds(new Rectangle(10, 207, 160, 18));
			lblLayersToExport.setText("Select layers to export:");
			lblExportPath = new JLabel();
			lblExportPath.setBounds(new Rectangle(9, 12, 73, 19));
			lblExportPath.setText("Export path:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lblExportPath, null);
			jContentPane.add(getTxtExportPath(), null);
			jContentPane.add(getBtnBrowse(), null);
			jContentPane.add(lblLayersToExport, null);
			jContentPane.add(getBtnExport(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(lblName, null);
			jContentPane.add(getTxtSimulationName(), null);
			jContentPane.add(getPnlEndTime(), null);
			jContentPane.add(getPnlBeginTime(), null);
			jContentPane.add(getChkExportPolygons(), null);
			jContentPane.add(getScrlLayersToExport(), null);
			jContentPane.add(getChkRoadNetworkSpeedInKMH(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes txtExportPath
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtExportPath"
	 */
	private JTextField getTxtExportPath() {
		if (txtExportPath == null) {
			txtExportPath = new JTextField();
			txtExportPath.setBounds(new Rectangle(9, 35, 330, 21));
		}
		return txtExportPath;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(448, 352);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setTitle("Export Simulation");
		setContentPane(getJContentPane());
	}

	/**
	 * Displays the folder selection dialog
	 * 
	 */
	protected void BrowseForExportPath() {
		JFileChooser f = new JFileChooser();

		f.setDialogTitle("Select export path");
		f.setAcceptAllFileFilterUsed(false);
		// f.setFileFilter(new FolderFileFilter());
		f.setMultiSelectionEnabled(false);
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (f.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			txtExportPath.setText(f.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 * Called when the user has pressed the Cancel buttn
	 * 
	 */
	protected void CancelExport() {
		answer = JOptionPane.CANCEL_OPTION;

		setVisible(false);
	}

	/**
	 * Called when the user has pressed the Export button
	 * 
	 */
	protected void Export() {
		answer = JOptionPane.OK_OPTION;

		// Validations
		if (txtExportPath.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please select the export path");
			return;
		}

		if (txtSimulationName.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter a name for the simulation");
			return;
		}

		if (steEndTime.getValue() <= steBeginTime.getValue()) {
			JOptionPane.showMessageDialog(this, "The simulation's end time must be later that its begin time");
			return;
		}

		File exportFolder = new File(txtExportPath.getText().trim());

		if (!exportFolder.exists()) {
			JOptionPane.showMessageDialog(this, "The specified export folder does not exist");
			return;
		}

		// Set values to simulation
		simulation.setExportPath(exportFolder.getAbsolutePath());
		simulation.setBeginTime(steBeginTime.getValue());
		simulation.setEndTime(steEndTime.getValue());
		simulation.setName(txtSimulationName.getText().trim());
		simulation.setExportPolygons(chkExportPolygons.isSelected());
		simulation.setRoadNetworkSpeedInKMH(chkRoadNetworkSpeedInKMH.isSelected());
		
		setVisible(false);
	}

	/**
	 * This method initializes txtSimulationName
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtSimulationName"
	 */
	private JTextField getTxtSimulationName() {
		if (txtSimulationName == null) {
			txtSimulationName = new JTextField();
			txtSimulationName.setBounds(new Rectangle(10, 89, 169, 21));
		}
		return txtSimulationName;
	}

	/**
	 * This method initializes steBeginTime
	 * @return  ui.controls.SimTimeEdit
	 * @uml.property  name="steBeginTime"
	 */
	private SimTimeEdit getSteBeginTime() {
		if (steBeginTime == null) {
			steBeginTime = new SimTimeEdit();
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
		}
		return steEndTime;
	}

	/**
	 * This method initializes pnlEndTime
	 * @return  javax.swing.JPanel
	 * @uml.property  name="pnlEndTime"
	 */
	private JPanel getPnlEndTime() {
		if (pnlEndTime == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			pnlEndTime = new JPanel();
			pnlEndTime.setLayout(new GridBagLayout());
			pnlEndTime.setBounds(new Rectangle(223, 120, 206, 77));
			pnlEndTime.setBorder(BorderFactory.createTitledBorder(null, "End time", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			pnlEndTime.add(getSteEndTime(), gridBagConstraints);
		}
		return pnlEndTime;
	}

	/**
	 * This method initializes pnlBeginTime
	 * @return  javax.swing.JPanel
	 * @uml.property  name="pnlBeginTime"
	 */
	private JPanel getPnlBeginTime() {
		if (pnlBeginTime == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			pnlBeginTime = new JPanel();
			pnlBeginTime.setLayout(new GridBagLayout());
			pnlBeginTime.setBounds(new Rectangle(10, 120, 206, 77));
			pnlBeginTime.setBorder(BorderFactory.createTitledBorder(null, "Begin time", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			pnlBeginTime.add(getSteBeginTime(), gridBagConstraints1);
		}
		return pnlBeginTime;
	}

	/**
	 * This method initializes chkExportPolygons
	 * @return  javax.swing.JCheckBox
	 * @uml.property  name="chkExportPolygons"
	 */
	private JCheckBox getChkExportPolygons() {
		if (chkExportPolygons == null) {
			chkExportPolygons = new JCheckBox();
			chkExportPolygons.setBounds(new Rectangle(280, 258, 138, 21));
			chkExportPolygons.setText("Export polygons");
		}
		return chkExportPolygons;
	}

	/**
	 * This method initializes scrlLayersToExport	
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrlLayersToExport"
	 */
	private JScrollPane getScrlLayersToExport() {
		if (scrlLayersToExport == null) {
			scrlLayersToExport = new JScrollPane();
			scrlLayersToExport.setBounds(new Rectangle(11, 229, 267, 84));
			scrlLayersToExport.setViewportView(getTblLayersToExport());
		}
		return scrlLayersToExport;
	}

	/**
	 * This method initializes tblLayersToExport	
	 * @return  javax.swing.JTable
	 * @uml.property  name="tblLayersToExport"
	 */
	private JTable getTblLayersToExport() {
		if (tblLayersToExport == null) {
			tblLayersToExport = new JTable();
		}
		return tblLayersToExport;
	}

	/**
	 * This method initializes chkRoadNetworkSpeedInKMH	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getChkRoadNetworkSpeedInKMH() {
		if (chkRoadNetworkSpeedInKMH == null) {
			chkRoadNetworkSpeedInKMH = new JCheckBox();
			chkRoadNetworkSpeedInKMH.setBounds(new Rectangle(197, 88, 232, 21));
			chkRoadNetworkSpeedInKMH.setText("Road network speed in kmh");
		}
		return chkRoadNetworkSpeedInKMH;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
