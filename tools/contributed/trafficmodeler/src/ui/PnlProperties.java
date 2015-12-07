package ui;

import classes.Layer;
import classes.Project;
import classes.ProjectElement;
import enums.SelectionCount;
import interfaces.CurrentLayerChangedListener;
import interfaces.ModifiedCollectionListener;
import interfaces.ProjectChangedListener;
import interfaces.SelectionChangedListener;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import roadnetwork.Edge;
import roadnetwork.Junction;
import trafficdefinition.Accident;
import trafficdefinition.AreaFlow;
import trafficdefinition.Flow;
import trafficdefinition.HotSpot;
import trafficdefinition.School;
import trafficdefinition.TrafficArea;
import ui.propertypanels.PnlAccidentProperties;
import ui.propertypanels.PnlAreaFlowProperties;
import ui.propertypanels.PnlEdgeProperties;
import ui.propertypanels.PnlFlowProperties;
import ui.propertypanels.PnlHotSpotProperties;
import ui.propertypanels.PnlJunctionProperties;
import ui.propertypanels.PnlSchoolProperties;
import ui.propertypanels.PnlTrafficAreaProperties;

/**
 * Panel used to show and edit the properties of road network and traffic elements
 */
public class PnlProperties extends JPanel implements ProjectChangedListener, ModifiedCollectionListener, CurrentLayerChangedListener, SelectionChangedListener {

	private static final long serialVersionUID = 1L;

	private JComboBox cmbElements = null;

	private JScrollPane scrollPane = null;

	private JLabel lblMessage = null;

	/**
	 * Component used to display the selected element's properties
	 */
	JComponent currentComponent = null;

	/**
	 * Currently selected layer (road network or traffic)
	 */
	private Layer currentLayer = null; // @jve:decl-index=0:

	/**
	 * Reference to the map display so that we can refresh it when the user
	 * selects an element
	 */
	private Display mapDisplay = null;

	private JButton btnCenterOnSelectedElement = null;

	private JPanel pnlTop = null;

	/**
	 * Default constructor
	 */
	public PnlProperties() {
		super();
		initialize();

		//Show the default message (No project loaded)
		getScrollPane().setViewportView(getLblMessage());

		currentComponent = lblMessage;
	}

