package ui.propertypanels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import roadnetwork.Edge;

public class PnlEdgeProperties extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblFromTitle = null;

	private JLabel lblFromValue = null;

	private JLabel lblIdTitle = null;

	private JLabel lblIDValue = null;

	private JLabel lblLanesTitle = null;

	private JLabel lblLanesValue = null;

	private JLabel lblLengthTitle = null;

	private JLabel lblLengthVaue = null;

	private JLabel lblPriorityTitle = null;

	private JLabel lblPriorityValue = null;

	private JLabel lblSpeedTitle = null;

	private JLabel lblSpeedValue = null;

	private JLabel lblToTitle = null;

	private JLabel lblToValue = null;

	/**
	 * This is the default constructor
	 */
	public PnlEdgeProperties() {
		super();
		initialize();
	}

	public void setEdge(Edge edge) {
		lblIDValue.setText(edge.getId());
		lblFromValue.setText(edge.getFrom());
		lblToValue.setText(edge.getTo());
		lblLengthVaue.setText(String.valueOf(edge.getLength()));
		lblSpeedValue.setText(String.valueOf(edge.getSpeedLimit()));
		lblPriorityValue.setText(String.valueOf(edge.getPriority()));
		lblLanesValue.setText(String.valueOf(edge.getLanes().size()));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
		gridBagConstraints31.gridx = 0;
		gridBagConstraints31.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints31.weighty = 1.0D;
		gridBagConstraints31.insets = new Insets(0, 10, 0, 5);
		gridBagConstraints31.gridy = 7;
		lblPriorityTitle = new JLabel();
		lblPriorityTitle.setText("Priority:");
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 1;
		gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints21.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints21.anchor = GridBagConstraints.NORTH;
		gridBagConstraints21.gridy = 7;
		lblPriorityValue = new JLabel();
		lblPriorityValue.setText("Edge Priority");
		lblPriorityValue.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints1.gridy = 6;
		lblLanesValue = new JLabel();
		lblLanesValue.setText("Edge Lanes");
		lblLanesValue.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridx = 0;
		gridBagConstraints13.anchor = GridBagConstraints.WEST;
		gridBagConstraints13.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints13.gridy = 6;
		lblLanesTitle = new JLabel();
		lblLanesTitle.setText("Lanes:");
		GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
		gridBagConstraints111.gridx = 1;
		gridBagConstraints111.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints111.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints111.gridy = 5;
		lblToValue = new JLabel();
		lblToValue.setText("Edge To");
		lblToValue.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 0;
		gridBagConstraints10.anchor = GridBagConstraints.WEST;
		gridBagConstraints10.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints10.gridy = 5;
		lblToTitle = new JLabel();
		lblToTitle.setText("To:");
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 1;
		gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints9.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints9.gridy = 4;
		lblFromValue = new JLabel();
		lblFromValue.setText("Edge From");
		lblFromValue.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.anchor = GridBagConstraints.WEST;
		gridBagConstraints8.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints8.gridy = 4;
		lblFromTitle = new JLabel();
		lblFromTitle.setText("From:");
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 1;
		gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints7.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints7.gridy = 3;
		lblSpeedValue = new JLabel();
		lblSpeedValue.setText("Edge Speed");
		lblSpeedValue.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 0;
		gridBagConstraints6.anchor = GridBagConstraints.WEST;
		gridBagConstraints6.insets = new Insets(0, 10, 0, 5);
		gridBagConstraints6.gridy = 3;
		lblSpeedTitle = new JLabel();
		lblSpeedTitle.setText("Speed limit:");
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 1;
		gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints5.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints5.gridy = 2;
		lblLengthVaue = new JLabel();
		lblLengthVaue.setText("Edge Length");
		lblLengthVaue.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.anchor = GridBagConstraints.WEST;
		gridBagConstraints4.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints4.gridy = 2;
		lblLengthTitle = new JLabel();
		lblLengthTitle.setText("Length:");
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints2.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints2.weightx = 1.0D;
		gridBagConstraints2.gridy = 1;
		lblIDValue = new JLabel();
		lblIDValue.setText("Edge ID");
		lblIDValue.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.anchor = GridBagConstraints.WEST;
		gridBagConstraints11.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints11.gridy = 1;
		lblIdTitle = new JLabel();
		lblIdTitle.setText("ID:");
		this.setSize(165, 150);
		this.setLayout(new GridBagLayout());
		this.add(lblIdTitle, gridBagConstraints11);
		this.add(lblIDValue, gridBagConstraints2);
		this.add(lblLengthTitle, gridBagConstraints4);
		this.add(lblLengthVaue, gridBagConstraints5);
		this.add(lblSpeedTitle, gridBagConstraints6);
		this.add(lblSpeedValue, gridBagConstraints7);
		this.add(lblFromTitle, gridBagConstraints8);
		this.add(lblFromValue, gridBagConstraints9);
		this.add(lblToTitle, gridBagConstraints10);
		this.add(lblToValue, gridBagConstraints111);
		this.add(lblLanesTitle, gridBagConstraints13);
		this.add(lblLanesValue, gridBagConstraints1);
		this.add(lblPriorityValue, gridBagConstraints21);
		this.add(lblPriorityTitle, gridBagConstraints31);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
