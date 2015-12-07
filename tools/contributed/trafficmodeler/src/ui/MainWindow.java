package ui;

import helpers.ApplicationSettings;
import helpers.Broadcaster;
import interfaces.CurrentLayerChangedListener;
import interfaces.ProjectChangedListener;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import roadnetwork.RoadNetwork;
import simulation.SimulationExporter;
import simulation.VehicleType;
import trafficdefinition.ActivityBasedTrafficDefinitionLayer;
import trafficdefinition.RandomTrafficDefinitionLayer;
import trafficdefinition.TrafficDefinitionLayer;
import trafficdefinition.UserDefinedTrafficDefinitionLayer;
import ui.dialogs.DlgExportSimulation;
import ui.dialogs.DlgManageJobTypes;
import ui.dialogs.DlgManageVehicleTypes;
import ui.dialogs.DlgNewProject;
import ui.dialogs.DlgRandomLayerProperties;
import ui.dialogs.DlgSelectTypes;
import ui.dialogs.DlgSettings;
import ui.filefilters.ProjectFileFilter;
import ui.filefilters.TDLFileFilter;
import classes.Layer;
import classes.Project;
import classes.ProjectElement;
import classes.TypeSelection;
import enums.EditingMode;
import enums.SelectionType;
import enums.ToolType;

/**
 * The application's main window
 */
public class MainWindow extends JFrame implements CurrentLayerChangedListener, WindowListener, ProjectChangedListener {

	private static final long serialVersionUID = 1L;

	private JToggleButton btnAddSelection = null;

	private JToggleButton btnCreateAccident = null;

	private JToggleButton btnCreateArea = null;

	private JToggleButton btnCreateAreaFlow = null;

	private JToggleButton btnCreateFlowEdgeToEdge = null;

	private JToggleButton btnCreateHotSpot = null;

	private JToggleButton btnCreateSchool = null;

	private JButton btnDelete = null;

	private JToggleButton btnNewSelection = null;

	private JToggleButton btnPan = null;

	private JToggleButton btnRemoveSelection = null;

	private JToggleButton btnSelect = null;

	private JButton btnUndelete = null;

	private JToggleButton btnZoom = null;

	/**
	 * The currently selected traffic layer
	 */
	private TrafficDefinitionLayer currentTrafficLayer = null; // @jve:decl-index=0:

	private JPanel jContentPane = null;

	private Display mapDisplay = null;

	private JMenu mnuFile = null;

	private JMenuItem mnuitmClose = null;

	private JCheckBoxMenuItem mnuitmDeletedMapElements = null;

	private JCheckBoxMenuItem mnuitmDrawAllTrafficLayers = null;

	private JMenuItem mnuitmExit = null;

	private JMenuItem mnuitmExportSimulation = null;

	private JMenuItem mnuitmSelectActivityBasedVehicleTypes = null;

	private JMenuItem mnuitmJobTypes = null;

	private JMenuItem mnuitmLayerSelectVehicleTypes = null;

	private JMenuItem mnuitmLayerProperties = null;

	private JMenuItem mnuitmNew = null;

	private JMenuItem mnuitmOpen = null;

	private JMenuItem mnuitmPurgeDeleted = null;

	private JMenuItem mnuitmSave = null;

	private JMenuItem mnuitmSaveAs = null;

	private JMenuItem mnuitmExportTDL = null;
	
	private JMenuItem mnuitmSelectAll = null;

	private JMenuItem mnuitmSelectInverse = null;

	private JMenuItem mnuitmSelectNone = null;

	private JMenuItem mnuitmSettings = null;

	private JMenuItem mnuitmVehicleTypes = null;

	private JMenuItem mnuitmZoomToFit = null;

	private JMenuItem mnuitmHelpAbout = null;

	private JMenuBar mnuMain = null;

	private JMenu mnuSelect = null;

	private JMenu mnuSimulation = null;

	private JMenu mnuHelp = null;

	private JMenu mnuTrafficLayer = null;

	private JMenu mnuView = null;

	private PnlLayers pnlLayers = null;

	private PnlProperties pnlProperties = null;

	private JPanel pnlScrollDisplay = null;

	/**
	 * The currently loaded project
	 */
	private Project project = null;

	private JScrollBar scrlMapHorizontal = null;

	private JScrollBar scrlMapVertical = null;

	private ButtonGroup SelectionTypeGroup = new ButtonGroup();

	private JSplitPane splitPane = null;

	private JSplitPane splitPaneRight = null;

	private JToolBar tlbTools = null;

	private ButtonGroup ToolGroup = new ButtonGroup();

	/**
	 * This is the default constructor
	 */
	public MainWindow() {
		super();

		// Initialize controls
		initialize();

		// Disable the toolbar
		SetToolbarEnabled(false);

		// Add event listeners
		Broadcaster.addProjectChangedListener(this);

		addWindowListener(this);

		pnlLayers.addCurrentLayerChangedListener(this);
		pnlLayers.addCurrentLayerChangedListener(pnlProperties);
		pnlLayers.addCurrentLayerChangedListener(mapDisplay);

		Broadcaster.addProjectChangedListener(pnlProperties);

		pnlProperties.setMapDisplay(mapDisplay);
	}

	public void CurrentLayerChanged(Layer<? extends ProjectElement> newCurrentLayer) {
		// Update the editing mode based on the new current layer
		if (newCurrentLayer instanceof RoadNetwork) {
			setEditingMode(EditingMode.Map);

			currentTrafficLayer = null;
		} else if (newCurrentLayer instanceof UserDefinedTrafficDefinitionLayer) {
			setEditingMode(EditingMode.UserDefinedTraffic);

			currentTrafficLayer = (TrafficDefinitionLayer) newCurrentLayer;
		} else if (newCurrentLayer instanceof ActivityBasedTrafficDefinitionLayer) {
			setEditingMode(EditingMode.ActivityBasedTraffic);

			currentTrafficLayer = (TrafficDefinitionLayer) newCurrentLayer;
		} else if (newCurrentLayer instanceof RandomTrafficDefinitionLayer) {
			setEditingMode(EditingMode.RandomTraffic);

			currentTrafficLayer = (TrafficDefinitionLayer) newCurrentLayer;
		}
	}