	/**
	 * @param mapDisplay  the mapDisplay to set
	 * @uml.property  name="mapDisplay"
	 */
	public void setMapDisplay(Display mapDisplay) {
		this.mapDisplay = mapDisplay;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.fill = GridBagConstraints.BOTH;
		gridBagConstraints3.gridwidth = 2;
		gridBagConstraints3.weightx = 1.0D;
		gridBagConstraints3.anchor = GridBagConstraints.NORTH;
		gridBagConstraints3.gridy = 0;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.weighty = 1.0;
		gridBagConstraints1.gridwidth = 2;
		gridBagConstraints1.gridx = 0;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getScrollPane(), gridBagConstraints1);
		this.add(getPnlTop(), gridBagConstraints3);
	}

	/**
	 * @return  the lblMessage
	 * @uml.property  name="lblMessage"
	 */
	private JLabel getLblMessage() {
		if (lblMessage == null) {
			lblMessage = new JLabel();
			lblMessage.setText("No project loaded");
			lblMessage.setHorizontalTextPosition(SwingConstants.CENTER);
			lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		}

		return lblMessage;
	}

	/**
	 * This method initializes cmbElements
	 * @return  javax.swing.JComboBox
	 * @uml.property  name="cmbElements"
	 */
	private JComboBox getCmbElements() {
		if (cmbElements == null) {
			cmbElements = new JComboBox();
			cmbElements.setPreferredSize(new Dimension(31, 20));
			cmbElements.setEnabled(false);
			cmbElements.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelectedElementChanged();
				}
			});
		}
		return cmbElements;
	}

	/**
	 * Handler for the selection changed event of the combobox
	 * 
	 */
	protected void SelectedElementChanged() {
		if (cmbElements.getSelectedIndex() != -1) {
			// Instruct the current layer to apply the new selection
			currentLayer.SelectElement((ProjectElement)cmbElements.getSelectedItem());

			// Refresh the map display
			mapDisplay.Refresh();

			// Show the properties for the selected element
			ShowElementProperties((ProjectElement) cmbElements.getSelectedItem());
			
			//Enable the center button
			btnCenterOnSelectedElement.setEnabled(true);
		}
		else{
			//Disable the center button
			btnCenterOnSelectedElement.setEnabled(false);			
		}
	}

	/**
	 * Called when the user changes the selection using the select tool in the
	 * map display
	 */
	public void SelectionChanged(SelectionCount count, ProjectElement selectedItem) {
		switch (count) {
		// No items selected
		case None:
			cmbElements.setSelectedIndex(-1);

			getLblMessage().setText("No items selected");
			getScrollPane().setViewportView(lblMessage);
			currentComponent = lblMessage;
			break;
		// One item selected
		case Single:
			// Select the corresponding element in the combo box
			cmbElements.setSelectedItem(selectedItem);

			// Show the selected element's properties
			ShowElementProperties(selectedItem);

			break;
		// Multiple items selected
		case Multiple:
			cmbElements.setSelectedIndex(-1);
			getLblMessage().setText("Multiple items selected");
			getScrollPane().setViewportView(lblMessage);
			currentComponent = lblMessage;
			break;
		}
	}

	/**
	 * Displays the appropriate properties component for the specified element
	 * 
	 * @param selectedItem
	 */
	private void ShowElementProperties(ProjectElement selectedItem) {
		if (selectedItem instanceof Edge) {
			PnlEdgeProperties propertyPanel = new PnlEdgeProperties();

			propertyPanel.setEdge((Edge) selectedItem);

			currentComponent = propertyPanel;

			getScrollPane().setViewportView(propertyPanel);
		} else if (selectedItem instanceof Junction) {
			PnlJunctionProperties propertyPanel = new PnlJunctionProperties();

			propertyPanel.setJunction((Junction) selectedItem);

			currentComponent = propertyPanel;

			getScrollPane().setViewportView(propertyPanel);
		} else if (selectedItem instanceof Flow) {
			PnlFlowProperties propertyPanel = new PnlFlowProperties();

			propertyPanel.setFlow((Flow) selectedItem);

			currentComponent = propertyPanel;

			getScrollPane().setViewportView(propertyPanel);
		} else if (selectedItem instanceof AreaFlow) {
			PnlAreaFlowProperties propertyPanel = new PnlAreaFlowProperties();

			propertyPanel.setAreaFlow((AreaFlow) selectedItem);

			currentComponent = propertyPanel;

			getScrollPane().setViewportView(propertyPanel);
		} else if (selectedItem instanceof HotSpot) {
			PnlHotSpotProperties propertyPanel = new PnlHotSpotProperties();

			propertyPanel.setHotSpot((HotSpot) selectedItem);

			currentComponent = propertyPanel;

			getScrollPane().setViewportView(propertyPanel);
		} else if (selectedItem instanceof TrafficArea) {
			PnlTrafficAreaProperties propertyPanel = new PnlTrafficAreaProperties();

			propertyPanel.setArea((TrafficArea) selectedItem);

			currentComponent = propertyPanel;

			getScrollPane().setViewportView(propertyPanel);
		}  else if (selectedItem instanceof School) {
			PnlSchoolProperties propertyPanel = new PnlSchoolProperties();

			propertyPanel.setSchool((School) selectedItem);

			currentComponent = propertyPanel;

			getScrollPane().setViewportView(propertyPanel);
		}  else if (selectedItem instanceof Accident) {
			PnlAccidentProperties propertyPanel = new PnlAccidentProperties();

			propertyPanel.setAccident((Accident) selectedItem);

			currentComponent = propertyPanel;

			getScrollPane().setViewportView(propertyPanel);
		} else {
			getLblMessage().setText("One item selected");
			getScrollPane().setViewportView(lblMessage);
			currentComponent = lblMessage;
		}
	}

	/**
	 * Called when the selected layer is modified to refresh the combobox
	 */
	public void CollectionModified() {
		cmbElements.setModel(new DefaultComboBoxModel(currentLayer.getElements().toArray()));
		cmbElements.updateUI();
	}

	/**
	 * Called when the user selects a different layer
	 */
	public void CurrentLayerChanged(Layer<? extends ProjectElement> newCurrentLayer) {
		// Remove event handlers for the previously selected layer
		if (currentLayer != null) {
			currentLayer.removeModifiedCollectionListener(this);
			currentLayer.removeSelectionChangedListener(this);
		}

		currentLayer = newCurrentLayer;

		// Fill the combo box with the layer's elements
		cmbElements.setModel(new DefaultComboBoxModel(newCurrentLayer.getElements().toArray()));
		cmbElements.setEnabled(true);
		cmbElements.setSelectedIndex(-1);

		getLblMessage().setText("No items selected");
		getScrollPane().setViewportView(lblMessage);
		currentComponent = lblMessage;

		// Register the event handlers on the new layer
		newCurrentLayer.addModifiedCollectionListener(this);
		newCurrentLayer.addSelectionChangedListener(this);
	}

	/**
	 * Called when the user closes, opens or creates a new project
	 */
	public void ProjectChanged(Project p) {
		// If the user closed the project remove any registered event handers
		// and empty the combo box
		if (p == null) {
			currentLayer.removeModifiedCollectionListener(this);
			currentLayer.removeSelectionChangedListener(this);

			currentLayer = null;

			cmbElements.setModel(new DefaultComboBoxModel());
			cmbElements.setEnabled(false);

			getLblMessage().setText("No project loaded");
			getScrollPane().setViewportView(lblMessage);
			currentComponent = lblMessage;
		}
	}

	/**
	 * This method initializes scrollPane
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrollPane"
	 */
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
		}
		return scrollPane;
	}

	/**
	 * This method initializes btnCenterOnSelectedElement	
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCenterOnSelectedElement"
	 */
	private JButton getBtnCenterOnSelectedElement() {
		if (btnCenterOnSelectedElement == null) {
			btnCenterOnSelectedElement = new JButton();
			btnCenterOnSelectedElement.setPreferredSize(new Dimension(20, 20));
			btnCenterOnSelectedElement.setIcon(new ImageIcon(getClass().getResource("/resources/icons/centeronelement.png")));
			btnCenterOnSelectedElement.setToolTipText("Center on element");
			btnCenterOnSelectedElement.setEnabled(false);
			btnCenterOnSelectedElement.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CenterOnSelectedElement();
				}
			});
		}
		return btnCenterOnSelectedElement;
	}

	/**
	 * Centers the map on the selected element
	 *
	 */
	protected void CenterOnSelectedElement() {
		mapDisplay.CenterAndZoomOnElement((ProjectElement) cmbElements.getSelectedItem());
	}

	/**
	 * This method initializes pnlTop	
	 * @return  javax.swing.JPanel
	 * @uml.property  name="pnlTop"
	 */
	private JPanel getPnlTop() {
		if (pnlTop == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridx = 0;
			pnlTop = new JPanel();
			pnlTop.setLayout(new GridBagLayout());
			pnlTop.setPreferredSize(new Dimension(100, 20));
			pnlTop.add(getCmbElements(), gridBagConstraints);
			pnlTop.add(getBtnCenterOnSelectedElement(), gridBagConstraints2);
		}
		return pnlTop;
	}

	// TODO molo une te kam xhan dhe me doren kpus

}
