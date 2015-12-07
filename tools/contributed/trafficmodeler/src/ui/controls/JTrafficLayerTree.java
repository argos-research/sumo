package ui.controls;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import trafficdefinition.TrafficDefinitionLayer;

/**
 * Custom tree control used for the layers panel. This tree enables editing of
 * only the traffic layer nodes
 * 
 */
public class JTrafficLayerTree extends JTree {

	private static final long serialVersionUID = 4595215237043236638L;

	@Override
	public boolean isPathEditable(TreePath path) {
		// If the path corresponds to a traffic layer node then it is editable
		if (((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject() instanceof TrafficDefinitionLayer) {
			return true;
		} else {
			return false;
		}
	}

}