	public void ProjectChanged(Project p) {
		project = p;

		// If the project has been closed
		if (project == null) {
			// Disable controls
			mnuitmClose.setEnabled(false);
			mnuitmSave.setEnabled(false);
			mnuitmSaveAs.setEnabled(false);
			mnuitmExportTDL.setEnabled(false);
			mnuitmPurgeDeleted.setEnabled(false);

			mnuSelect.setEnabled(false);
			mnuView.setEnabled(false);
			mnuTrafficLayer.setEnabled(false);
			mnuSimulation.setEnabled(false);

			SetToolbarEnabled(false);

			scrlMapVertical.setEnabled(false);
			scrlMapHorizontal.setEnabled(false);

			// Reset title and selection mode
			setTitle("SUMO Traffic Generator");

			btnNewSelection.setSelected(true);

			currentTrafficLayer = null;
		}
		// If the project has been opened
		else {
			// Enable controls
			mnuitmClose.setEnabled(true);
			mnuitmSave.setEnabled(true);
			mnuitmSaveAs.setEnabled(true);
			mnuitmExportTDL.setEnabled(true);
			mnuitmPurgeDeleted.setEnabled(true);

			mnuSelect.setEnabled(true);
			mnuView.setEnabled(true);
			mnuSimulation.setEnabled(true);

			scrlMapVertical.setEnabled(true);
			scrlMapHorizontal.setEnabled(true);

			SetToolbarEnabled(true);

			// Update title
			setTitle("SUMO Traffic Generator" + " - " + project.getFileName());

			// Set the editing mode to road network and initialize the tool to
			// the pan tool
			setEditingMode(EditingMode.Map);

			getMapDisplay().setTool(ToolType.Pan);
		}
	}

