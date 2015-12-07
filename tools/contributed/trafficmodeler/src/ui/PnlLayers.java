package ui;

import classes.Layer;
import classes.Project;
import classes.ProjectElement;
import enums.TrafficDefinitionLayerType;
import helpers.Broadcaster;
import interfaces.CurrentLayerChangedListener;
import interfaces.ProjectChangedListener;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import roadnetwork.RoadNetwork;
import trafficdefinition.TrafficDefinitionLayer;
import ui.controls.JTrafficLayerTree;
import ui.controls.JTrafficLayerTreeModel;
import ui.controls.ProjectTreeCellRenderer;

/**
 * Panel used to display and modify the current project's layer collection
 */
public class PnlLayers extends JPanel implements TreeSelectionListener, ProjectChangedListener, TreeWillExpandListener {

	private static final long serialVersionUID = 1L;

	private JButton btnDelete = null;

	private JButton btnDuplicate = null;

	private JButton btnNew = null;

	/**
	 * Collection of objects that will be notified when the user selects another
	 * layer
	 */
	private List<CurrentLayerChangedListener> currentLayerChangedListeners = new ArrayList<CurrentLayerChangedListener>(); // @jve:decl-index=0:

	/**
	 * Currently loaded project
	 */
	private Project project = null;

	private JScrollPane scrlLayerList = null;

	private JToolBar tlbLayers = null;

	private JTrafficLayerTree treeLayers = null;

	/**
	 * This is the default constructor
	 */
	public PnlLayers() {
		super();
		initialize();

		// Be informed when the loaded project is changed
		Broadcaster.addProjectChangedListener(this);

		treeLayers.addTreeWillExpandListener(this);
	}

	/**
	 * Registers the specified listener so that it will be notified when the
	 * current layer changes
	 * 
	 * @param currentLayerChangedListener
	 */
	public void addCurrentLayerChangedListener(CurrentLayerChangedListener currentLayerChangedListener) {
		currentLayerChangedListeners.add(currentLayerChangedListener);
	}

	/**
	 * Notifies the registered listeners that the current layer has changed
	 * 
	 * @param currentLayer
	 */
	public void NotifyCurrentLayerChanged(Layer<? extends ProjectElement> currentLayer) {
		for (CurrentLayerChangedListener currentLayerChangedListener : currentLayerChangedListeners) {
			currentLayerChangedListener.CurrentLayerChanged(currentLayer);
		}
	}

	/**
	 * Called when the project is closed, opened or created
	 */
	public void ProjectChanged(Project p) {
		project = p;

		// If the project was closed reset the component
		if (project == null) {
			treeLayers.removeTreeSelectionListener(this);

			treeLayers.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("No project loaded")));
			treeLayers.updateUI();

