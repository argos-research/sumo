package ui.controls;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import trafficdefinition.TrafficDefinitionLayer;

/**
 * Tree model used for the layers panel. This model enables the renaming of
 * traffic layers
 * 
 */
public class JTrafficLayerTreeModel extends DefaultTreeModel {
	private static final long serialVersionUID = -6986407387360325989L;

	public JTrafficLayerTreeModel(TreeNode root) {
		super(root);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// Get the changed node
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

		// Get the new layer name and trim the layer type
		String newName = (String) newValue;
		if (newName.contains("(")) {
			newName = newName.substring(0, newName.lastIndexOf("("));
		}
		
		// Set the name of the layer corresponding to the node
		((TrafficDefinitionLayer) node.getUserObject()).setName(newName);
	}

}