	/**
	 * Sets the current editing mode. Depending on the mode, different controls
	 * are enabled / disabled
	 * 
	 * @param newMode
	 */
	public void setEditingMode(EditingMode newMode) {
		switch (newMode) {
		case Map:
			btnCreateFlowEdgeToEdge.setEnabled(false);
			btnCreateAreaFlow.setEnabled(false);
			btnCreateArea.setEnabled(false);
			btnCreateHotSpot.setEnabled(false);
			btnCreateAccident.setEnabled(false);
			btnCreateSchool.setEnabled(false);
			btnUndelete.setEnabled(true);

			mnuTrafficLayer.setEnabled(false);
			break;
		case UserDefinedTraffic:
			btnCreateFlowEdgeToEdge.setEnabled(true);
			btnCreateAreaFlow.setEnabled(true);
			btnCreateHotSpot.setEnabled(true);
			btnCreateAccident.setEnabled(true);

			btnCreateSchool.setEnabled(false);
			btnCreateArea.setEnabled(false);

			btnUndelete.setEnabled(false);

			mnuTrafficLayer.setEnabled(false);
			break;
		case ActivityBasedTraffic:
			btnCreateFlowEdgeToEdge.setEnabled(false);
			btnCreateAreaFlow.setEnabled(false);
			btnCreateHotSpot.setEnabled(false);
			btnCreateAccident.setEnabled(false);

			btnCreateSchool.setEnabled(true);
			btnCreateArea.setEnabled(true);

			btnUndelete.setEnabled(false);

			mnuTrafficLayer.setEnabled(false);
			break;
		case RandomTraffic:
			btnCreateFlowEdgeToEdge.setEnabled(false);
			btnCreateAreaFlow.setEnabled(false);
			btnCreateArea.setEnabled(false);
			btnCreateHotSpot.setEnabled(false);
			btnCreateAccident.setEnabled(false);
			btnCreateSchool.setEnabled(false);
			btnUndelete.setEnabled(false);

			mnuTrafficLayer.setEnabled(true);
			break;
		}

		// Default tool
		btnPan.setSelected(true);

		getMapDisplay().setTool(ToolType.Pan);
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		Exit();
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	/**
	 * Closes the currently open project
	 * 
	 * @return JOptionPane.OK_OPTION if the project was closed,
	 *         JOptionPane.CANCEL_OPTION if the user cancelled the operation
	 */
	private int CloseProject() {
		// Ask the user to save any changes she might have made
		switch (JOptionPane.showConfirmDialog(this, "Would you like to save your changes?")) {
		case JOptionPane.YES_OPTION:
			SaveProject();
		case JOptionPane.NO_OPTION:
			break;
		// If the user pressed the cancel button or closed the message box then
		// return a cancel message
		case JOptionPane.CANCEL_OPTION:
		case JOptionPane.CLOSED_OPTION:
			return JOptionPane.CANCEL_OPTION;
		}

		// Close the project
		project.Close();

		// Notify any registered listeners that the project has been closed
		Broadcaster.NotifyProjectChanged(null);

		return JOptionPane.OK_OPTION;
	}

	/**
	 * Exits the application
	 * 
	 */
	private void Exit() {
		// If there was a loaded project, close it
		if (project != null) {
			if (CloseProject() == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}

		System.exit(0);
	}

	/**
	 * This method initializes btnAddSelection
	 * @return  javax.swing.JToggleButton
	 * @uml.property  name="btnAddSelection"
	 */
	private JToggleButton getBtnAddSelection() {
		if (btnAddSelection == null) {
			btnAddSelection = new JToggleButton();
			btnAddSelection.setIcon(new ImageIcon(getClass().getResource("/resources/icons/selectionadd.png")));
			btnAddSelection.setToolTipText("Add to selection");
			btnAddSelection.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SetAddToSelection();
				}
			});
		}
		return btnAddSelection;
	}

	/**
	 * This method initializes btnCreateAccident
	 * @return  javax.swing.JToggleButton
	 * @uml.property  name="btnCreateAccident"
	 */
	private JToggleButton getBtnCreateAccident() {
		if (btnCreateAccident == null) {
			btnCreateAccident = new JToggleButton();
			btnCreateAccident.setText("Accident");
			btnCreateAccident.setIcon(new ImageIcon(getClass().getResource("/resources/icons/accident.png")));
			btnCreateAccident.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().setTool(ToolType.Accident);
				}
			});

		}
		return btnCreateAccident;
	}

	/**
	 * This method initializes btnCreateArea
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCreateArea"
	 */
	private JToggleButton getBtnCreateArea() {
		if (btnCreateArea == null) {
			btnCreateArea = new JToggleButton();
			btnCreateArea.setText("Traffic Area");
			btnCreateArea.setIcon(new ImageIcon(getClass().getResource("/resources/icons/traffic_area.png")));
			btnCreateArea.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().setTool(ToolType.Area);
				}
			});
		}
		return btnCreateArea;
	}

	/**
	 * This method initializes btnCreateAreaFlow
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="btnCreateAreaFlow"
	 */
	private JToggleButton getBtnCreateAreaFlow() {
		if (btnCreateAreaFlow == null) {
			btnCreateAreaFlow = new JToggleButton();
			btnCreateAreaFlow.setText("Area Flow");
			btnCreateAreaFlow.setIcon(new ImageIcon(getClass().getResource("/resources/icons/areaflow.png")));
			btnCreateAreaFlow.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().setTool(ToolType.AreaFlow);
				}
			});
		}
		return btnCreateAreaFlow;
	}

	/**
	 * This method initializes btnCreateFlowEdgeToEdge
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCreateFlowEdgeToEdge"
	 */
	private JToggleButton getBtnCreateFlowEdgeToEdge() {
		if (btnCreateFlowEdgeToEdge == null) {
			btnCreateFlowEdgeToEdge = new JToggleButton();
			btnCreateFlowEdgeToEdge.setText("Flow");
			btnCreateFlowEdgeToEdge.setIcon(new ImageIcon(getClass().getResource("/resources/icons/flow.png")));
			btnCreateFlowEdgeToEdge.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().setTool(ToolType.Flow);
				}
			});
		}
		return btnCreateFlowEdgeToEdge;
	}

	/**
	 * This method initializes btnCreateHotSpot
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCreateHotSpot"
	 */
	private JToggleButton getBtnCreateHotSpot() {
		if (btnCreateHotSpot == null) {
			btnCreateHotSpot = new JToggleButton();
			btnCreateHotSpot.setText("Hotspot");
			btnCreateHotSpot.setIcon(new ImageIcon(getClass().getResource("/resources/icons/hotspotinout.png")));
			btnCreateHotSpot.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().setTool(ToolType.Hotspot);
				}
			});
		}
		return btnCreateHotSpot;
	}

	/**
	 * This method initializes btnCreateSchool
	 * @return  javax.swing.JToggleButton
	 * @uml.property  name="btnCreateSchool"
	 */
	private JToggleButton getBtnCreateSchool() {
		if (btnCreateSchool == null) {
			btnCreateSchool = new JToggleButton();
			btnCreateSchool.setText("School");
			btnCreateSchool.setIcon(new ImageIcon(getClass().getResource("/resources/icons/school.png")));
			btnCreateSchool.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().setTool(ToolType.School);
				}
			});
		}
		return btnCreateSchool;
	}

	/**
	 * This method initializes btnDelete
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnDelete"
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setIcon(new ImageIcon(getClass().getResource("/resources/icons/delete.png")));
			btnDelete.setToolTipText("Delete");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().DeleteSelectedObjects();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnNewSelection
	 * @return  javax.swing.JToggleButton
	 * @uml.property  name="btnNewSelection"
	 */
	private JToggleButton getBtnNewSelection() {
		if (btnNewSelection == null) {
			btnNewSelection = new JToggleButton();
			btnNewSelection.setIcon(new ImageIcon(getClass().getResource("/resources/icons/selectionnew.png")));
			btnNewSelection.setToolTipText("New selection");
			btnNewSelection.setSelected(true);
			btnNewSelection.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SetNewSelection();
				}
			});
		}
		return btnNewSelection;
	}

	/**
	 * This method initializes btnPan
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnPan"
	 */
	private JToggleButton getBtnPan() {
		if (btnPan == null) {
			btnPan = new JToggleButton();
			btnPan.setPreferredSize(new Dimension(16, 16));
			btnPan.setSize(new Dimension(16, 16));
			btnPan.setSelected(false);
			btnPan.setIcon(new ImageIcon(getClass().getResource("/resources/icons/pan.gif")));
			btnPan.setToolTipText("Pan");
			btnPan.setLocation(new Point(16, 5));
			btnPan.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().setTool(ToolType.Pan);
				}
			});
		}
		return btnPan;
	}

	/**
	 * This method initializes btnRemoveSelection
	 * @return  javax.swing.JToggleButton
	 * @uml.property  name="btnRemoveSelection"
	 */
	private JToggleButton getBtnRemoveSelection() {
		if (btnRemoveSelection == null) {
			btnRemoveSelection = new JToggleButton();
			btnRemoveSelection.setIcon(new ImageIcon(getClass().getResource("/resources/icons/selectionremove.png")));
			btnRemoveSelection.setToolTipText("Subtract from selection");
			btnRemoveSelection.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SetRemoveFromSelection();
				}
			});
		}
		return btnRemoveSelection;
	}

	/**
	 * This method initializes btnSelect
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnSelect"
	 */
	private JToggleButton getBtnSelect() {
		if (btnSelect == null) {
			btnSelect = new JToggleButton();
			btnSelect.setIcon(new ImageIcon(getClass().getResource("/resources/icons/select.png")));
			btnSelect.setToolTipText("Select");
			btnSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().setTool(ToolType.Select);
				}
			});
			btnSelect.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (btnSelect.isSelected()) {
						btnNewSelection.setVisible(true);
						btnAddSelection.setVisible(true);
						btnRemoveSelection.setVisible(true);
					} else {
						btnNewSelection.setVisible(false);
						btnAddSelection.setVisible(false);
						btnRemoveSelection.setVisible(false);
					}
				}
			});
		}
		return btnSelect;
	}

	/**
	 * This method initializes btnUndelete
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnUndelete"
	 */
	private JButton getBtnUndelete() {
		if (btnUndelete == null) {
			btnUndelete = new JButton();
			btnUndelete.setIcon(new ImageIcon(getClass().getResource("/resources/icons/undelete.png")));
			btnUndelete.setToolTipText("Undelete");
			btnUndelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().UndeleteSelectedObjects();
				}
			});
		}
		return btnUndelete;
	}

	/**
	 * This method initializes btnZoom
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnZoom"
	 */
	private JToggleButton getBtnZoom() {
		if (btnZoom == null) {
			btnZoom = new JToggleButton();
			btnZoom.setToolTipText("Zoom");
			btnZoom.setPreferredSize(new Dimension(24, 24));
			btnZoom.setBounds(new Rectangle(296, 1, 24, 24));
			btnZoom.setIcon(new ImageIcon(getClass().getResource("/resources/icons/zoom.png")));
			btnZoom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().setTool(ToolType.Zoom);
				}
			});
		}
		return btnZoom;
	}

	/**
	 * This method initializes jContentPane
	 * @return  javax.swing.JPanel
	 * @uml.property  name="jContentPane"
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getTlbTools(), BorderLayout.PAGE_START);
			jContentPane.add(getSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes mapDisplay
	 * @return  ui.Display
	 * @uml.property  name="mapDisplay"
	 */
	private Display getMapDisplay() {
		if (mapDisplay == null) {
			mapDisplay = new Display(getScrlMapVertical(), getScrlMapHorizontal());
		}
		return mapDisplay;
	}

	/**
	 * This method initializes mnuFile
	 * @return  javax.swing.JMenu
	 * @uml.property  name="mnuFile"
	 */
	private JMenu getMnuFile() {
		if (mnuFile == null) {
			mnuFile = new JMenu();
			mnuFile.setText("File");
			mnuFile.add(getMnuitmNew());
			mnuFile.add(getMnuitmOpen());
			mnuFile.add(getMnuitmSave());
			mnuFile.add(getMnuitmSaveAs());
			mnuFile.add(getMnuitmExportTDL());
			mnuFile.add(getMnuitmClose());
			mnuFile.add(getMnuitmPurgeDeleted());
			mnuFile.add(getMnuitmExit());
		}
		return mnuFile;
	}

	/**
	 * This method initializes mnuitmClose
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmClose"
	 */
	private JMenuItem getMnuitmClose() {
		if (mnuitmClose == null) {
			mnuitmClose = new JMenuItem();
			mnuitmClose.setText("Close");
			mnuitmClose.setIcon(new ImageIcon(getClass().getResource("/resources/icons/clear.png")));
			mnuitmClose.setEnabled(false);
			mnuitmClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CloseProject();
				}
			});
		}
		return mnuitmClose;
	}

	/**
	 * This method initializes mnuitmDeletedMapElements
	 * @return  javax.swing.JCheckBoxMenuItem
	 * @uml.property  name="mnuitmDeletedMapElements"
	 */
	private JCheckBoxMenuItem getMnuitmDeletedMapElements() {
		if (mnuitmDeletedMapElements == null) {
			mnuitmDeletedMapElements = new JCheckBoxMenuItem();
			mnuitmDeletedMapElements.setText("Show deleted map elements");
			mnuitmDeletedMapElements.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					getMapDisplay().setShowDeleted(mnuitmDeletedMapElements.isSelected());
				}
			});
		}
		return mnuitmDeletedMapElements;
	}

	/**
	 * This method initializes mnuitmDrawAllTrafficLayers
	 * @return  javax.swing.JCheckBoxMenuItem
	 * @uml.property  name="mnuitmDrawAllTrafficLayers"
	 */
	private JCheckBoxMenuItem getMnuitmDrawAllTrafficLayers() {
		if (mnuitmDrawAllTrafficLayers == null) {
			mnuitmDrawAllTrafficLayers = new JCheckBoxMenuItem();
			mnuitmDrawAllTrafficLayers.setText("Draw all traffic layers");
			mnuitmDrawAllTrafficLayers.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					getMapDisplay().setDrawAllTrafficLayers(mnuitmDrawAllTrafficLayers.isSelected());
				}
			});
		}
		return mnuitmDrawAllTrafficLayers;
	}
	
	/**
	 * This method initializes mnuitmExit
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmExit"
	 */
	private JMenuItem getMnuitmExit() {
		if (mnuitmExit == null) {
			mnuitmExit = new JMenuItem();
			mnuitmExit.setText("Exit");
			mnuitmExit.setIcon(new ImageIcon(getClass().getResource("/resources/icons/exit.png")));
			mnuitmExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Exit();
				}
			});
		}
		return mnuitmExit;
	}

	/**
	 * This method initializes mnuitmExportSimulation
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmExportSimulation"
	 */
	private JMenuItem getMnuitmExportSimulation() {
		if (mnuitmExportSimulation == null) {
			mnuitmExportSimulation = new JMenuItem();
			mnuitmExportSimulation.setText("Export...");
			mnuitmExportSimulation.setIcon(new ImageIcon(getClass().getResource("/resources/icons/clear.png")));
			mnuitmExportSimulation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExportSimulation();
				}
			});
		}
		return mnuitmExportSimulation;
	}

	/**
	 * This method initializes mnuitmHelpAbout
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmHelpAbout"
	 */
	private JMenuItem getMnuitmHelpAbout() {
		if (mnuitmHelpAbout == null) {
			mnuitmHelpAbout = new JMenuItem();
			mnuitmHelpAbout.setText("About...");
			mnuitmHelpAbout.setIcon(new ImageIcon(getClass().getResource("/resources/icons/clear.png")));
			mnuitmHelpAbout.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ShowHelpAbout();
				}
			});
		}
		return mnuitmHelpAbout;
	}

	protected void ShowHelpAbout() {
		JOptionPane.showMessageDialog(this, "Version 1.1\nDeveloped by Leontios Papaleontiou and others");
	}

	/**
	 * This method initializes mnuitmSelectActivityBasedVehicleTypes
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmSelectActivityBasedVehicleTypes"
	 */
	private JMenuItem getMnuitmSelectActivityBasedVehicleTypes() {
		if (mnuitmSelectActivityBasedVehicleTypes == null) {
			mnuitmSelectActivityBasedVehicleTypes = new JMenuItem();
			mnuitmSelectActivityBasedVehicleTypes.setText("Select vehicle types for activity-based layers...");
			mnuitmSelectActivityBasedVehicleTypes.setIcon(new ImageIcon(getClass().getResource("/resources/icons/clear.png")));
			mnuitmSelectActivityBasedVehicleTypes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectActivityBasedVehicleTypes();
				}
			});
		}
		return mnuitmSelectActivityBasedVehicleTypes;
	}

	/**
	 * This method initializes mnuitmJobTypes
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmJobTypes"
	 */
	private JMenuItem getMnuitmJobTypes() {
		if (mnuitmJobTypes == null) {
			mnuitmJobTypes = new JMenuItem();
			mnuitmJobTypes.setText("Job types...");
			mnuitmJobTypes.setIcon(new ImageIcon(getClass().getResource("/resources/icons/jobtypes.png")));
			mnuitmJobTypes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ShowJobTypes();
				}
			});
		}
		return mnuitmJobTypes;
	}

	/**
	 * This method initializes mnuitmLayerSelectVehicleTypes
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmLayerSelectVehicleTypes"
	 */
	private JMenuItem getMnuitmLayerSelectVehicleTypes() {
		if (mnuitmLayerSelectVehicleTypes == null) {
			mnuitmLayerSelectVehicleTypes = new JMenuItem();
			mnuitmLayerSelectVehicleTypes.setText("Select vehicle types...");
			mnuitmLayerSelectVehicleTypes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectRandomTrafficLayerVehicleTypes();
				}
			});
		}
		return mnuitmLayerSelectVehicleTypes;
	}

	/**
	 * This method initializes mnuitmLayerProperties
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmLayerProperties"
	 */
	private JMenuItem getMnuitmLayerProperties() {
		if (mnuitmLayerProperties == null) {
			mnuitmLayerProperties = new JMenuItem();
			mnuitmLayerProperties.setText("Properties...");
			mnuitmLayerProperties.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SetTrafficLayerProperties();
				}
			});
		}
		return mnuitmLayerProperties;
	}

	/**
	 * This method initializes mnuitmNew
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmNew"
	 */
	private JMenuItem getMnuitmNew() {
		if (mnuitmNew == null) {
			mnuitmNew = new JMenuItem();
			mnuitmNew.setText("New project...");
			mnuitmNew.setIcon(new ImageIcon(getClass().getResource("/resources/icons/new.png")));
			mnuitmNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					NewProject();
				}
			});
		}
		return mnuitmNew;
	}

	/**
	 * This method initializes mnuitmOpen
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmOpen"
	 */
	private JMenuItem getMnuitmOpen() {
		if (mnuitmOpen == null) {
			mnuitmOpen = new JMenuItem();
			mnuitmOpen.setText("Open");
			mnuitmOpen.setIcon(new ImageIcon(getClass().getResource("/resources/icons/open.png")));
			mnuitmOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					OpenProject();
				}
			});
		}
		return mnuitmOpen;
	}

	/**
	 * This method initializes mnuitmPurgeDeleted
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmPurgeDeleted"
	 */
	private JMenuItem getMnuitmPurgeDeleted() {
		if (mnuitmPurgeDeleted == null) {
			mnuitmPurgeDeleted = new JMenuItem();
			mnuitmPurgeDeleted.setText("Purge deleted map");
			mnuitmPurgeDeleted.setIcon(new ImageIcon(getClass().getResource("/resources/icons/purge.png")));
			mnuitmPurgeDeleted.setEnabled(false);
			mnuitmPurgeDeleted.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PurgeDeleted();
				}
			});
		}
		return mnuitmPurgeDeleted;
	}

	/**
	 * This method initializes mnuitmSave
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmSave"
	 */
	private JMenuItem getMnuitmSave() {
		if (mnuitmSave == null) {
			mnuitmSave = new JMenuItem();
			mnuitmSave.setText("Save");
			mnuitmSave.setIcon(new ImageIcon(getClass().getResource("/resources/icons/save.png")));
			mnuitmSave.setEnabled(false);
			mnuitmSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SaveProject();
				}
			});
		}
		return mnuitmSave;
	}

	/**
	 * This method initializes mnuitmSaveAs
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmSaveAs"
	 */
	private JMenuItem getMnuitmSaveAs() {
		if (mnuitmSaveAs == null) {
			mnuitmSaveAs = new JMenuItem();
			mnuitmSaveAs.setText("Save As...");
			mnuitmSaveAs.setIcon(new ImageIcon(getClass().getResource("/resources/icons/clear.png")));
			mnuitmSaveAs.setEnabled(false);
			mnuitmSaveAs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SaveProjectAs();
				}
			});
		}
		return mnuitmSaveAs;
	}

	/**
	 * This method initializes mnuitmSaveAs
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmExportTDL"
	 */
	private JMenuItem getMnuitmExportTDL() {
		if (mnuitmExportTDL == null) {
			mnuitmExportTDL = new JMenuItem();
			mnuitmExportTDL.setText("Export TDL...");
			mnuitmExportTDL.setIcon(new ImageIcon(getClass().getResource("/resources/icons/clear.png")));
			mnuitmExportTDL.setEnabled(false);
			mnuitmExportTDL.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExportTDL();
				}
			});
		}
		return mnuitmExportTDL;
	}
	
	/**
	 * This method initializes mnuitmSelectAll
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmSelectAll"
	 */
	private JMenuItem getMnuitmSelectAll() {
		if (mnuitmSelectAll == null) {
			mnuitmSelectAll = new JMenuItem();
			mnuitmSelectAll.setText("Select All");
			mnuitmSelectAll.setIcon(new ImageIcon(getClass().getResource("/resources/icons/clear.png")));
			mnuitmSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectAll();
				}
			});
		}
		return mnuitmSelectAll;
	}

	/**
	 * This method initializes mnuitmSelectInverse
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmSelectInverse"
	 */
	private JMenuItem getMnuitmSelectInverse() {
		if (mnuitmSelectInverse == null) {
			mnuitmSelectInverse = new JMenuItem();
			mnuitmSelectInverse.setText("Select Inverse");
			mnuitmSelectInverse.setIcon(new ImageIcon(getClass().getResource("/resources/icons/clear.png")));
			mnuitmSelectInverse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectInverse();
				}
			});
		}
		return mnuitmSelectInverse;
	}

	/**
	 * This method initializes mnuitmSelectNone
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmSelectNone"
	 */
	private JMenuItem getMnuitmSelectNone() {
		if (mnuitmSelectNone == null) {
			mnuitmSelectNone = new JMenuItem();
			mnuitmSelectNone.setText("Select None");
			mnuitmSelectNone.setIcon(new ImageIcon(getClass().getResource("/resources/icons/clear.png")));
			mnuitmSelectNone.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectNone();
				}
			});
		}
		return mnuitmSelectNone;
	}

	/**
	 * This method initializes mnuitmSettings
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmSettings"
	 */
	private JMenuItem getMnuitmSettings() {
		if (mnuitmSettings == null) {
			mnuitmSettings = new JMenuItem();
			mnuitmSettings.setText("Settings...");
			mnuitmSettings.setIcon(new ImageIcon(getClass().getResource("/resources/icons/settings.png")));
			mnuitmSettings.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ShowSettings();
				}
			});
		}
		return mnuitmSettings;
	}

	/**
	 * This method initializes mnuitmVehicleTypes
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmVehicleTypes"
	 */
	private JMenuItem getMnuitmVehicleTypes() {
		if (mnuitmVehicleTypes == null) {
			mnuitmVehicleTypes = new JMenuItem();
			mnuitmVehicleTypes.setText("Vehicle types...");
			mnuitmVehicleTypes.setIcon(new ImageIcon(getClass().getResource("/resources/icons/vehicletypes.png")));
			mnuitmVehicleTypes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ShowVehicleTypes();
				}
			});
		}
		return mnuitmVehicleTypes;
	}

	/**
	 * This method initializes mnuitmZoomToFit
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mnuitmZoomToFit"
	 */
	private JMenuItem getMnuitmZoomToFit() {
		if (mnuitmZoomToFit == null) {
			mnuitmZoomToFit = new JMenuItem();
			mnuitmZoomToFit.setText("Zoom to fit");
			mnuitmZoomToFit.setIcon(new ImageIcon(getClass().getResource("/resources/icons/clear.png")));
			mnuitmZoomToFit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getMapDisplay().ZoomToFitAndCenter();
				}
			});
		}
		return mnuitmZoomToFit;
	}

	/**
	 * This method initializes mnuMain
	 * @return  javax.swing.JMenuBar
	 * @uml.property  name="mnuMain"
	 */
	private JMenuBar getMnuMain() {
		if (mnuMain == null) {
			mnuMain = new JMenuBar();
			mnuMain.add(getMnuFile());
			mnuMain.add(getMnuSelect());
			mnuMain.add(getMnuView());
			mnuMain.add(getMnuTrafficLayer());
			mnuMain.add(getMnuSimulation());
			mnuMain.add(getMnuHelp());
		}
		return mnuMain;
	}

	/**
	 * This method initializes mnuSelect
	 * @return  javax.swing.JMenu
	 * @uml.property  name="mnuSelect"
	 */
	private JMenu getMnuSelect() {
		if (mnuSelect == null) {
			mnuSelect = new JMenu();
			mnuSelect.setText("Select");
			mnuSelect.setEnabled(false);
			mnuSelect.add(getMnuitmSelectInverse());
			mnuSelect.add(getMnuitmSelectNone());
			mnuSelect.add(getMnuitmSelectAll());
		}
		return mnuSelect;
	}

	/**
	 * This method initializes mnuSimulation
	 * @return  javax.swing.JMenu
	 * @uml.property  name="mnuSimulation"
	 */
	private JMenu getMnuSimulation() {
		if (mnuSimulation == null) {
			mnuSimulation = new JMenu();
			mnuSimulation.setText("Simulation");
			mnuSimulation.setEnabled(false);
			mnuSimulation.add(getMnuitmExportSimulation());
			mnuSimulation.add(getMnuitmSelectActivityBasedVehicleTypes());
		}
		return mnuSimulation;
	}

	/**
	 * This method initializes mnuHelp
	 * @return  javax.swing.JMenu
	 * @uml.property  name="mnuHelp"
	 */
	private JMenu getMnuHelp() {
		if (mnuHelp == null) {
			mnuHelp = new JMenu();
			mnuHelp.setText("Help");
			mnuHelp.add(getMnuitmHelpAbout());
		}
		return mnuHelp;
	}

	/**
	 * This method initializes mnuTrafficLayer
	 * @return  javax.swing.JMenu
	 * @uml.property  name="mnuTrafficLayer"
	 */
	private JMenu getMnuTrafficLayer() {
		if (mnuTrafficLayer == null) {
			mnuTrafficLayer = new JMenu();
			mnuTrafficLayer.setText("Traffic Layer");
			mnuTrafficLayer.setEnabled(false);
			mnuTrafficLayer.add(getMnuitmLayerSelectVehicleTypes());
			mnuTrafficLayer.add(getMnuitmLayerProperties());
		}
		return mnuTrafficLayer;
	}

	/**
	 * This method initializes mnuView
	 * @return  javax.swing.JMenu
	 * @uml.property  name="mnuView"
	 */
	private JMenu getMnuView() {
		if (mnuView == null) {
			mnuView = new JMenu();
			mnuView.setText("View");
			mnuView.setEnabled(false);
			mnuView.add(getMnuitmDeletedMapElements());
			mnuView.add(getMnuitmDrawAllTrafficLayers());
			mnuView.add(getMnuitmZoomToFit());
			mnuView.add(getMnuitmSettings());
			mnuView.add(getMnuitmJobTypes());
			mnuView.add(getMnuitmVehicleTypes());
		}
		return mnuView;
	}

	/**
	 * This method initializes pnlLayers2
	 * @return  ui.PnlLayers
	 * @uml.property  name="pnlLayers"
	 */
	private PnlLayers getPnlLayers() {
		if (pnlLayers == null) {
			pnlLayers = new PnlLayers();
		}
		return pnlLayers;
	}

	/**
	 * This method initializes pnlProperties
	 * @return  ui.PnlProperties
	 * @uml.property  name="pnlProperties"
	 */
	private PnlProperties getPnlProperties() {
		if (pnlProperties == null) {
			pnlProperties = new PnlProperties();
		}
		return pnlProperties;
	}

	/**
	 * This method initializes pnlScrollDisplay
	 * @return  javax.swing.JPanel
	 * @uml.property  name="pnlScrollDisplay"
	 */
	private JPanel getPnlScrollDisplay() {
		if (pnlScrollDisplay == null) {
			GridBagConstraints scrlHorizontalConstraints = new GridBagConstraints();
			scrlHorizontalConstraints.fill = GridBagConstraints.HORIZONTAL;
			scrlHorizontalConstraints.gridy = 1;
			scrlHorizontalConstraints.anchor = GridBagConstraints.SOUTH;
			scrlHorizontalConstraints.gridx = 0;

			GridBagConstraints scrlVerticalConstraints = new GridBagConstraints();
			scrlVerticalConstraints.fill = GridBagConstraints.VERTICAL;
			scrlVerticalConstraints.gridy = 0;
			scrlVerticalConstraints.weighty = 1.0;
			scrlVerticalConstraints.anchor = GridBagConstraints.WEST;
			scrlVerticalConstraints.gridx = 1;

			GridBagConstraints mapDisplayConstraints = new GridBagConstraints();
			mapDisplayConstraints.gridx = 0;
			mapDisplayConstraints.anchor = GridBagConstraints.NORTHWEST;
			mapDisplayConstraints.fill = GridBagConstraints.BOTH;
			mapDisplayConstraints.weightx = 1.0D;
			mapDisplayConstraints.weighty = 1.0D;
			mapDisplayConstraints.gridy = 0;

			pnlScrollDisplay = new JPanel();
			pnlScrollDisplay.setLayout(new GridBagLayout());
			pnlScrollDisplay.add(getMapDisplay(), mapDisplayConstraints);
			pnlScrollDisplay.add(getScrlMapVertical(), scrlVerticalConstraints);
			pnlScrollDisplay.add(getScrlMapHorizontal(), scrlHorizontalConstraints);
		}
		return pnlScrollDisplay;
	}

	/**
	 * This method initializes scrlMapHorizontal
	 * @return  javax.swing.JScrollBar
	 * @uml.property  name="scrlMapHorizontal"
	 */
	private JScrollBar getScrlMapHorizontal() {
		if (scrlMapHorizontal == null) {
			scrlMapHorizontal = new JScrollBar();
			scrlMapHorizontal.setOrientation(Adjustable.HORIZONTAL);
			scrlMapHorizontal.setEnabled(false);
		}
		return scrlMapHorizontal;
	}

	/**
	 * This method initializes scrlMapVertical
	 * @return  javax.swing.JScrollBar
	 * @uml.property  name="scrlMapVertical"
	 */
	private JScrollBar getScrlMapVertical() {
		if (scrlMapVertical == null) {
			scrlMapVertical = new JScrollBar();
			scrlMapVertical.setEnabled(false);
		}
		return scrlMapVertical;
	}

	/**
	 * This method initializes splitPane
	 * @return  javax.swing.JSplitPane
	 * @uml.property  name="splitPane"
	 */
	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setContinuousLayout(true);
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerLocation(640);
			splitPane.setResizeWeight(1.0D);
			splitPane.setDividerSize(8);
			splitPane.setLeftComponent(getPnlScrollDisplay());
			splitPane.setRightComponent(getSplitPaneRight());
		}
		return splitPane;
	}

	/**
	 * This method initializes splitPaneRight
	 * @return  javax.swing.JSplitPane
	 * @uml.property  name="splitPaneRight"
	 */
	private JSplitPane getSplitPaneRight() {
		if (splitPaneRight == null) {
			splitPaneRight = new JSplitPane();
			splitPaneRight.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPaneRight.setResizeWeight(1.0D);
			splitPaneRight.setDividerLocation(200);
			splitPaneRight.setDividerSize(8);
			splitPaneRight.setContinuousLayout(true);
			splitPaneRight.setTopComponent(getPnlLayers());
			splitPaneRight.setBottomComponent(getPnlProperties());
			splitPaneRight.setOneTouchExpandable(true);
		}
		return splitPaneRight;
	}

	/**
	 * This method initializes tlbTools
	 * @return  javax.swing.JToolBar
	 * @uml.property  name="tlbTools"
	 */
	private JToolBar getTlbTools() {
		if (tlbTools == null) {
			tlbTools = new JToolBar();
			tlbTools.setName("Tools");
			tlbTools.setPreferredSize(new Dimension(500, 32));
			tlbTools.setVisible(true);
			tlbTools.setRollover(true);

			tlbTools.add(getBtnSelect());
			tlbTools.add(getBtnZoom());
			tlbTools.add(getBtnPan());
			tlbTools.add(new JToolBar.Separator());
			tlbTools.add(getBtnCreateFlowEdgeToEdge());
			tlbTools.add(getBtnCreateAreaFlow());
			tlbTools.add(getBtnCreateHotSpot());
			tlbTools.add(getBtnCreateAccident());
			tlbTools.add(new JToolBar.Separator());
			tlbTools.add(getBtnCreateArea());
			tlbTools.add(getBtnCreateSchool());
			tlbTools.add(new JToolBar.Separator());
			tlbTools.add(getBtnDelete());
			tlbTools.add(getBtnUndelete());
			tlbTools.add(new JToolBar.Separator());
			tlbTools.add(getBtnNewSelection());
			tlbTools.add(getBtnAddSelection());
			tlbTools.add(getBtnRemoveSelection());

			SelectionTypeGroup.add(getBtnNewSelection());
			SelectionTypeGroup.add(getBtnAddSelection());
			SelectionTypeGroup.add(getBtnRemoveSelection());

			ToolGroup.add(getBtnCreateFlowEdgeToEdge());
			ToolGroup.add(getBtnCreateAreaFlow());
			ToolGroup.add(getBtnCreateHotSpot());
			ToolGroup.add(getBtnCreateAccident());
			ToolGroup.add(getBtnCreateArea());
			ToolGroup.add(getBtnCreateSchool());
			ToolGroup.add(getBtnPan());
			ToolGroup.add(getBtnZoom());
			ToolGroup.add(getBtnSelect());

		}
		return tlbTools;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(946, 505);
		setPreferredSize(new Dimension(946, 505));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setJMenuBar(getMnuMain());
		setContentPane(getJContentPane());

		setTitle("SUMO Traffic Generator");

		pack();
	}

	/**
	 * Creates a new project
	 * 
	 */
	private void NewProject() {
		// If there was an open project close it
		if (project != null) {
			if (CloseProject() == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}

		// Show new project window
		DlgNewProject dlg = new DlgNewProject(this);
		dlg.setSize(new Dimension(452, 153));

		// If the user pressed the OK button
		if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
			try {
				setCursor(Cursor.WAIT_CURSOR);

				// Create the project
				Project newProject = new Project(dlg.getProjectPath(), dlg.getMapFile());

				// Notify any registered listeners that the project has changed
				Broadcaster.NotifyProjectChanged(newProject);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "An error occured while creating the project", "Error", JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} finally {
				setCursor(Cursor.DEFAULT_CURSOR);
			}
		}
	}

	/**
	 * Opens a project from file
	 * 
	 */
	private void OpenProject() {
		// If there was a previously open project close it
		if (project != null) {
			if (CloseProject() == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}

		// Ask the user to select the project file
		JFileChooser f = new JFileChooser();
		f.setDialogTitle("Select project path");
		f.setAcceptAllFileFilterUsed(false);
		f.setFileFilter(new ProjectFileFilter());
		f.setMultiSelectionEnabled(false);

		int result = f.showOpenDialog(this);

		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}

		try {
			setCursor(Cursor.WAIT_CURSOR);

			File file = f.getSelectedFile();

			// Open the project
			Project openedProject = new Project(file.getAbsolutePath());

			// Notify any registered listeners that the project has been opened
			Broadcaster.NotifyProjectChanged(openedProject);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "An error occured while opening the project", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			setCursor(Cursor.DEFAULT_CURSOR);
		}

	}

	/**
	 * Saves the currently loaded project
	 * 
	 */
	private void SaveProject() {
		try {
			setCursor(Cursor.WAIT_CURSOR);

			project.Save();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "An error occured while saving the project", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			setCursor(Cursor.DEFAULT_CURSOR);
		}

	}

	/**
	 * Exports the currently loaded project to TDL
	 *
	 */
	private void ExportTDL(){
		// Ask the user to select the destination file
		JFileChooser f = new JFileChooser();

		f.setDialogTitle("Select export file");
		f.setAcceptAllFileFilterUsed(false);
		f.setFileFilter(new TDLFileFilter());
		f.setMultiSelectionEnabled(false);
		
		int result = f.showSaveDialog(this);

		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}

		try {
			setCursor(Cursor.WAIT_CURSOR);

			File file = f.getSelectedFile();

			if(!file.getAbsolutePath().endsWith(".tdl")){
				file = new File(file.getAbsolutePath()+".tdl");
			}
			
			// Export the project as TDL
			project.saveTDL(file.getAbsolutePath());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "An error occured while exporting the project", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			setCursor(Cursor.DEFAULT_CURSOR);
		}
	}
	
	/**
	 * Saves the currently loaded project using a different path
	 * 
	 */
	private void SaveProjectAs() {
		// Ask the user to select the destination file
		JFileChooser f = new JFileChooser();

		f.setDialogTitle("Select project path");
		f.setAcceptAllFileFilterUsed(false);
		f.setFileFilter(new ProjectFileFilter());
		f.setMultiSelectionEnabled(false);

		int result = f.showSaveDialog(this);

		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}

		try {
			setCursor(Cursor.WAIT_CURSOR);

			File file = f.getSelectedFile();

			// Save the project
			project.SaveAs(file.getAbsolutePath());

			// Update the window's title
			setTitle("SUMO Traffic Generator" + " - " + project.getFileName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "An error occured while saving the project", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			setCursor(Cursor.DEFAULT_CURSOR);
		}
	}

	/**
	 * Enables or disables the main toolbar
	 * 
	 * @param status
	 */
	private void SetToolbarEnabled(boolean status) {
		for (Component c : tlbTools.getComponents()) {
			c.setEnabled(status);
		}
	}

	/**
	 * Displays a dialog so that the user may select the vehicle types for the
	 * activity-based layers
	 * 
	 */
	protected void SelectActivityBasedVehicleTypes() {
		// Create a type selection that contains all the vehicle types of
		// the project, each with a 0 probability
		TypeSelection<VehicleType> projectVehicleTypes = new TypeSelection<VehicleType>(Project.getCurrentlyLoadedProject().getVehicleTypes());

		// Get a copy of the activity based vehicle types (so that if the user
		// cancels, the changes won't be permanent)
		TypeSelection<VehicleType> abVehicleTypes = new TypeSelection<VehicleType>(Project.getCurrentlyLoadedProject().getActivityBasedTrafficVehicleSelection());

		// Show the vehicle selection dialog
		abVehicleTypes.addTypesNotInSelection(projectVehicleTypes);

		DlgSelectTypes<VehicleType> dlg = new DlgSelectTypes<VehicleType>(abVehicleTypes);
		dlg.setVisible(true);

		if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
			// Remove any types that have 0 probability
			abVehicleTypes.removeTypesWithZeroPercentage();

			// Set the selected types to the layer
			Project.getCurrentlyLoadedProject().setActivityBasedTrafficVehicleSelection(abVehicleTypes);
		}
	}

	/**
	 * Exports the simulation
	 * 
	 */
	protected void ExportSimulation() {
		// Check if the user has provided the SUMO executable file paths and a
		// temporary folder path
		if (!ApplicationSettings.Validate()) {
			JOptionPane.showMessageDialog(this, "Before you export the simulation you need to set the paths in the View > Settings dialog");

			return;
		}

		// Show the dialog
		DlgExportSimulation dlg = new DlgExportSimulation(this, project);

		dlg.setVisible(true);

		// If the user has pressed OK then get the selected traffic layers and
		// export them
		if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
			List<TrafficDefinitionLayer> layersToExport = dlg.getSelectedLayers();

			try {
				setCursor(Cursor.WAIT_CURSOR);

				SimulationExporter.ExportSimulation(project, layersToExport);
			} finally {
				setCursor(Cursor.DEFAULT_CURSOR);
			}
		}
	}

	/**
	 * Permanently deletes the deleted road network elements
	 * 
	 */
	protected void PurgeDeleted() {
		if (JOptionPane.showConfirmDialog(this, "Are you sure you want to permanently remove the deleted parts of the map?") == JOptionPane.YES_OPTION) {
			try {
				setCursor(Cursor.WAIT_CURSOR);

				project.getRoadNetwork().PurgeDeleted();
			} finally {
				setCursor(Cursor.DEFAULT_CURSOR);
			}

			getMapDisplay().Refresh();
		}
	}

	/**
	 * Selects all the elements on the display
	 * 
	 */
	protected void SelectAll() {
		getMapDisplay().SelectAll();
	}

	/**
	 * Selects the inverse elements on the display
	 * 
	 */
	protected void SelectInverse() {
		getMapDisplay().SelectInverse();
	}

	/**
	 * Deselects all elements on the display
	 * 
	 */
	protected void SelectNone() {
		getMapDisplay().SelectNone();
	}

	/**
	 * Enables the user to select vehicle types for the current traffic layer
	 * 
	 */
	protected void SelectRandomTrafficLayerVehicleTypes() {
		if (currentTrafficLayer instanceof RandomTrafficDefinitionLayer) {
			RandomTrafficDefinitionLayer rdl = (RandomTrafficDefinitionLayer) currentTrafficLayer;

			// Create a type selection that contains all the vehicle types of
			// the
			// project, each with a 0 probability
			TypeSelection<VehicleType> projectVehicleTypes = new TypeSelection<VehicleType>(Project.getCurrentlyLoadedProject().getVehicleTypes());

			// Get a copy of the layer's vehicle types (so that if the user
			// cancels,
			// the changes won't be permanent)
			TypeSelection<VehicleType> layerVehicleTypes = new TypeSelection<VehicleType>(rdl.getVehicleSelection());

			// Show the vehicle selection dialog
			layerVehicleTypes.addTypesNotInSelection(projectVehicleTypes);

			DlgSelectTypes<VehicleType> dlg = new DlgSelectTypes<VehicleType>(layerVehicleTypes);
			dlg.setVisible(true);

			if (dlg.getAnswer() == JOptionPane.OK_OPTION) {
				// Remove any types that have 0 probability
				layerVehicleTypes.removeTypesWithZeroPercentage();

				// Set the selected types to the layer
				rdl.setVehicleSelection(layerVehicleTypes);
			}
		}
	}

	/**
	 * Enables the user to modify the current layer's properties
	 * 
	 */
	protected void SetTrafficLayerProperties() {
		if (currentTrafficLayer instanceof RandomTrafficDefinitionLayer) {
			DlgRandomLayerProperties dlg = new DlgRandomLayerProperties(this, (RandomTrafficDefinitionLayer) currentTrafficLayer);
		}
	}

	/**
	 * Sets the selection mode to 'Add'
	 * 
	 */
	protected void SetAddToSelection() {
		getMapDisplay().setSelectionType(SelectionType.Add);
	}

	/**
	 * Sets the selection mode to 'New'
	 * 
	 */
	protected void SetNewSelection() {
		getMapDisplay().setSelectionType(SelectionType.New);
	}

	/**
	 * Sets the selection mode to 'Remove'
	 * 
	 */
	protected void SetRemoveFromSelection() {
		getMapDisplay().setSelectionType(SelectionType.Remove);
	}

	/**
	 * Shows the job types dialog
	 * 
	 */
	protected void ShowJobTypes() {
		DlgManageJobTypes dlg = new DlgManageJobTypes(this, project.getJobTypes());

		project.setJobTypes(dlg.getJobTypes());
	}

	/**
	 * Shows the application settings dialog
	 * 
	 */
	protected void ShowSettings() {
		DlgSettings dlg = new DlgSettings(this);

		dlg.setVisible(true);
	}

	/**
	 * Shows the vehicle types dialog
	 * 
	 */
	protected void ShowVehicleTypes() {
		DlgManageVehicleTypes dlg = new DlgManageVehicleTypes(this, project.getVehicleTypes());

		project.setVehicleTypes(dlg.getVehicleTypes());
	}
} // @jve:decl-index=0:visual-constraint="10,10"
