package ui.dialogs;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import ui.filefilters.ProjectFileFilter;
import ui.filefilters.SUMOFileFilter;

/**
 * Dialog that enables the user to create a new project
 */
public class DlgNewProject extends JDialog {

	private static final long serialVersionUID = 1L;

	private int answer = JOptionPane.CANCEL_OPTION;

	private JButton btnBrowseMap = null;

	private JButton btnBrowseSave = null;

	private JButton btnCancel = null;

	private JButton btnOK = null;

	private JDialog dialog = this; // @jve:decl-index=0:visual-constraint="82,242"

	private JPanel jContentPane = null; // @jve:decl-index=0:visual-constraint="10,10"

	private JLabel lblLocation = null;

	private JLabel lblMapLocation = null;

	private JTextField txtMapLocation = null;

	private JTextField txtSave = null;

	/**
	 * @param owner
	 */

	public DlgNewProject(JFrame frame) {
		super(frame, "New Project", true);
		initialize();
		setLocationRelativeTo(frame);
		setVisible(true);
	}

	/**
	 * Returns the user's answer
	 * @return
	 * @uml.property  name="answer"
	 */
	public int getAnswer() {
		return answer;
	}

	/**
	 * Returns the file containing the road network
	 * @return
	 */
	public File getMapFile() {
		return new File(txtMapLocation.getText());
	}

	/**
	 * Returns the path to save the new project to
	 * @return
	 */
	public String getProjectPath() {
		return txtSave.getText();
	}

	/**
	 * This method initializes btnBrowseMap
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnBrowse"
	 */
	private JButton getBtnBrowseMap() {
		if (btnBrowseMap == null) {
			btnBrowseMap = new JButton();
			btnBrowseMap.setText("Browse...");
			btnBrowseMap.setBounds(new Rectangle(351, 34, 93, 19));
			btnBrowseMap.setPreferredSize(new Dimension(87, 20));
			btnBrowseMap.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//Show road network selection dialog
					JFileChooser f = new JFileChooser();

					f.setMultiSelectionEnabled(false);
					f.setFileSelectionMode(JFileChooser.FILES_ONLY);
					f.setDialogTitle("Select a SUMO road network map");
					f.setFileFilter(new SUMOFileFilter());
					f.setAcceptAllFileFilterUsed(false);

					if (f.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
						getTxtMapLocation().setText(f.getSelectedFile().getAbsolutePath());
						getTxtMapLocation().setToolTipText(f.getSelectedFile().getAbsolutePath());
					}

				}
			});
		}
		return btnBrowseMap;
	}

	/**
	 * This method initializes btnBrowseSave
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnBrowseSave"
	 */
	private JButton getBtnBrowseSave() {
		if (btnBrowseSave == null) {
			btnBrowseSave = new JButton();
			btnBrowseSave.setText("Browse...");
			btnBrowseSave.setBounds(new Rectangle(351, 8, 93, 19));
			btnBrowseSave.setPreferredSize(new Dimension(87, 20));
			btnBrowseSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//Shows the file save dialog
					JFileChooser f = new JFileChooser();

					f.setDialogTitle("Select project path");
					f.setAcceptAllFileFilterUsed(false);
					f.setFileFilter(new ProjectFileFilter());
					f.setMultiSelectionEnabled(false);

					if (f.showSaveDialog(dialog) == JFileChooser.APPROVE_OPTION) {
						if (f.getSelectedFile().getAbsolutePath().endsWith(".sumo.prj")) {
							getTxtSave().setText(f.getSelectedFile().getAbsolutePath());
							getTxtSave().setToolTipText(f.getSelectedFile().getAbsolutePath());
						} else {
							getTxtSave().setText(f.getSelectedFile().getAbsolutePath() + ".sumo.prj");
							getTxtSave().setToolTipText(f.getSelectedFile().getAbsolutePath() + ".sumo.prj");
						}

					}

				}
			});
		}
		return btnBrowseSave;
	}

	/**
	 * This method initializes btnCancel
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCancel"
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("Cancel");
			btnCancel.setBounds(new Rectangle(362, 63, 82, 24));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					answer = JOptionPane.CANCEL_OPTION;

					dialog.setVisible(false);
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
			btnOK.setText("OK");
			btnOK.setBounds(new Rectangle(275, 63, 82, 24));
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					answer = JOptionPane.OK_OPTION;

					//Perform validations
					if (txtSave.getText().trim().length() == 0) {
						JOptionPane.showMessageDialog(dialog, "Please select the project path");
						return;
					}

					if (txtMapLocation.getText().trim().length() == 0) {
						JOptionPane.showMessageDialog(dialog, "Please select a road network map");
						return;
					}

					if (!(new File(txtMapLocation.getText().trim().replaceFirst(".net.", ".edg.")).exists()) || (!(new File(txtMapLocation.getText().trim().replaceFirst(".net.", ".nod.")).exists()))) {
						JOptionPane.showMessageDialog(dialog, "No corresponding edge and/or node file was found for the selected map");
						return;
					}

					dialog.setVisible(false);
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes jContentPane
	 * @return  javax.swing.JPanel
	 * @uml.property  name="jContentPane"
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getLblLocation(), null);
			jContentPane.add(getTxtSave(), null);
			jContentPane.add(getBtnBrowseSave(), null);
			jContentPane.add(getLblMapLocation(), null);
			jContentPane.add(getTxtMapLocation(), null);
			jContentPane.add(getBtnBrowseMap(), null);
			jContentPane.add(getBtnOK(), null);
		}
		return jContentPane;
	}

	/**
	 * @return  the lblLocation
	 * @uml.property  name="lblLocation"
	 */
	private JLabel getLblLocation() {
		if (lblLocation == null) {
			lblLocation = new JLabel();
			lblLocation.setHorizontalAlignment(SwingConstants.RIGHT);
			lblLocation.setBounds(new Rectangle(9, 11, 52, 16));
			lblLocation.setText("Save as:");
		}

		return lblLocation;
	}

	/**
	 * @return  the lblMapLocation
	 * @uml.property  name="lblMapLocation"
	 */
	private JLabel getLblMapLocation() {
		if (lblMapLocation == null) {
			lblMapLocation = new JLabel();
			lblMapLocation.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMapLocation.setBounds(new Rectangle(8, 36, 52, 16));
			lblMapLocation.setText("Map file:");
		}

		return lblMapLocation;
	}

	/**
	 * This method initializes txtMapLocation
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtMapLocation"
	 */
	private JTextField getTxtMapLocation() {
		if (txtMapLocation == null) {
			txtMapLocation = new JTextField();
			txtMapLocation.setPreferredSize(new Dimension(60, 20));
			txtMapLocation.setEditable(false);
			txtMapLocation.setBounds(new Rectangle(71, 34, 273, 19));
		}
		return txtMapLocation;
	}

	/**
	 * This method initializes txtSave
	 * @return  javax.swing.JTextField
	 * @uml.property  name="txtSave"
	 */
	private JTextField getTxtSave() {
		if (txtSave == null) {
			txtSave = new JTextField();
			txtSave.setBounds(new Rectangle(71, 8, 273, 19));
			txtSave.setEditable(false);
		}
		return txtSave;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(459, 127);
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setContentPane(getJContentPane());
		setTitle("New Project");
	}
} // @jve:decl-index=0:visual-constraint="17,21"
