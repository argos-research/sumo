package ui.controls;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import roadnetwork.RoadNetwork;
import trafficdefinition.ActivityBasedTrafficDefinitionLayer;
import trafficdefinition.RandomTrafficDefinitionLayer;
import trafficdefinition.UserDefinedTrafficDefinitionLayer;

/**
 * Custom tree renderer for the project tree
 *
 */
public class ProjectTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;

	ImageIcon mapIcon = new ImageIcon(getClass().getResource("/resources/icons/map.png"));

	ImageIcon activityBasedTrafficLayerIcon = new ImageIcon(getClass().getResource("/resources/icons/layeractivity.png"));
	ImageIcon randomTrafficLayerIcon = new ImageIcon(getClass().getResource("/resources/icons/layerrandom.png"));
	ImageIcon userDefinedTrafficLayerIcon = new ImageIcon(getClass().getResource("/resources/icons/layeruser.png"));

	ImageIcon trafficLayersIcon = new ImageIcon(getClass().getResource("/resources/icons/layers.png"));

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		//Depending on the node being rendered set the appropriate icon
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object nodeInfo = node.getUserObject();

		if(nodeInfo instanceof ActivityBasedTrafficDefinitionLayer){
			setIcon(activityBasedTrafficLayerIcon);
		}
		else if(nodeInfo instanceof RandomTrafficDefinitionLayer){
			setIcon(randomTrafficLayerIcon);
		}
		else if(nodeInfo instanceof UserDefinedTrafficDefinitionLayer){
			setIcon(userDefinedTrafficLayerIcon);
		}
		else if (nodeInfo instanceof RoadNetwork) {
			setIcon(mapIcon);
		}
		else if(nodeInfo instanceof String){
			String s = (String) nodeInfo;
			
			if(s.equals("Traffic Layers")){
				setIcon(trafficLayersIcon);
			}
		}

		return this;
	}
}