			btnDelete.setEnabled(false);
			btnDuplicate.setEnabled(false);
			btnNew.setEnabled(false);
		}
		// Project opened or created
		else {
			// Create the tree nodes
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("Project");
			DefaultMutableTreeNode mapNode = new DefaultMutableTreeNode(project.getRoadNetwork());
			DefaultMutableTreeNode layersNode = new DefaultMutableTreeNode("Traffic Layers");

			root.add(mapNode);

			for (TrafficDefinitionLayer layer : project.getTrafficLayers()) {
				DefaultMutableTreeNode layerNode = new DefaultMutableTreeNode(layer);

				layersNode.add(layerNode);
			}

			root.add(layersNode);

			JTrafficLayerTreeModel dtm = new JTrafficLayerTreeModel(root);

			treeLayers.setModel(dtm);

			// Select the map node initially
			DefaultMutableTreeNode initialSelection = mapNode;

			TreePath initialSelectionPath = new TreePath(((DefaultTreeModel) treeLayers.getModel()).getPathToRoot(initialSelection));

			treeLayers.addTreeSelectionListener(this);

			treeLayers.setSelectionPath(initialSelectionPath);
		}
	}

	/**
	 * 
	 * @param currentLayerChangedListener
	 */
	public void removeCurrentLayerChangedListener(CurrentLayerChangedListener currentLayerChangedListener) {
		if (currentLayerChangedListeners.contains(currentLayerChangedListener)) {
			currentLayerChangedListeners.remove(currentLayerChangedListener);
		}
	}

	public void treeWillCollapse(TreeExpansionEvent e) throws ExpandVetoException {
		//Dont let the user collapse the tree
		throw new ExpandVetoException(e);
	}

	public void treeWillExpand(TreeExpansionEvent e) throws ExpandVetoException {
	}

	/**
	 * Called when the user selects a layer in the tree
	 */
	public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
		//Get the selected object
		Object newObject = ((DefaultMutableTreeNode) e.getNewLeadSelectionPath().getLastPathComponent()).getUserObject();

		// If the user has selected a traffic layer
		if (newObject instanceof TrafficDefinitionLayer) {
			// Inform the listeners that the current layer has changed
			NotifyCurrentLayerChanged((TrafficDefinitionLayer) newObject);

			// Enable buttons
			btnDelete.setEnabled(true);
			btnNew.setEnabled(true);
			btnDuplicate.setEnabled(true);
		}
		// If the user selected the map
		else if (newObject instanceof RoadNetwork) {
			// Inform the listeners that the current layer has changed
			NotifyCurrentLayerChanged((RoadNetwork) newObject);

			// Disable buttons
			btnDelete.setEnabled(false);
			btnNew.setEnabled(false);
			btnDuplicate.setEnabled(false);
		}
		// If the root or the layers node was selected
		else if (newObject instanceof String) {
			String o = (String) newObject;

			// If the user selected the root node then select the previously
			// selected node (i.e. don't let the user select the root node
			if (o.equals("Project")) {
				treeLayers.setSelectionPath(e.getOldLeadSelectionPath());

				return;
			}
			// If the user selected the layers node then select the first
			// layer
			else if (o.equals("Traffic Layers")) {
				TreePath tp = e.getNewLeadSelectionPath();

				DefaultMutableTreeNode firstLayerNode = ((DefaultMutableTreeNode) tp.getLastPathComponent()).getFirstLeaf();

				TreePath selectionPath = new TreePath(((DefaultTreeModel) treeLayers.getModel()).getPathToRoot(firstLayerNode));

				treeLayers.setSelectionPath(selectionPath);
			}
		}
	}

	/**
	 * This method initializes btnDelete
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnDelete"
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setIcon(new ImageIcon(getClass().getResource("/resources/icons/deletelayer.png")));
			btnDelete.setToolTipText("Delete layer");
			btnDelete.setEnabled(false);
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DeleteCurrentTrafficLayer();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnDuplicate
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnDuplicate"
	 */
	private JButton getBtnDuplicate() {
		if (btnDuplicate == null) {
			btnDuplicate = new JButton();
			btnDuplicate.setEnabled(false);
			btnDuplicate.setIcon(new ImageIcon(getClass().getResource("/resources/icons/duplicatelayer.png")));
			btnDuplicate.setToolTipText("Duplicate layer");
			btnDuplicate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DuplicateCurrentTrafficLayer();
				}
			});
		}
		return btnDuplicate;
	}

	/**
	 * This method initializes btnNew
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnNew"
	 */
	private JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton();
			btnNew.setIcon(new ImageIcon(getClass().getResource("/resources/icons/addlayer.png")));
			btnNew.setToolTipText("Add new layer");
			btnNew.setSize(new Dimension(28, 20));
			btnNew.setEnabled(false);
			btnNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CreateNewTrafficLayer(null);
				}
			});
		}
		return btnNew;
	}

	/**
	 * This method initializes scrlLayerList
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrlLayerList"
	 */
	private JScrollPane getScrlLayerList() {
		if (scrlLayerList == null) {
			scrlLayerList = new JScrollPane();
			scrlLayerList.setPreferredSize(new Dimension(100, 100));
			scrlLayerList.setViewportView(getTreeLayers());
		}
		return scrlLayerList;
	}

	/**
	 * This method initializes tlbLayers
	 * @return  javax.swing.JToolBar
	 * @uml.property  name="tlbLayers"
	 */
	private JToolBar getTlbLayers() {
		if (tlbLayers == null) {
			tlbLayers = new JToolBar();
			tlbLayers.setSize(new Dimension(211, 30));
			tlbLayers.setFloatable(false);
			tlbLayers.setPreferredSize(new Dimension(18, 26));
			tlbLayers.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			tlbLayers.add(getBtnNew());
			tlbLayers.add(getBtnDelete());
			tlbLayers.add(getBtnDuplicate());
		}
		return tlbLayers;
	}

	/**
	 * This method initializes treeLayers
	 * @return  javax.swing.JTree
	 * @uml.property  name="treeLayers"
	 */
	private JTree getTreeLayers() {
		if (treeLayers == null) {
			treeLayers = new JTrafficLayerTree();
			treeLayers.setShowsRootHandles(false);
			treeLayers.setToggleClickCount(-1);
			treeLayers.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("No project loaded")));
			treeLayers.setEditable(true);
			treeLayers.setCellRenderer(new ProjectTreeCellRenderer());
			treeLayers.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		}
		return treeLayers;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(162, 200);
		this.setLayout(new BorderLayout());
		this.add(getScrlLayerList(), BorderLayout.CENTER);
		this.add(getTlbLayers(), BorderLayout.SOUTH);

	}

	/**
	 * Creates a new traffic layer
	 * 
	 * @param type The new layer's type
	 */
	protected void CreateNewTrafficLayer(TrafficDefinitionLayerType type) {
		//If the type was not specified then ask the user
		if(type==null){
			String s = (String) JOptionPane.showInputDialog(this,
															"Select the type of the new layer",
															"Create new traffic definition layer",
															JOptionPane.INFORMATION_MESSAGE,
															(Icon)new ImageIcon(getClass().getResource("/resources/icons/addlayer.png")),
															new String[]{"User-defined","Activity-based","Random"},
															"User-Defined"
															);
			
			if ((s != null) && (s.length() > 0)) {
				if(s.equals("User-defined")){
					type = TrafficDefinitionLayerType.UserDefined;
				}
				else if(s.equals("Activity-based")){
					type = TrafficDefinitionLayerType.ActivityBased;
				}
				else if(s.equals("Random")){
					type = TrafficDefinitionLayerType.Random;
				}
			}
			else{
				return;
			}
		}
		
		//Create the layer
		TrafficDefinitionLayer newLayer = project.createNewLayer(type);

		//Add the node to the tree
		DefaultMutableTreeNode newLayerNode = new DefaultMutableTreeNode(newLayer);

		DefaultMutableTreeNode layersNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) treeLayers.getModel().getRoot()).getChildAt(1);

		layersNode.add(newLayerNode);

		//Select the new layer
		treeLayers.setSelectionPath(new TreePath(newLayerNode.getPath()));

		treeLayers.updateUI();
	}

	/**
	 * Deletes the currently selected traffic layer
	 * 
	 */
	protected void DeleteCurrentTrafficLayer() {
		//Get the selected traffic layer
		DefaultMutableTreeNode selectedLayerNode = (DefaultMutableTreeNode) treeLayers.getSelectionPath().getLastPathComponent();
		TrafficDefinitionLayer selectedLayer = (TrafficDefinitionLayer) selectedLayerNode.getUserObject();

		if (JOptionPane.showConfirmDialog(this, "Delete layer " + selectedLayer.getName() + " ?") == JOptionPane.YES_OPTION) {
			// Delete the layer
			project.DeleteLayer((TrafficDefinitionLayer) selectedLayerNode.getUserObject());

			// If this was the last traffic layer add a new layer to the project
			if (selectedLayerNode.getSiblingCount() == 1) {
				JOptionPane.showMessageDialog(this, "The project must have at least one traffic layer. An empty layer will be inserted to the project.");

				CreateNewTrafficLayer(TrafficDefinitionLayerType.UserDefined);
			}

			// Remove the layer node from the tree
			DefaultMutableTreeNode layersNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) treeLayers.getModel().getRoot()).getChildAt(1);

			layersNode.remove(selectedLayerNode);

			// Select the first layer
			treeLayers.setSelectionPath(new TreePath(((DefaultMutableTreeNode) layersNode.getFirstChild()).getPath()));

			treeLayers.updateUI();
		}
	}

	/**
	 * Creates a new traffic layer that is a copy of the currently selected one
	 * 
	 */
	protected void DuplicateCurrentTrafficLayer() {
		DefaultMutableTreeNode selectedLayerNode = (DefaultMutableTreeNode) treeLayers.getSelectionPath().getLastPathComponent();

		TrafficDefinitionLayer newLayer = project.createDuplicateLayer((TrafficDefinitionLayer) selectedLayerNode.getUserObject());

		DefaultMutableTreeNode newLayerNode = new DefaultMutableTreeNode(newLayer);

		DefaultMutableTreeNode layersNode = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) treeLayers.getModel().getRoot()).getChildAt(1);

		layersNode.add(newLayerNode);

		treeLayers.setSelectionPath(new TreePath(newLayerNode.getPath()));

		treeLayers.updateUI();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
