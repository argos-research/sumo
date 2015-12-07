package ui.propertypanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import roadnetwork.Junction;

public class PnlJunctionProperties extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblIdTitle = null;

	private JLabel lblIDValue = null;

	private JLabel lblTypeTitle = null;

	private JLabel lblTypeValue = null;

	/**
	 * This is the default constructor
	 */
	public PnlJunctionProperties() {
		super();
		initialize();
	}

	public void setJunction(Junction junction) {
		lblIDValue.setText(junction.getId());
		lblTypeValue.setText(junction.getType());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 1;
		gridBagConstraints3.anchor = GridBagConstraints.NORTH;
		gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints3.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints3.gridy = 2;
		lblTypeValue = new JLabel();
		lblTypeValue.setText("Type");
		lblTypeValue.setHorizontalTextPosition(SwingConstants.LEFT);
		lblTypeValue.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.gridx = 0;
		gridBagConstraints21.weighty = 1.0D;
		gridBagConstraints21.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints21.insets = new Insets(0, 10, 0, 5);
		gridBagConstraints21.gridy = 2;
		lblTypeTitle = new JLabel();
		lblTypeTitle.setText("Type:");
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 1;
		gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints2.weightx = 1.0D;
		gridBagConstraints2.insets = new Insets(10, 0, 0, 10);
		gridBagConstraints2.gridy = 1;
		lblIDValue = new JLabel();
		lblIDValue.setText("ID");
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.anchor = GridBagConstraints.WEST;
		gridBagConstraints11.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints11.gridy = 1;
		lblIdTitle = new JLabel();
		lblIdTitle.setText("ID:");
		this.setSize(143, 52);
		this.setLayout(new GridBagLayout());
		this.add(lblIdTitle, gridBagConstraints11);
		this.add(lblIDValue, gridBagConstraints2);
		this.add(lblTypeTitle, gridBagConstraints21);
		this.add(lblTypeValue, gridBagConstraints3);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
